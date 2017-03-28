package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.model.bean.BookCommentBean;

/**
 * Created by Jessica on 2017/3/21.
 */

public interface BookCommentContract {
    interface View extends BaseView {

        void showContent(BookCommentBean bookCommentBean);

    }

    interface Presenter extends BasePresenter<View> {

        void getCommentData(int id,int commentKind);

    }
}
