package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.LoginBean;

/**
 * Created by Jessica on 2017/4/4.
 */

public interface LoginContract {
    interface View extends BaseView {

        //  void showContent(BookCommentBean bookCommentBean);
        void jump2LoginSucc(LoginBean loginBean);

    }

    interface Presenter extends BasePresenter<View> {

        //   void getCommentData(int id,int commentKind);
        void postLogin(String name,String pass,String channelId);
        void insertLoginData(LoginBean bean);

    }
}
