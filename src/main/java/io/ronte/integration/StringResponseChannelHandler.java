package io.ronte.integration;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class StringResponseChannelHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(StringResponseChannelHandler.class);

    private CompletableFuture<String> responseFuture = new CompletableFuture<>();

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        responseFuture.complete(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Error in inbound channel handler", cause);
        ctx.close();
    }

    public String getResponse() {
        try {
            String result = responseFuture.get(500, TimeUnit.MILLISECONDS);
            responseFuture = new CompletableFuture<>();
            return result;
        } catch (Exception e) {
            logger.error("Failed to get response in inbound handler", e);

        }
        return null;
    }
}
