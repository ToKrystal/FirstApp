package com.chao.bookviki.di.component;

import com.chao.bookviki.app.App;
import com.chao.bookviki.di.module.AppModule;
import com.chao.bookviki.di.module.HttpModule;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.db.RealmHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    App getContext();  // 提供App的Context

    RetrofitHelper retrofitHelper();  //提供http的帮助类

    RealmHelper realmHelper();    //提供数据库帮助类
}
