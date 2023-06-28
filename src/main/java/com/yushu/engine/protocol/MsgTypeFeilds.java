package com.yushu.engine.protocol;


public enum MsgTypeFeilds {
    //字段域type
    TYPE("type");

    private String name;

    MsgTypeFeilds(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
