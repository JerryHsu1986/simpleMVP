package com.haoche51.mvp.entity;

/**
 * @author HaiboXu on 2018/10/17
 * @github https://github.com/JerryHsu1986
 */
public class ResponseEntity<T> {


    private int errno; //错误码
    private String errmsg; // 错误信息
    private T data; // 返回数据


    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
