package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;

/**
 * Created by Jessica on 2017/3/21.
 */

public interface BookCommentContract {
    interface View extends BaseView {

      //  void showContent(BookCommentBean bookCommentBean);

    }

    interface Presenter extends BasePresenter<View> {

     //   void getCommentData(int id,int commentKind);

    }
}
