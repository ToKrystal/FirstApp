package com.chao.bookviki.di.module;

import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.api.VtexApis;
import com.chao.bookviki.app.App;
import com.chao.bookviki.model.db.RealmHelper;
import com.chao.bookviki.model.http.api.BookApis;
import com.chao.bookviki.model.http.api.GankApis;
import com.chao.bookviki.model.http.api.GoldApis;
import com.chao.bookviki.model.http.api.MyApis;
import com.chao.bookviki.model.http.api.ZhihuApis;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by codeest on 16/8/7.
 */

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
    RetrofitHelper provideRetrofitHelper(ZhihuApis zhihuApiService, GankApis gankApiService,
                                         MyApis myApiService, GoldApis goldApiService, VtexApis vtexApiService, BookApis bookApis) {
        return new RetrofitHelper(zhihuApiService, gankApiService,
                myApiService, goldApiService, vtexApiService,bookApis);
    }


    //提供数据库
    @Provides
    @Singleton
    RealmHelper provideRealmHelper() {
        return new RealmHelper(application);
    }
}
