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

    public LoginBean(String name, String icon_url) {
        this.name = name;
        this.icon_url = icon_url;
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
}
