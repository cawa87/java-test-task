package io.ronte.integration;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultChannelPoolHandler implements ChannelPoolHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultChannelPoolHandler.class);

    private final SslContext sslCtx;
    private final String host;
    private final int port;

    public DefaultChannelPoolHandler(SslContext sslCtx, String host, int port) {
        this.sslCtx = sslCtx;
        this.host = host;
        this.port = port;
    }

    public void channelReleased(Channel ch) throws Exception {
        logger.debug("Channel released:" + ch);

    }

    public void channelCreated(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        logger.debug("Channel created:" + ch);


        pipeline.addLast(sslCtx.newHandler(ch.alloc(), host, port));
        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new StringResponseChannelHandler());
    }

    public void channelAcquired(Channel ch) throws Exception {
        logger.debug("Channel acquired:" + ch);
    }
}
