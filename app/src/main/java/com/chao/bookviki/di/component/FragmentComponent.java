package com.chao.bookviki.di.component;

import android.app.Activity;

import com.chao.bookviki.di.module.FragmentModule;
import com.chao.bookviki.di.scope.FragmentScope;
import com.chao.bookviki.ui.gank.fragment.GirlFragment;
import com.chao.bookviki.ui.gank.fragment.TechFragment;
import com.chao.bookviki.ui.gank.fragment.YingWenYuLuFragment;
import com.chao.bookviki.ui.gold.fragment.BookCommentFragment;
import com.chao.bookviki.ui.gold.fragment.BookMainFragment;
import com.chao.bookviki.ui.gold.fragment.BookPagerFragment;
import com.chao.bookviki.ui.main.fragment.LikeFragment;
import com.chao.bookviki.ui.main.fragment.SettingFragment;
import com.chao.bookviki.ui.userinfo.fragment.UserInfoFragment;
import com.chao.bookviki.ui.zhihu.fragment.CommentFragment;
import com.chao.bookviki.ui.zhihu.fragment.DailyFragment;
import com.chao.bookviki.ui.zhihu.fragment.HotFragment;
import com.chao.bookviki.ui.zhihu.fragment.SectionFragment;
import com.chao.bookviki.ui.zhihu.fragment.ThemeFragment;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(DailyFragment dailyFragment);

    void inject(ThemeFragment themeFragment);

    void inject(SectionFragment sectionFragment);

    void inject(HotFragment hotFragment);

    void inject(CommentFragment longCommentFragment);

    void inject(TechFragment techFragment);

    void inject(GirlFragment girlFragment);

    void inject(LikeFragment likeFragment);

    void inject(SettingFragment settingFragment);


    void inject(BookCommentFragment bookCommentFragment);

    void inject(UserInfoFragment userInfoFragment);

    void inject(BookPagerFragment bookPagerFragment);

    void inject(BookMainFragment bookMainFragment);

    void inject(YingWenYuLuFragment yingWenYuLuFragment);



}
