package org.lab.mars.oenm2m.web.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.lab.mars.onem2m.web.nework.protol.M2mWebPacket;
import org.lab.onem2m.web.handle.HelloWorldController;
import org.lab.onem2m.web.handle.RetrieveKeyController;

public class WebClientChannelHandler extends
        SimpleChannelInboundHandler<Object> {
    /**
     * Creates a client-side handler.
     */
    public WebClientChannelHandler() {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        M2mWebPacket m2mWebPacket = (M2mWebPacket) msg;
        if (m2mWebPacket.getM2mRequestHeader().getKey() != null) {
            RetrieveKeyController.m2mWebPacket = m2mWebPacket;
            RetrieveKeyController.reentrantLock.lock();
            RetrieveKeyController.condition.signalAll();
            RetrieveKeyController.reentrantLock.unlock();
        } else {
            HelloWorldController.m2mWebPacket = m2mWebPacket;
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}