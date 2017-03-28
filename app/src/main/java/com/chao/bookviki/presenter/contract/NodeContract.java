package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.NodeBean;
import com.chao.bookviki.model.bean.NodeListBean;

import java.util.List;

/**
 * Created by codeest on 16/12/23.
 */

public interface NodeContract {

    interface View extends BaseView {

        void showContent(List<NodeListBean> mList);

        void showTopInfo(NodeBean mTopInfo);
    }

    interface Presenter extends BasePresenter<View> {

        void getContent(String node_name);

        void getTopInfo(String node_name);
    }
}
