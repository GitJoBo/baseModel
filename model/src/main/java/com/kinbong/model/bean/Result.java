package com.kinbong.model.bean;

public class Result<T> {

    public int code ;
    public String msg;
    public T data;

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
