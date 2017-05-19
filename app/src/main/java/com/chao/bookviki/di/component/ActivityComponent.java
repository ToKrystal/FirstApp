package com.chao.bookviki.di.component;

import android.app.Activity;

import com.chao.bookviki.di.module.ActivityModule;
import com.chao.bookviki.di.scope.ActivityScope;
import com.chao.bookviki.ui.gold.activity.BookDetailActivity;
import com.chao.bookviki.ui.gold.activity.BookFollowActivity;
import com.chao.bookviki.ui.main.activity.MainActivity;
import com.chao.bookviki.ui.main.activity.WelcomeActivity;
import com.chao.bookviki.ui.userinfo.activity.LoginActivity;
import com.chao.bookviki.ui.userinfo.activity.CreateAccountActivity;
import com.chao.bookviki.ui.userinfo.activity.UserEditActivity;

import dagger.Component;



@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(WelcomeActivity welcomeActivity);

    void inject(MainActivity mainActivity);

    void inject(BookDetailActivity bookDetailActivity);

    void inject(UserEditActivity userEditActivity);

    void inject(LoginActivity loginActivity);

    void inject(CreateAccountActivity createAccountActivity);

    void inject(BookFollowActivity bookFollowActivity);
}
