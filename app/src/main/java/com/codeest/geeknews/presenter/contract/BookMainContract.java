package com.codeest.geeknews.presenter.contract;

import com.codeest.geeknews.base.BasePresenter;
import com.codeest.geeknews.base.BaseView;
import com.codeest.geeknews.model.bean.BookManagerItemBean;
import com.codeest.geeknews.model.bean.BookManagerBean;

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
