package com.chao.bookviki.di.component;

import android.app.Activity;

import com.chao.bookviki.di.module.FragmentModule;
import com.chao.bookviki.di.scope.FragmentScope;
import com.chao.bookviki.ui.best.fragment.TechFragment;
import com.chao.bookviki.ui.best.fragment.YingWenYuLuFragment;
import com.chao.bookviki.ui.daily.fragment.BookCommentFragment;
import com.chao.bookviki.ui.daily.fragment.BookMainFragment;
import com.chao.bookviki.ui.daily.fragment.BookPagerFragment;
import com.chao.bookviki.ui.main.fragment.LikeFragment;
import com.chao.bookviki.ui.main.fragment.SettingFragment;
import com.chao.bookviki.ui.userinfo.fragment.UserInfoFragment;

import dagger.Component;


@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(TechFragment techFragment);

    void inject(LikeFragment likeFragment);

    void inject(SettingFragment settingFragment);

    void inject(BookCommentFragment bookCommentFragment);

    void inject(UserInfoFragment userInfoFragment);

    void inject(BookPagerFragment bookPagerFragment);

    void inject(BookMainFragment bookMainFragment);

    void inject(YingWenYuLuFragment yingWenYuLuFragment);



}
