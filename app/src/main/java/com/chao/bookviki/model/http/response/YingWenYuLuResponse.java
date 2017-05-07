package com.chao.bookviki.model.http.response;

/**
 * Created by Jessica on 2017/5/7.
 */

public class YingWenYuLuResponse<K> {
    private int showapi_res_code;
    private String showapi_res_error;
    private BodyResponse<K> showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public BodyResponse<K> getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(BodyResponse<K> showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class BodyResponse<K>{
        private int ret_code;
        private String ret_message;
        private K data;

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public String getRet_message() {
            return ret_message;
        }

        public void setRet_message(String ret_message) {
            this.ret_message = ret_message;
        }

        public K getData() {
            return data;
        }

        public void setData(K data) {
            this.data = data;
        }
    }
}
