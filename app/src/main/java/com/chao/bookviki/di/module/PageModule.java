package com.chao.bookviki.di.module;

import com.chao.bookviki.ui.userinfo.fragment.UserInfoFragment;
import com.chao.bookviki.ui.gank.fragment.GankMainFragment;
import com.chao.bookviki.ui.main.fragment.AboutFragment;
import com.chao.bookviki.ui.main.fragment.LikeFragment;
import com.chao.bookviki.ui.main.fragment.SettingFragment;
import com.chao.bookviki.ui.zhihu.fragment.ZhihuMainFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by codeest on 2017/2/26.
 */

@Module
public class PageModule {

    @Singleton
    @Provides
    ZhihuMainFragment provideZhihuMain() {
        return new ZhihuMainFragment();
    }

    @Singleton
    @Provides
    GankMainFragment provideGankMain() {
        return new GankMainFragment();
    }

    @Singleton
    @Provides
    UserInfoFragment provideUserInfo() {
        return new UserInfoFragment();
    }


    @Singleton
    @Provides
    LikeFragment provideLikeMain() {
        return new LikeFragment();
    }

    @Singleton
    @Provides
    SettingFragment provideSettingMain() {
        return new SettingFragment();
    }

    @Singleton
    @Provides
    AboutFragment provideAboutMain() {
        return new AboutFragment();
    }
}
