package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.YingWenYuLuBean;

import java.util.List;

/**
 * Created by codeest on 16/8/20.
 */

public interface YingWenYuLuContract {

    interface View extends BaseView {

        void showYingWenYuLus(List<YingWenYuLuBean> beans);

        void showMoreYinWenYuLus(List<YingWenYuLuBean> beans);

        void showImage(String url,String name);
    }

    interface Presenter extends BasePresenter<View> {

        void getYingWenYuLu(int count,int appId,String sign,boolean showMore);

        void getRandomImage();

    }
}
