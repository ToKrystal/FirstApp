package com.codeest.geeknews.presenter;

import com.codeest.geeknews.base.RxPresenter;
import com.codeest.geeknews.model.bean.BookManagerBean;
import com.codeest.geeknews.model.bean.BookManagerItemBean;
import com.codeest.geeknews.model.db.RealmHelper;
import com.codeest.geeknews.presenter.contract.BookMainContract;
import com.codeest.geeknews.util.SharedPreferenceUtil;

import javax.inject.Inject;

import io.realm.RealmList;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/3/28.
 */

public class BookMainPresenter extends RxPresenter<BookMainContract.View> implements BookMainContract.Presenter{

    private RealmHelper mRealmHelper;
    private RealmList<BookManagerItemBean> mList;//list->android isSelect

    @Inject
    public BookMainPresenter(RealmHelper mRealHelper) {
        this.mRealmHelper = mRealHelper;
        registerEvent();
    }

    private void registerEvent() {
        addRxBusSubscribe(BookManagerBean.class, new Action1<BookManagerBean>() {
            @Override
            public void call(BookManagerBean goldManagerBean) {
                mRealmHelper.updateBookManagerList(goldManagerBean);
                mView.updateTab(goldManagerBean.getManagerList());
            }
        });
    }

    @Override
    public void initManagerList() {
        if (SharedPreferenceUtil.getManagerPoint()) {
            //第一次使用，生成默认ManagerList
            initList();
            mRealmHelper.updateBookManagerList(new BookManagerBean(mList));
            mView.updateTab(mList);
        } else {
            if (mRealmHelper.getGoldManagerList() == null) {
                initList();
                mRealmHelper.updateBookManagerList(new BookManagerBean(mList));
            } else {
                mList = mRealmHelper.getBookManagerList().getManagerList();
            }
            mView.updateTab(mList);
        }
    }

    @Override
    public void setManagerList() {
        mView.jumpToManager(mRealmHelper.getBookManagerList());
    }

    private void initList() {
        mList = new RealmList<>();
        mList.add(new BookManagerItemBean(0, true));
        mList.add(new BookManagerItemBean(1, true));
        mList.add(new BookManagerItemBean(2, true));
        mList.add(new BookManagerItemBean(3, true));
        mList.add(new BookManagerItemBean(4, false));
        mList.add(new BookManagerItemBean(5, false));
        mList.add(new BookManagerItemBean(6, false));
        mList.add(new BookManagerItemBean(7, false));
    }
}

