package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.JingXuanNewsBean;

import java.util.List;

/**
 * Created by codeest on 16/8/20.
 */

public interface TechContract {

    interface View extends BaseView {

    //    void showContent(List<GankItemBean> mList);

   //     void showMoreContent(List<GankItemBean> mList);

        void showGirlImage(String url, String copyright);

        void showJingXuanItem(List<JingXuanNewsBean> beans);

        void showMoreJingXuanItem(List<JingXuanNewsBean> beans);
    }

    interface Presenter extends BasePresenter<View> {

     //   void getGankData(String tech, int type);

     //   void getMoreGankData(String tech);

        void getGirlImage();

        void getJingXuanNews(String type);

        void getMoreJingXuanNews(String type);

    }
}
