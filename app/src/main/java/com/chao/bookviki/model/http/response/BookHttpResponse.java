package com.chao.bookviki.model.http.response;

/**
 * Created by Administrator on 2017/3/28.
 */

public class BookHttpResponse<T> {
    private T results;
    private int status;
    private String msg;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
