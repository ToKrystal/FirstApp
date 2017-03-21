package com.codeest.geeknews.presenter.contract;

import com.codeest.geeknews.base.BasePresenter;
import com.codeest.geeknews.base.BaseView;
import com.codeest.geeknews.model.bean.BookDetailBean;
import com.codeest.geeknews.model.bean.BookDetailExtraBean;

/**
 * Created by Jessica on 2017/3/21.
 */

public interface BookDetailContract {
    interface View extends BaseView {

        void showContent(BookDetailBean bookDetailBean);

        void showExtraInfo(BookDetailExtraBean bookDetailExtraBean);

        void setLikeButtonState(boolean isLiked);
    }

    interface  Presenter extends BasePresenter<View> {

        void getDetailData(int id);

        void getExtraData(int id);

        void insertLikeData();

        void deleteLikeData();

        void queryLikeData(int id);
    }

}
