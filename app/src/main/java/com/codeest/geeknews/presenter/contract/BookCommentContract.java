package com.codeest.geeknews.presenter.contract;

import com.codeest.geeknews.base.BasePresenter;
import com.codeest.geeknews.base.BaseView;
import com.codeest.geeknews.model.bean.BookCommentBean;

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
