package ru.smarty.accountapi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Dirty implementation of some kind of save & naive pooling: under load it'll use specified number of connections and no more.
 * Without load it'll start to release connections after 5 seconds.
 * <p>
 * Implementation is simple: N threads, processing BlockingQueue of incoming messages. As soon as there are no messages, connection is closed.
 * Might be overkill, good thing here is it's stable to reason about concurrent connection access, stale connections, etc (connections are closed soon after not being used).
 * <p>
 * In production it's better to replace with some well-known & tested pool implementation.
 */
@Service
public class SimpleSslPoolService {
    private final BlockingQueue<SimpleMessage> messages = new LinkedBlockingQueue<>();
    private final List<SenderInstance> instances = new ArrayList<>();
    private Configuration configuration;

    public SimpleSslPoolService(Configuration configuration) {
        this.configuration = configuration;
    }

    public Future<String> postMessage(String message) {
        SimpleMessage msg = new SimpleMessage(message);
        messages.add(msg);
        return msg.result;
    }

    @PostConstruct
    public void start() {
        for (int i = 0; i < configuration.poolSize; i++) {
            SenderInstance instance = new SenderInstance(messages, configuration.host, configuration.port);
            instance.start();
            instances.add(instance);
        }
    }

    @PreDestroy
    public void shutdown() {
        instances.forEach(SenderInstance::shutdown);
    }


    static class SenderInstance {
        private static final Logger logger = LoggerFactory.getLogger(SenderInstance.class);
        private BlockingQueue<SimpleMessage> requests;
        private final String remoteServer;
        private final int port;
        private Thread thread;

        SenderInstance(BlockingQueue<SimpleMessage> requests, String remoteServer, int port) {
            this.requests = requests;
            this.remoteServer = remoteServer;
            this.port = port;
        }

        void start() {
            this.thread = new Thread(() -> {
                Socket socket = null;
                OutputStream os = null;
                BufferedReader is = null;
                while (!Thread.interrupted()) {
                    try {
                        SimpleMessage next;
                        try {
                            next = requests.poll(5, TimeUnit.SECONDS);
                        } catch (InterruptedException e) {
                            break;
                        }

                        if (next != null) {
                            if (socket == null) {
                                socket = createSocket();
                                os = socket.getOutputStream();
                                is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            }

                            os.write(next.payload.getBytes(StandardCharsets.UTF_8));
                            os.write("\r\n".getBytes());
                            os.flush();

                            String response = is.readLine();
                            next.result.complete(response);
                        } else if (socket != null) {
                            logger.debug("Closing unused connection.");
                            socket.close();
                            socket = null;
                        }
                    } catch (Exception e) {
                        logger.warn("Exception while processing message", e);
                    }
                }

                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        logger.warn("Error while shutdown", e);
                    }
                }
            });
            this.thread.start();
        }

        void shutdown() {
            if (this.thread.isAlive()) {
                this.thread.interrupt();
                try {
                    this.thread.join();
                } catch (InterruptedException ignored) {
                }
            }
        }

        private Socket createSocket() {
            try {
                logger.debug("Creating new socket to {}: {}.", remoteServer, port);
                Socket socket = SSLSocketFactory.getDefault().createSocket(remoteServer, port);
                logger.debug("Creating new socket: Done.");
                return socket;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class SimpleMessage {
        final String payload;
        final CompletableFuture<String> result = new CompletableFuture<>();

        SimpleMessage(String payload) {
            this.payload = payload;
        }
    }

    @Component
    @ConfigurationProperties("remote-server")
    static class Configuration {
        String host;
        int port;
        int poolSize;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getPoolSize() {
            return poolSize;
        }

        public void setPoolSize(int poolSize) {
            this.poolSize = poolSize;
        }
    }
}

