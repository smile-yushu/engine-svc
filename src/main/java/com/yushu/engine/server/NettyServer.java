package com.yushu.engine.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
@Component
@Slf4j
public class NettyServer {
    private final ServerBootstrap serverBootstrap;
    private final InetSocketAddress tcpPort;



    private Channel serverChannel;

    public NettyServer(ServerBootstrap serverBootstrap, InetSocketAddress tcpPort) {
        this.serverBootstrap = serverBootstrap;
        this.tcpPort = tcpPort;
    }

    public void bind() throws Exception {
        log.info("NettyServer 端口："+tcpPort+" 启动");
        serverChannel = serverBootstrap.bind(tcpPort).sync().channel();
    }

    public void unbind() {
        if (serverChannel != null) {
            log.info("NettyServer 端口："+tcpPort+" 关闭");
            serverChannel.close();
        }
    }

}

