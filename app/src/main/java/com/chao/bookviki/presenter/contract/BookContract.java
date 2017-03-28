package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.BookListBean;

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
