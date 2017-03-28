package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.TopicListBean;

import java.util.List;

/**
 * Created by codeest on 16/12/23.
 */

public interface VtexContract {

    interface View extends BaseView {

        void showContent(List<TopicListBean> mList);

    }

    interface Presenter extends BasePresenter<View> {

        void getContent(String type);

    }
}
