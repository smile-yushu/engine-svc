package com.yushu.engine.hander;

import com.yushu.engine.pojo.entity.CommunicationProtocolLog;
import com.yushu.engine.protobuf.BaseMsg;
import com.yushu.engine.protocol.MsgTypes;
import com.yushu.engine.protocol.SequenceIdGenerator;
import com.yushu.engine.service.ICommunicationProtocolLogService;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ChannelHandler.Sharable
@Slf4j
public class EngineHandler extends SimpleChannelInboundHandler<BaseMsg.ResultMsg> {
    private final ICommunicationProtocolLogService service;

    public  EngineHandler(ICommunicationProtocolLogService service){
        this.service = service;
    }


//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        // 客户端连接成功时，获取客户端的 ChannelHandlerContext 对象
//        ChannelHandlerContext clientCtx = getClientCtx(ctx.channel());
//        // 使用客户端的 ChannelHandlerContext 对象发送消息
//        clientCtx.writeAndFlush(Unpooled.copiedBuffer("Hello, client!", CharsetUtil.UTF_8));
//    }

    private ChannelHandlerContext getClientCtx(Channel channel) {
//        ChannelGroup channels = service.getChannels();
//        return channels.stream()
//                .filter(ch -> ch.remoteAddress().equals(channel.remoteAddress()))
//                .findFirst()
//                .orElse(null);

        return null;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMsg.ResultMsg msg)
            throws Exception {
        log.info("2222");
        // 收到消息直接打印输出
        log.info("服务端接受的消息 : " + msg.getAllFields());
        if("quit".equals(msg)){//服务端断开的条件
            ctx.close();
        }
        CommunicationProtocolLog entity = new CommunicationProtocolLog();
//        entity.setRemark(msg.getAllFields());
//        service.save(entity);
        Date date=new Date();
        // 返回客户端消息
//        ctx.writeAndFlush(date+"\n");

        //server回复client消息ACK
        BaseMsg.ResultMsg userMsgServerAck = BaseMsg.ResultMsg.newBuilder()
                //消息id
                .setId(SequenceIdGenerator.nextId())
                //ACK
                .setType(MsgTypes.TYPE_RESULT.getMsgType())
                .build();
        ctx.channel().writeAndFlush(userMsgServerAck);
    }


    // 保存所有客户端的 ChannelHandlerContext 对象
    private final Map<String, ChannelHandlerContext> clientMap = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 客户端连接成功时，将客户端的 ChannelHandlerContext 对象加入到 Map 中
        String clientId = getClientId(ctx);
        log.info("连接的客户端地址:" + clientId);
        clientMap.put(clientId, ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 客户端连接断开时，将客户端的 ChannelHandlerContext 对象从 Map 中移除
        String clientId = getClientId(ctx);
        clientMap.remove(clientId);
    }

    public void sendMessageToClient(String clientId, String message) {
        // 获取客户端的 ChannelHandlerContext 对象，并使用它向客户端发送消息
        ChannelHandlerContext clientCtx = clientMap.get(clientId);
        if (clientCtx != null) {
            BaseMsg.ResultMsg userMsgServerAck = BaseMsg.ResultMsg.newBuilder()
                    //消息id
                    .setId(SequenceIdGenerator.nextId())
                    //ACK
                    .setType(MsgTypes.TYPE_RESULT.getMsgType())
                    .build();
            clientCtx.writeAndFlush(userMsgServerAck);
        }
    }

    private String getClientId(ChannelHandlerContext ctx) {
        // 根据客户端的 IP 地址和端口号生成客户端 ID
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        return address.getAddress().getHostAddress() + ":" + address.getPort();
    }


}

