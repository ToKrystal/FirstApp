package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.SectionChildListBean;

/**
 * Created by codeest on 16/8/28.
 */

public interface SectionChildContract {

    interface View extends BaseView {

        void showContent(SectionChildListBean sectionChildListBean);
    }

    interface Presenter extends BasePresenter<View> {

        void getThemeChildData(int id);

        void insertReadToDB(int id);
    }
}
