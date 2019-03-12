package com.lg.commons.base.enums;

public enum TypeEnum {
    //1 创建订单 2 创建静态页面 3创建索引
    CREATE_ORDER(1, "create order"),

    CREATE_PAGE(2, "create page"),

    CREATE_INDEX(3, "create index");

    private int code;
    private String value;


    TypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getCode() {
        return code;
    }
}
