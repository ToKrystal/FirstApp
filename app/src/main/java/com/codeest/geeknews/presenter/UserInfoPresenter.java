package com.codeest.geeknews.presenter;

import com.codeest.geeknews.base.RxPresenter;
import com.codeest.geeknews.model.db.RealmHelper;
import com.codeest.geeknews.presenter.contract.UserInfoContract;

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
