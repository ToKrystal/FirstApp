package com.chao.bookviki.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Jessica on 2017/4/4.
 */

public class LoginBean  extends RealmObject implements Parcelable {
    @PrimaryKey
    public String name;
    public String icon_url;
    public String nickName;
    public String signUpName;
    public String simpleDesc;
    public int replayNum;
    public int followNum;

    public LoginBean(String name, String icon_url, String nickName, String signUpName, String simpleDesc, int replayNum, int followNum) {
        this.name = name;
        this.icon_url = icon_url;
        this.nickName = nickName;
        this.signUpName = signUpName;
        this.simpleDesc = simpleDesc;
        this.replayNum = replayNum;
        this.followNum = followNum;
    }

    public LoginBean(){}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.icon_url);
        dest.writeString(this.nickName);
        dest.writeString(this.signUpName);
        dest.writeString(this.simpleDesc);
        dest.writeInt(this.replayNum);
        dest.writeInt(this.followNum);

    }
    public static final Creator<LoginBean> CREATOR = new Creator<LoginBean>() {
        @Override
        public LoginBean createFromParcel(Parcel source) {
            return new LoginBean(source);
        }

        @Override
        public LoginBean[] newArray(int size) {
            return new LoginBean[size];
        }
    };
    protected LoginBean(Parcel in) {

        this.name = in.readString();
        this.icon_url = in.readString();
        this.nickName = in.readString();
        this.signUpName = in.readString();
        this.simpleDesc = in.readString();
        this.replayNum = in.readInt();
        this.followNum = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSignUpName() {
        return signUpName;
    }

    public void setSignUpName(String signUpName) {
        this.signUpName = signUpName;
    }

    public String getSimpleDesc() {
        return simpleDesc;
    }

    public void setSimpleDesc(String simpleDesc) {
        this.simpleDesc = simpleDesc;
    }

    public int getReplayNum() {
        return replayNum;
    }

    public void setReplayNum(int replayNum) {
        this.replayNum = replayNum;
    }

    public int getFollowNum() {
        return followNum;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }
}
