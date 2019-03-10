package com.lg.commons.base.enums;

public enum TypeEnum {
    CREATE_ORDER(1, "create order"),

    CREATE_PAGE(2, "create page");

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
