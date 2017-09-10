package com.shen.joke.core;

public class HttpResult<T> {


    public static final int REQ_SUCC = 1;
    public static final int REQ_FAIL = 0;
    public static final int REQ_EXCE = -1;

    private int code;
    private String msg;
    private T data;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
