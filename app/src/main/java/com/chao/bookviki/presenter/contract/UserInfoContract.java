package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.LoginBean;

/**
 * Created by Jessica on 2017/3/25.
 */

public interface UserInfoContract {
    interface View extends BaseView {
        void showContent(LoginBean bean);
        void jumpToEdit( );


    }

    /**
     * init tab data
     */
    interface Presenter extends BasePresenter<View> {
        void seteditData();
        void queryLoginBean();

    }
}
