package com.chao.bookviki.di.module;

import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.api.JingXuanNewsApis;
import com.chao.bookviki.app.App;
import com.chao.bookviki.model.db.RealmHelper;
import com.chao.bookviki.model.http.api.BookApis;
import com.chao.bookviki.model.http.api.YingWenYuLuApis;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }

    //提供Retrofit
    @Provides
    @Singleton
    RetrofitHelper provideRetrofitHelper(
                                         BookApis bookApis, JingXuanNewsApis jingXuanNewsApis, YingWenYuLuApis yingWenYuLuApis) {
        return new RetrofitHelper(
               bookApis,jingXuanNewsApis,yingWenYuLuApis);
    }


    //提供数据库
    @Provides
    @Singleton
    RealmHelper provideRealmHelper() {
        return new RealmHelper(application);
    }
}
