package com.codeest.geeknews.presenter.contract;

import com.codeest.geeknews.base.BasePresenter;
import com.codeest.geeknews.base.BaseView;
import com.codeest.geeknews.model.bean.BookDetailBean;
import com.codeest.geeknews.model.bean.BookDetailExtraBean;
import com.codeest.geeknews.model.bean.NodeListBean;
import com.codeest.geeknews.model.bean.RepliesListBean;

import java.util.List;

/**
 * Created by Jessica on 2017/3/21.
 */

public interface BookDetailContract {
    interface View extends BaseView {

        void showContent(BookDetailBean bookDetailBean);

        void showExtraInfo(BookDetailExtraBean bookDetailExtraBean);

        void setLikeButtonState(boolean isLiked);

        void showContent(List<RepliesListBean> mList);

        void showTopInfo(NodeListBean mTopInfo);

    }

    interface  Presenter extends BasePresenter<View> {

        void getDetailData(int id);

        void getExtraData(int id);

        void insertLikeData();

        void deleteLikeData();

        void queryLikeData(int id);

        void getContent(String topic_id);

        void getTopInfo(String topic_id);
    }

}
