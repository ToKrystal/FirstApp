package com.codeest.geeknews.presenter;

import com.codeest.geeknews.base.RxPresenter;
import com.codeest.geeknews.model.db.RealmHelper;
import com.codeest.geeknews.presenter.contract.UserEditContract;

import javax.inject.Inject;

/**
 * Created by Jessica on 2017/3/25.
 */

public class UserEditPresenter extends RxPresenter<UserEditContract.View> implements UserEditContract.Presenter{
    private RealmHelper mRealmHelper;

    @Inject
    public UserEditPresenter(RealmHelper mRealHelper) {
        this.mRealmHelper = mRealmHelper;
    }

}
