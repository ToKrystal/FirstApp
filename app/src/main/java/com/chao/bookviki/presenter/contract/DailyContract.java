package com.chao.bookviki.presenter.contract;

import com.chao.bookviki.base.BaseView;
import com.chao.bookviki.model.bean.DailyListBean;
import com.chao.bookviki.base.BasePresenter;
import com.chao.bookviki.model.bean.DailyBeforeListBean;

/**
 * Created by codeest on 16/8/11.
 */

public interface DailyContract {

    interface View extends BaseView {

        void showContent(DailyListBean info);

        void showMoreContent(String date,DailyBeforeListBean info);

        void doInterval(int currentCount);
    }

    interface Presenter extends BasePresenter<View> {

        void getDailyData();

        void getBeforeData(String date);

        void startInterval();

        void stopInterval();

        void insertReadToDB(int id);
    }
}
