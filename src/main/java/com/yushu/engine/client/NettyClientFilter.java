package com.yushu.engine.client;


import com.yushu.engine.hander.UserMsgServerAckClientHandler;
import com.yushu.engine.protocol.MessageCodecSharable;
import com.yushu.engine.protocol.ProcotolFrameDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class NettyClientFilter extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();

        // 解码和编码，应和客户端一致
        //传输的协议 Protobuf
        ph.addLast("LengthFieldBasedFrameDecoder", new ProcotolFrameDecoder());
        ph.addLast("decoc-protobuf",new MessageCodecSharable());
       
        //业务逻辑实现类
        ph.addLast("UserMsgServerAckClientHandler", new UserMsgServerAckClientHandler());
      
    }
}
