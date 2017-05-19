package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.LoginBean;
import com.chao.bookviki.model.bean.PersonalInfoBean;

/**
 * Created by Jessica on 2017/3/25.
 */

public interface UserEditContract  {
    interface View extends BaseView {
        void showUpdateSuc();
        void showUserInfo(LoginBean bean);

    }

    /**
     * init tab data
     */
    interface Presenter extends BasePresenter<View> {
        void postUpdatePersonalInfo(PersonalInfoBean bean);
        LoginBean queryLoginBean();
        void updateLoginBean(LoginBean bean);
        void updateDesc(String str);
        void updateSigna(String str);
        void updateNick(String str);

    }
}
