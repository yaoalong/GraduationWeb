package org.lab.mars.onem2m.web;

import lab.mars.ds.network.TcpClientNetwork;

import org.lab.mars.onem2m.web.initializer.WebClientChannelInitializer;

public class WebTcpClient extends TcpClientNetwork {

    public WebTcpClient() {
        setSocketChannelChannelInitializer(new WebClientChannelInitializer());
    }

    @Override
    public void write(Object msg) {
        while (channel == null) {
            try {
                reentrantLock.lock();
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }
        if (!channel.isActive()) {
            System.out.println("端口已关闭");
        } else {
            System.out.println("HH");
        }
        channel.writeAndFlush(msg);
        System.out.println("发送成功");
    }
}
