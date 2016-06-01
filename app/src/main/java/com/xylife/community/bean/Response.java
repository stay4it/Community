package com.xylife.community.bean;


public class Response<T> {

    public boolean isSuccess() {
        return error_code == 0;
    }


    public int total;
    public int error_code;
    public String reason;
    public T result;
}
