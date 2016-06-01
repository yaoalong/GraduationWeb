package org.lab.mars.onem2m.web.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lab.mars.ds.web.network.constant.WebOperateType;
import lab.mars.ds.web.protocol.M2mWebPacket;

import org.lab.onem2m.web.handle.HelloWorldController;
import org.lab.onem2m.web.handle.ReplicationServersController;
import org.lab.onem2m.web.handle.RetrieveKeyController;
import org.lab.onem2m.web.handle.ServerPacketStatisticsController;

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
        if (m2mWebPacket.getM2mRequestHeader().getType() == WebOperateType.retriveLocalKey
                .getCode()) {
            RetrieveKeyController.m2mWebPacket = m2mWebPacket;
            RetrieveKeyController.reentrantLock.lock();
            RetrieveKeyController.condition.signalAll();
            RetrieveKeyController.reentrantLock.unlock();
        } else if (m2mWebPacket.getM2mRequestHeader().getType() == WebOperateType.lookServerLoad
                .getCode()) {

            ServerPacketStatisticsController.m2mWebPacket = m2mWebPacket;
            ServerPacketStatisticsController.reentrantLock.lock();
            ServerPacketStatisticsController.condition.signalAll();
            ServerPacketStatisticsController.reentrantLock.unlock();
        } else if (m2mWebPacket.getM2mRequestHeader().getType() == WebOperateType.lookReplicationServers
                .getCode()) {
            ReplicationServersController.m2mWebPacket = m2mWebPacket;
            ReplicationServersController.reentrantLock.lock();
            ReplicationServersController.condition.signalAll();
            ReplicationServersController.reentrantLock.unlock();
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