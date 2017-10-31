package io.ronte.integration;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;

public class SSLSocketStringClientImpl implements SSLSocketStringClient {

    private static final Logger logger = LoggerFactory.getLogger(DefaultChannelPoolHandler.class);

    private final FixedChannelPool channelPool;

    public SSLSocketStringClientImpl(String host,
                                     int port,
                                     int poolMaxConnectionsCount,
                                     int poolMaxPendingAcquires) throws SSLException {

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(host, port);

        //using InsecureTrustManagerFactory for simplification, it can't be used in production!
        final SslContext sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

        this.channelPool = new FixedChannelPool(bootstrap,
                new DefaultChannelPoolHandler(sslCtx, host, port), poolMaxConnectionsCount, poolMaxPendingAcquires);
    }

    @Override
    public String sendMessage(String msg) {

        String result = null;
        try {
            Channel channel = channelPool.acquire().sync().get();
            StringResponseChannelHandler handler = channel.pipeline().get(StringResponseChannelHandler.class);
            channel.writeAndFlush(msg + "\r\n").sync().get();
            result = handler.getResponse();
            channelPool.release(channel).sync().get();
        } catch (Exception e) {
            logger.error("Failed to send message or get response, msg:" + msg, e);
        }
        return result;
    }
}
