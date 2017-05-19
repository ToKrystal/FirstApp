package com.chao.bookviki.di.module;

import com.chao.bookviki.ui.best.fragment.BestMainFragment;
import com.chao.bookviki.ui.main.fragment.AboutFragment;
import com.chao.bookviki.ui.main.fragment.LikeFragment;
import com.chao.bookviki.ui.main.fragment.SettingFragment;
import com.chao.bookviki.ui.userinfo.fragment.UserInfoFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class PageModule {



    @Singleton
    @Provides
    BestMainFragment provideGankMain() {
        return new BestMainFragment();
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
