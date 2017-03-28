package com.chao.bookviki.model.http.response;

/**
 * Created by Administrator on 2017/3/28.
 */

public class BookHttpResponse<T> {
    private T results;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
