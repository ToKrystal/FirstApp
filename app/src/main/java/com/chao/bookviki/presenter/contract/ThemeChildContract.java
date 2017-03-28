package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.model.bean.ThemeChildListBean;

/**
 * Created by codeest on 16/8/24.
 */

public interface ThemeChildContract {

    interface View extends BaseView {

        void showContent(ThemeChildListBean themeChildListBean);

    }

    interface Presenter extends BasePresenter<View> {

        void getThemeChildData(int id);

        void insertReadToDB(int id);
    }
}
