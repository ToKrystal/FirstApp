package com.chao.bookviki.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2017/3/28.
 */

public class BookManagerItemBean extends RealmObject implements Parcelable {



    private int index;

    private boolean isSelect;

    private String typeId;

    public BookManagerItemBean() {

    }

    public BookManagerItemBean(int index, boolean isSelect,String typeId) {
        this.index = index;
        this.isSelect = isSelect;
        this.typeId = typeId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean getIsSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.index);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeString(this.typeId);
    }

    protected BookManagerItemBean(Parcel in) {
        this.index = in.readInt();
        this.isSelect = in.readByte() != 0;
        this.typeId = in.readString();
    }

    public static final Creator<BookManagerItemBean> CREATOR = new Creator<BookManagerItemBean>() {
        @Override
        public BookManagerItemBean createFromParcel(Parcel source) {
            return new BookManagerItemBean(source);
        }

        @Override
        public BookManagerItemBean[] newArray(int size) {
            return new BookManagerItemBean[size];
        }
    };
}
