package com.chao.bookviki.model.bean;

/**
 * Created by codeest on 16/10/10.
 */

public class VersionBean {

    private String code;

    private String size;

    private String des;

    private String downloadUrl;

    public String getCode() {
        return code;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
