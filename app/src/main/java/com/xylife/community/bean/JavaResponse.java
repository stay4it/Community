package com.xylife.community.bean;

public class JavaResponse<T> extends Response{

    public int infoCode;
    public String message;
    public T data;

    public boolean isSuccess() {
        return infoCode == 200;
    }

}
