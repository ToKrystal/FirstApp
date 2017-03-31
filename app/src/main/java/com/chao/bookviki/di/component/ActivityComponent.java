package com.chao.bookviki.di.component;

import android.app.Activity;

import com.chao.bookviki.di.module.ActivityModule;
import com.chao.bookviki.di.scope.ActivityScope;
import com.chao.bookviki.ui.gold.activity.BookCommentActivity;
import com.chao.bookviki.ui.gold.activity.BookDetailActivity;
import com.chao.bookviki.ui.main.activity.MainActivity;
import com.chao.bookviki.ui.main.activity.WelcomeActivity;
import com.chao.bookviki.ui.userinfo.activity.UserEditActivity;
import com.chao.bookviki.ui.vtex.activity.NodeListActivity;
import com.chao.bookviki.ui.vtex.activity.RepliesActivity;
import com.chao.bookviki.ui.zhihu.activity.SectionActivity;
import com.chao.bookviki.ui.zhihu.activity.ThemeActivity;
import com.chao.bookviki.ui.zhihu.activity.ZhihuDetailActivity;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(WelcomeActivity welcomeActivity);

    void inject(MainActivity mainActivity);

    void inject(ZhihuDetailActivity zhihuDetailActivity);

    void inject(ThemeActivity themeActivity);

    void inject(SectionActivity sectionActivity);

    void inject(RepliesActivity repliesActivity);

    void inject(NodeListActivity nodeListActivity);

    void inject(BookDetailActivity bookDetailActivity);

    void inject(BookCommentActivity bookCommentActivity);

    void inject(UserEditActivity userEditActivity);
}