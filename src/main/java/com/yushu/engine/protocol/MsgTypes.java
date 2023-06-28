package com.yushu.engine.protocol;

/**
 * 消息type指令类型等， 类型，1 = Login 、2 = Quit、3 = Heartbeat、4 = ComputerMsg、5 = ResultMsg
 */
public enum MsgTypes {

    TYPE_LOGIN(1,"Login"),
    TYPE_QUIT(2,"Quit"),
    TYPE_HEARTBEAT(3,"Heartbeat"),
    TYPE_COMPUTER_MSG(4,"ComputerMsg"),
    TYPE_RESULT(5,"ResultMsg");

    //指令
    //类型，1 = Login 、2 = Quit、3 = Heartbeat、4 = ComputerMsg、5 = ResultMsg
    private int msgType;
    private String msgTypeName;


    MsgTypes(int msgType, String msgTypeName) {
        this.msgType = msgType;
        this.msgTypeName = msgTypeName;
    }

    public String getMsgTypeName() {
        return msgTypeName;
    }

    public void setMsgTypeName(String msgTypeName) {
        this.msgTypeName = msgTypeName;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }
}
