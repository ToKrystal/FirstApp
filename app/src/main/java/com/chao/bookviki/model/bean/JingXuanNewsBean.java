package com.chao.bookviki.model.bean;

/**
 * Created by Jessica on 2017/5/7.
 */


public class JingXuanNewsBean {
    /**
     * "imgurl": "http://cms-bucket.nosdn.127.net/4427c8ee04774c2d8f6c2d7e7c3dd1ae20170507141128.jpeg",
     "has_content": true,
     "docurl": "http://war.163.com/17/0507/14/CJRCTTLR000181KT.html",
     "id": 3251,
     "time": "2017-05-07 14:11:28",
     "title": "俄推迟开通对朝航线 专家：需与中国协调对朝政策",
     "channelname": "war"
     */
    private String imgurl;
    private boolean has_content;
    private String docurl;
    private int id;
    private String time;
    private String title;
    private String channelname;



    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public boolean isHas_content() {
        return has_content;
    }

    public void setHas_content(boolean has_content) {
        this.has_content = has_content;
    }

    public String getDocurl() {
        return docurl;
    }

    public void setDocurl(String docurl) {
        this.docurl = docurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    @Override
    public String toString() {
        return "JingXuanNewsBean{" +
                "imgurl='" + imgurl + '\'' +
                ", has_content=" + has_content +
                ", docurl='" + docurl + '\'' +
                ", id=" + id +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", channelname='" + channelname + '\'' +
                '}';
    }
}
