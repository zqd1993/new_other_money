package com.terdfgdfjieyiji.jghrstyfghtertry.entity;

public class BaseEntity<T> {
    private int code;

    private String msg;

    private T data;

    private GoodsEntity top;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public GoodsEntity getTop() {
        return top;
    }

    public void setTop(GoodsEntity top) {
        this.top = top;
    }
}
