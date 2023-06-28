package com.yushu.engine.client;

import com.yushu.engine.protobuf.BaseMsg;
import com.yushu.engine.protocol.MsgTypes;
import com.yushu.engine.protocol.SequenceIdGenerator;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

public class NettyClient {

	public String host = "127.0.0.1"; // ip地址
	public int port = 30099; // 端口
	// 通过nio方式来接收连接和处理连接
	private EventLoopGroup group = new NioEventLoopGroup();
	public static 	NettyClient nettyClient = new NettyClient();

	/**唯一标记 */
	private boolean initFalg=true;

	public static void main(String[] args) {
		nettyClient.run();
	}

	/**
	 * Netty创建全部都是实现自AbstractBootstrap。 客户端的是Bootstrap，服务端的则是 ServerBootstrap。
	 **/
	public void run() {
		doConnect(new Bootstrap(), group);
	}

	/**
	 * 重连
	 */
	public void doConnect(Bootstrap bootstrap, EventLoopGroup eventLoopGroup) {
		ChannelFuture f = null;
		try {
			if (bootstrap != null) {
				bootstrap.group(eventLoopGroup);
				bootstrap.channel(NioSocketChannel.class);
				bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
				bootstrap.handler(new NettyClientFilter());
				bootstrap.remoteAddress(host, port);
				f = bootstrap.connect().addListener((ChannelFuture futureListener) -> {
					final EventLoop eventLoop = futureListener.channel().eventLoop();
					if (!futureListener.isSuccess()) {
						System.out.println("与服务端断开连接!在10s之后准备尝试重连!");
						eventLoop.schedule(() -> doConnect(new Bootstrap(), eventLoop), 10, TimeUnit.SECONDS);
					}
					else{
						//链接成功，发送一条消息给服务器
//						BaseMsg.LoginMsg userMsgSingle = BaseMsg.LoginMsg.newBuilder()
//								//消息id
//								.setId(SequenceIdGenerator.nextId())
//								//单聊single
//								.setType(MsgTypes.TYPE_LOGIN.getMsgType())
//								.setContent("发起聊天内容")
//								.setComputerName("个人电脑666")
//								.setComputerId(666)
//								.build();

//						BaseMsg.ResultMsg userMsgSingle = BaseMsg.ResultMsg.newBuilder()
//								//消息id
//								.setId(SequenceIdGenerator.nextId())
//								//ACK
//								.setType(MsgTypes.TYPE_RESULT.getMsgType())
//								.build();

						BaseMsg.HeartbeatMsg userMsgSingle = BaseMsg.HeartbeatMsg.newBuilder()
								.setId(SequenceIdGenerator.nextId())
								.setType(MsgTypes.TYPE_HEARTBEAT.getMsgType()).build();

						System.out.println("test0:"+futureListener.channel());
						System.out.println("test1");
						futureListener.channel().writeAndFlush(userMsgSingle);
						System.out.println("test3:"+userMsgSingle);
					}
				});
				if(initFalg){
					System.out.println("Netty客户端启动成功!");
					initFalg=false;
				}
				// 阻塞
				f.channel().closeFuture().sync();
			}
		} catch (Exception e) {
			System.out.println("客户端连接失败!"+e.getMessage());
		}

	}
}
