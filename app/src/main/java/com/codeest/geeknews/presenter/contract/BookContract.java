package com.codeest.geeknews.presenter.contract;

import com.codeest.geeknews.base.BasePresenter;
import com.codeest.geeknews.base.BaseView;
import com.codeest.geeknews.model.bean.BookListBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */

public interface BookContract {
    interface View extends BaseView {

        void showContent(List<BookListBean> bookListBean);

        void showMoreContent(List<BookListBean> bookMoreListBean, int start, int end);
    }

    interface Presenter extends BasePresenter<View> {

        void getBookData(String type);

        void getMoreBookData();
    }
}
