package org.lab.mars.onem2m.web;

import lab.mars.ds.network.TcpClientNetwork;

import org.lab.mars.onem2m.web.initializer.WebClientChannelInitializer;

public class WebTcpClient extends TcpClientNetwork {

    public WebTcpClient() {
        setSocketChannelChannelInitializer(new WebClientChannelInitializer());
    }

    @Override
    public void write(Object msg) throws Exception {
        while (channel == null) {
            try {
                reentrantLock.lock();
                condition.await();
            } catch (InterruptedException e) {
            } finally {
                reentrantLock.unlock();
            }
        }
        if (!channel.isActive()) {
            throw new Exception("端口已经关闭");

        }
        channel.writeAndFlush(msg);
        System.out.println("发送成功");
    }
}
