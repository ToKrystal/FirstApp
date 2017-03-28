package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.VersionBean;

/**
 * Created by codeest on 16/10/17.
 */

public interface SettingContract {

    interface View extends BaseView {

        void showUpdateDialog(VersionBean bean);

    }

    interface  Presenter extends BasePresenter<View> {

        void checkVersion(String currentVersion);

    }
}
