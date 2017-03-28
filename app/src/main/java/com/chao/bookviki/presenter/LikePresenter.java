package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.presenter.contract.LikeContract;
import com.chao.bookviki.model.db.RealmHelper;

import javax.inject.Inject;

/**
 * Created by codeest on 2016/8/23.
 */
public class LikePresenter extends RxPresenter<LikeContract.View> implements LikeContract.Presenter {

    private RealmHelper mRealmHelper;

    @Inject
    public LikePresenter(RealmHelper mRealmHelper) {
        this.mRealmHelper = mRealmHelper;
    }

    @Override
    public void getLikeData() {
        mView.showContent(mRealmHelper.getLikeList());
    }

    @Override
    public void deleteLikeData(String id) {
        mRealmHelper.deleteLikeBean(id);
    }

    @Override
    public void changeLikeTime(String id, long time, boolean isPlus) {
        mRealmHelper.changeLikeTime(id,time,isPlus);
    }
}
