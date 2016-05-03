package org.lab.mars.onem2m.web.initializer;

import io.netty.channel.ChannelPipeline;
import lab.mars.ds.network.initializer.TcpChannelInitializer;

import org.lab.mars.onem2m.web.handler.WebClientChannelHandler;

/**
 * @author yaoalong
 * @Date 2016年1月24日
 * @Email yaoalong@foxmail.com
 */
public class WebClientChannelInitializer extends TcpChannelInitializer {

    public WebClientChannelInitializer() {

    }

    @Override
    public void init(ChannelPipeline ch) {
        ch.addLast(new WebClientChannelHandler());

    }
}