package com.chao.bookviki.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 主题信息
 */

public class BookListBean implements Parcelable {
    private String objectId;

    private String createdAt;

    private String title;

    private int collectionCount;

    private int commentsCount;

    private String url;

    private GoldListUserBean user;

    private GoldListScreenshotBean screenshot;

    public BookListBean(){}
    protected BookListBean(Parcel in){
        this.objectId = in.readString();
        this.createdAt = in.readString();
        this.title = in.readString();
        this.collectionCount = in.readInt();
        this.commentsCount = in.readInt();
        this.url = in.readString();
        this.user = in.readParcelable(getClass().getClassLoader());
        this.screenshot = in.readParcelable(getClass().getClassLoader());


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.objectId);
        dest.writeString(this.createdAt);
        dest.writeString(this.title);
        dest.writeInt(this.collectionCount);
        dest.writeInt(this.commentsCount);
        dest.writeString(this.url);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.screenshot, flags);
    }

    public static final Creator<BookListBean> CREATOR = new Creator<BookListBean>() {
        @Override
        public BookListBean createFromParcel(Parcel source) {
            return new BookListBean(source);
        }

        @Override
        public BookListBean[] newArray(int size) {
            return new BookListBean[size];
        }
    };


    public static class GoldListUserBean implements Parcelable{
        public GoldListUserBean(){}
        private String username;


        protected GoldListUserBean(Parcel in) {
            this.username = in.readString();
        }
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            return " "+username+" ";
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.username);

        }

        public static final Creator<GoldListUserBean> CREATOR = new Creator<GoldListUserBean>() {
            @Override
            public GoldListUserBean createFromParcel(Parcel source) {
                return new GoldListUserBean(source);
            }

            @Override
            public GoldListUserBean[] newArray(int size) {
                return new GoldListUserBean[size];
            }
        };
    }

    public static class GoldListScreenshotBean implements Parcelable{
        public GoldListScreenshotBean(){}
        protected GoldListScreenshotBean(Parcel in) {
            this.url = in.readString();
        }
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return " "+url+" ";
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.url);
        }
        public static final Creator<GoldListScreenshotBean> CREATOR = new Creator<GoldListScreenshotBean>() {
            @Override
            public GoldListScreenshotBean createFromParcel(Parcel source) {
                return new GoldListScreenshotBean(source);
            }

            @Override
            public GoldListScreenshotBean[] newArray(int size) {
                return new GoldListScreenshotBean[size];
            }
        };
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public GoldListScreenshotBean getScreenshot() {
        return screenshot;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public GoldListUserBean getUser() {
        return user;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setScreenshot(GoldListScreenshotBean screenshot) {
        this.screenshot = screenshot;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUser(GoldListUserBean user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return " " +objectId +" " + createdAt +" " + title +" " + collectionCount +" " + commentsCount +" " + url  +" " + user +" " + screenshot +" " ;
    }
}
