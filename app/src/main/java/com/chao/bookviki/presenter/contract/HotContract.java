package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.model.bean.HotListBean;

/**
 * Created by codeest on 16/8/12.
 */

public interface HotContract {

    interface View extends BaseView {

        void showContent(HotListBean hotListBean);
    }

    interface Presenter extends BasePresenter<View> {

        void getHotData();

        void insertReadToDB(int id);

    }
}
