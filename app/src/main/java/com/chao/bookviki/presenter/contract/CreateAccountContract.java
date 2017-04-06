package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.CreateAccountBean;
import com.chao.bookviki.model.bean.LoginBean;

/**
 * Created by Jessica on 2017/4/5.
 */

public interface CreateAccountContract {

    interface View extends BaseView {

        void jump2CreateSucc(LoginBean bean);

    }

    interface Presenter extends BasePresenter<View> {

        void postCreateAccount(CreateAccountBean bean);
        void insertLoginBean();

    }
}
