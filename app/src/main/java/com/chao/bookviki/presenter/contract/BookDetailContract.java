package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.BookDetailBean;
import com.chao.bookviki.model.bean.BookDetailExtraBean;
import com.chao.bookviki.model.bean.RepliesListBean;
import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.model.bean.NodeListBean;

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
