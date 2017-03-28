package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.SectionListBean;
import com.chao.bookviki.base.BasePresenter;

/**
 * Created by codeest on 16/8/12.
 */

public interface SectionContract {

    interface View extends BaseView {

        void showContent(SectionListBean sectionListBean);

    }

    interface Presenter extends BasePresenter<View> {

        void getSectionData();
    }
}
