package com.chao.bookviki.model.bean;

/**
 * Created by Administrator on 2017/4/13.
 */

public class FollowBean {
    public String typeId;
    public boolean follow;

    public FollowBean(String typeId, boolean follow) {
        this.typeId = typeId;
        this.follow = follow;
    }
}
