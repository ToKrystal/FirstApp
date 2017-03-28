package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.GankItemBean;

import java.util.List;

/**
 * Created by codeest on 16/8/20.
 */

public interface TechContract {

    interface View extends BaseView {

        void showContent(List<GankItemBean> mList);

        void showMoreContent(List<GankItemBean> mList);

        void showGirlImage(String url, String copyright);
    }

    interface Presenter extends BasePresenter<View> {

        void getGankData(String tech, int type);

        void getMoreGankData(String tech);

        void getGirlImage();

    }
}
