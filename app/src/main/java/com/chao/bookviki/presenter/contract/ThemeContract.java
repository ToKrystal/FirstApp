package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.ThemeListBean;

/**
 * Created by codeest on 16/8/12.
 */

public interface ThemeContract {

    interface View extends BaseView {

        void showContent(ThemeListBean themeListBean);
    }

    interface Presenter extends BasePresenter<View> {

        void getThemeData();
    }
}
