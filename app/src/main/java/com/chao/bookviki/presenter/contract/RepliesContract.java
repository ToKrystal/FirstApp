package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.NodeListBean;
import com.chao.bookviki.model.bean.RealmLikeBean;
import com.chao.bookviki.model.bean.RepliesListBean;

import java.util.List;

/**
 * Created by codeest on 16/12/23.
 */

public interface RepliesContract {

    interface View extends BaseView {

        void showContent(List<RepliesListBean> mList);

        void showTopInfo(NodeListBean mTopInfo);
    }

    interface Presenter extends BasePresenter<View> {

        void getContent(String topic_id);

        void getTopInfo(String topic_id);

        void insert(RealmLikeBean bean);

        void delete(String id);

        boolean query(String id);
    }
}
