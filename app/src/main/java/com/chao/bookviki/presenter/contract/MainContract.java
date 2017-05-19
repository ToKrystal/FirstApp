package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.model.bean.LoginBean;
import com.chao.bookviki.model.bean.MyPushBean;
import com.tbruyelle.rxpermissions.RxPermissions;

/**
 * Created by codeest on 16/8/9.
 */

public interface MainContract {

    interface View extends BaseView {

        void showUpdateDialog(String versionContent,String downloadUrl);

        void startDownloadService(String downloadUrl);

        void jump2PushSucc(MyPushBean bean);


    }

    interface  Presenter extends BasePresenter<View> {

        void checkVersion(String currentVersion);

        void checkPermissions(RxPermissions rxPermissions,String downloadUrl);

        void doLogout();

        void deleteLoginBean(LoginBean bean);


        void queryLoginState();

        void postChannelIdNotLogin(String channelId);

        boolean queryIfLogin();
    }
}
