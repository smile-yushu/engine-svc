package com.yushu.engine.protocol;/**
 * @author HeShengjin 2356899074@qq.com
 * @Description TODO
 * @createTime 2021年07月29日 15:47:00
 */

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.yushu.engine.protobuf.BaseMsg;


import java.util.Iterator;
import java.util.Map;


public final class MessageUtil {

    public static Integer parseMessageFeildTypeFromProtobufMessage(GeneratedMessageV3 userMsgSingle) {
        Map<Descriptors.FieldDescriptor, Object> map = userMsgSingle.getAllFields();
        Iterator<Map.Entry<Descriptors.FieldDescriptor, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Descriptors.FieldDescriptor, Object> next = it.next();
            if (next.getKey().getJsonName().equals(MsgTypeFeilds.TYPE.getName())) {
                return (Integer) next.getValue();
            }
        }
        return null;
    }


    public static GeneratedMessageV3 parseMessageType2Message(int messageType, byte[] bytes) throws InvalidProtocolBufferException {
        if (MsgTypes.TYPE_LOGIN.getMsgType() == messageType) {
            return BaseMsg.LoginMsg.parseFrom(bytes);
        }
        if (MsgTypes.TYPE_QUIT.getMsgType() == messageType) {
            return BaseMsg.QuitMsg.parseFrom(bytes);
        }
        if (MsgTypes.TYPE_HEARTBEAT.getMsgType() == messageType) {
            return BaseMsg.HeartbeatMsg.parseFrom(bytes);
        }
        if (MsgTypes.TYPE_COMPUTER_MSG.getMsgType() == messageType) {
            return BaseMsg.ComputerMsg.parseFrom(bytes);
        }
        if (MsgTypes.TYPE_RESULT.getMsgType() == messageType) {
            return BaseMsg.ResultMsg.parseFrom(bytes);
        }
        return null;
    }
}
