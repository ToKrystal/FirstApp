package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.model.bean.RealmLikeBean;

import java.util.List;

/**
 * Created by codeest on 2016/8/23.
 */
public interface LikeContract {

    interface View extends BaseView {

        void showContent(List<RealmLikeBean> mList);
    }

    interface Presenter extends BasePresenter<View> {

        void getLikeData();

        void deleteLikeData(String id);

        void changeLikeTime(String id,long time,boolean isPlus);
    }
}
