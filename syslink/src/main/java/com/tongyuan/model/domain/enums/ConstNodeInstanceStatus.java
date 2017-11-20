package com.tongyuan.model.domain.enums;

public interface ConstNodeInstanceStatus {
    public static final Byte NOT_ACTIVE = new Byte("1");
    public static final Byte ACTIVE = new Byte("2");
    public static final Byte AGREE = new Byte("3");
    public static final Byte NOT_AGREE = new Byte("4");

    public static final Byte NOT_ACTIVE_AND_CANCEL = new Byte("5");
    public static final Byte ACTIVE_AND_CANCEL = new Byte("6");
    public static final Byte AGREE_AND_CANCEL = new Byte("7");
    public static final Byte NOT_AGREE_AND_CANCEL = new Byte("8");
}
