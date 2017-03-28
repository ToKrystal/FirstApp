package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;

/**
 * Created by Jessica on 2017/3/25.
 */

public interface UserInfoContract {
    interface View extends BaseView {
        void showContent();
        void jumpToEdit( );


    }

    /**
     * init tab data
     */
    interface Presenter extends BasePresenter<View> {
        void seteditData();

    }
}
