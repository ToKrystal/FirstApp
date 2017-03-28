package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.BookManagerItemBean;
import com.chao.bookviki.model.bean.BookManagerBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */

public interface BookMainContract {
    interface View extends BaseView {

        void updateTab(List<BookManagerItemBean> mList);

        void jumpToManager(BookManagerBean mBean);
    }

    /**
     * init tab data
     */
    interface Presenter extends BasePresenter<View> {

        void initManagerList();

        void setManagerList();
    }
}
