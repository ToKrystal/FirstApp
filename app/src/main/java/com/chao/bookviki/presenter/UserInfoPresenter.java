package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.db.RealmHelper;
import com.chao.bookviki.presenter.contract.UserInfoContract;

import javax.inject.Inject;

/**
 * Created by Jessica on 2017/3/25.
 */

public class UserInfoPresenter extends RxPresenter<UserInfoContract.View> implements UserInfoContract.Presenter {

    private RealmHelper mRealmHelper;


    @Inject
    public UserInfoPresenter(RealmHelper mRealHelper) {
        this.mRealmHelper = mRealmHelper;
    }

    @Override
    public void seteditData() {
        mView.jumpToEdit();
    }
}
