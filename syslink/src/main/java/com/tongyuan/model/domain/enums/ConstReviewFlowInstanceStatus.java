package com.tongyuan.model.domain.enums;

public interface ConstReviewFlowInstanceStatus {
    public static final Byte INUSE = new Byte("1");
    public static final Byte  REFUSE= new Byte("2");
    public static final Byte  FINISH= new Byte("3");
    public static final Byte CANCEL = new Byte("4");
}
