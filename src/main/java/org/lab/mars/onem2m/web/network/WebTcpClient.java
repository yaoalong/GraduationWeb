/**
 * 
 */
package org.lab.mars.onem2m.web.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.lab.mars.onem2m.web.network.initialize.WebClientChannelInitializer;

/**
 * @author Administrator
 *
 */
public class WebTcpClient {
	private Channel	channel;

	public void connectionOne(String host, int port) {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group( NetworkEventLoopGroup.workerGroup ).channel( NioSocketChannel.class ).option( ChannelOption.TCP_NODELAY, true )
				.handler( new WebClientChannelInitializer() );
		bootstrap.connect( host, port ).addListener( (ChannelFuture future) -> {
			channel = future.channel();
		} );

	}

	public void write(Object msg) {
		while (channel == null) {
			try {
				Thread.sleep( 1000 );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		channel.writeAndFlush( msg );
	}

	public void close() {
		if (channel != null) {
			channel.close();
		}
	}

	public static void main(String args[]) {
		WebTcpClient tcpClient = new WebTcpClient();
		try {
			tcpClient.connectionOne( "localhost", 2182 );
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

}