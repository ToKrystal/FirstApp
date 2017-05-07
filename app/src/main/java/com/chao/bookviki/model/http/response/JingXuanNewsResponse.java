package com.chao.bookviki.model.http.response;

/**
 * Created by Jessica on 2017/5/7.
 */

public class JingXuanNewsResponse<T> {
    private T list;
    private int size;

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
