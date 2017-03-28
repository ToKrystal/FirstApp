package com.codeest.geeknews.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Administrator on 2017/3/28.
 */

public class BookManagerBean extends RealmObject implements Parcelable {

    public BookManagerBean() {

    }

    private RealmList<BookManagerItemBean> managerList;

    public RealmList<BookManagerItemBean> getManagerList() {
        return managerList;
    }

    public void setManagerList(RealmList<BookManagerItemBean> managerList) {
        this.managerList = managerList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.managerList);
    }

    protected BookManagerBean(Parcel in) {
        this.managerList = new RealmList<>();
        in.readList(this.managerList, GoldManagerItemBean.class.getClassLoader());
    }

    public BookManagerBean(RealmList<BookManagerItemBean> mList) {
        this.managerList = mList;
    }

    public static final Parcelable.Creator<BookManagerBean> CREATOR = new Parcelable.Creator<BookManagerBean>() {
        @Override
        public BookManagerBean createFromParcel(Parcel source) {
            return new BookManagerBean(source);
        }

        @Override
        public BookManagerBean[] newArray(int size) {
            return new BookManagerBean[size];
        }
    };
}

