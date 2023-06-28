package com.yushu.engine.config;

import com.yushu.engine.hander.EngineHandler;
import com.yushu.engine.protocol.MessageCodecSharable;
import com.yushu.engine.protocol.ProcotolFrameDecoder;
import com.yushu.engine.server.NettyServer;
import com.yushu.engine.service.ICommunicationProtocolLogService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
@Slf4j
public class NettyServerConfig {
    @Value("${netty.server.port}")
    private int port;

    @Autowired
    private ICommunicationProtocolLogService service;

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        log.info("bossGroup");
        return new NioEventLoopGroup(1);
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        log.info("workerGroup");

        return new NioEventLoopGroup();
    }

    @Bean()
    public ServerBootstrap serverBootstrap() {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline ph = ch.pipeline();
                        // 以("\n")为结尾分割的 解码器
//                        ph.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
//                        // 解码和编码，应和客户端一致
//                        ph.addLast("decoder", new StringDecoder());
//                        ph.addLast("encoder", new StringEncoder());
                        ph.addLast("LengthFieldBasedFrameDecoder", new ProcotolFrameDecoder());
                        ph.addLast("decoc-protobuf",new MessageCodecSharable());
                        //业务逻辑实现类
//                        ph.addLast("UserMsgSingleServerHandler", new UserMsgSingleServerHandler());
                        ph.addLast("EngineHandler",EngineHandler()); // 添加自定义的处理器
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        log.info("serverBootstrap");

        return b;
    }

    @Bean
    public EngineHandler EngineHandler() {
        log.info("EngineHandler");

        return new EngineHandler(service);
    }

    public void echo(String s){
        EngineHandler().sendMessageToClient(s,"来自服务端的单播消息");
    }

    @Bean
    public InetSocketAddress tcpSocketAddress() {
        log.info("tcpSocketAddress");

        return new InetSocketAddress(port);
    }

    @Bean(initMethod = "bind", destroyMethod = "unbind")
    public NettyServer nettyServer() {
        log.info("nettyServer");

        return new NettyServer(serverBootstrap(), tcpSocketAddress());
    }
}

