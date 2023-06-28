package com.yushu.engine.protocol;


public enum SerializeType {

    PROTOBUF(0);

    //字节的序列化方式 protobuf 0
    private int serializeType;


    SerializeType(int serializeType) {
        this.serializeType = serializeType;
    }

    public int getSerializeType() {
        return serializeType;
    }

    public void setSerializeType(int serializeType) {
        this.serializeType = serializeType;
    }


}
