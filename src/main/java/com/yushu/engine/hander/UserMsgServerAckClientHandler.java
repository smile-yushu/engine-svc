package com.yushu.engine.hander;

import com.yushu.engine.protobuf.BaseMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@ChannelHandler.Sharable
@Slf4j
public class UserMsgServerAckClientHandler extends SimpleChannelInboundHandler<BaseMsg.ResultMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMsg.ResultMsg msg) throws Exception {
        //自动释放ByteBuf
         log.info("接受到服务器ACK的protobuf消息，{}",msg.getAllFields());
    }
}