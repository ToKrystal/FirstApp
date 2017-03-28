package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.db.RealmHelper;
import com.chao.bookviki.presenter.contract.UserEditContract;

import javax.inject.Inject;

/**
 * Created by Jessica on 2017/3/25.
 */

public class UserEditPresenter extends RxPresenter<UserEditContract.View> implements UserEditContract.Presenter {
    private RealmHelper mRealmHelper;

    @Inject
    public UserEditPresenter(RealmHelper mRealHelper) {
        this.mRealmHelper = mRealmHelper;
    }

}
