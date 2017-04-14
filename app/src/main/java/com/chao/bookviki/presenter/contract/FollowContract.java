package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;

/**
 * Created by Administrator on 2017/4/14.
 */

public interface FollowContract {
    interface View extends BaseView {

        void showFollowSuc();



    }


    interface  Presenter extends BasePresenter<View> {
        void postFollow(String objectId);


    }

}
