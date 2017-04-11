package com.chao.bookviki.base;

import com.chao.bookviki.model.bean.LoginBean;

/**
 *
 * View基类
 */
public interface BaseView {

    void showError(String msg);

    void useNightMode(boolean isNight);

    void showLogInInfo(LoginBean bean);

    void showLogOutInfo();

    void showDefaultUserInfo();


}
