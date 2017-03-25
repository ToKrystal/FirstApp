package com.codeest.geeknews.presenter.contract;

import com.codeest.geeknews.base.BasePresenter;
import com.codeest.geeknews.base.BaseView;

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
