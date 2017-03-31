package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.bean.DailyListBean;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.presenter.contract.DailyContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;
import com.chao.bookviki.component.RxBus;
import com.chao.bookviki.model.bean.DailyBeforeListBean;
import com.chao.bookviki.model.db.RealmHelper;
import com.chao.bookviki.util.DateUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by codeest on 16/8/11.
 * 日期为明天时，取latest接口的数据
 * 其他日期，取before接口的数据
 */

public class DailyPresenter extends RxPresenter<DailyContract.View> implements DailyContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;
    private Subscription intervalSubscription;

    private static final int INTERVAL_INSTANCE = 6;

    private int topCount = 0;
    private int currentTopCount = 0;

    @Inject
    public DailyPresenter(RetrofitHelper mRetrofitHelper,RealmHelper mRealHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mRealmHelper = mRealHelper;
        registerEvent();
    }

    private void registerEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(CalendarDay.class)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CalendarDay, String>() {
                    @Override
                    public String call(CalendarDay calendarDay) {
                        StringBuilder date = new StringBuilder();
                        String year = String.valueOf(calendarDay.getYear());
                        String month = String.valueOf(calendarDay.getMonth() + 1);
                        String day = String.valueOf(calendarDay.getDay() + 1);
                        if(month.length() < 2) {
                            month = "0" + month;
                        }
                        if(day.length() < 2) {
                            day = "0" + day;
                        }
                        return date.append(year).append(month).append(day).toString();
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        if(s.equals(DateUtil.getTomorrowDate())) {
                            getDailyData();
                            return false;
                        }
                        return true;
                    }
                })
                .observeOn(Schedulers.io())   //为了网络请求切到io线程
                .flatMap(new Func1<String, Observable<DailyBeforeListBean>>() {
                    @Override
                    public Observable<DailyBeforeListBean> call(String date) {
                        return mRetrofitHelper.fetchDailyBeforeListInfo(date);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())    //为了使用Realm和显示结果切到主线程
                .map(new Func1<DailyBeforeListBean, DailyBeforeListBean>() {
                    @Override
                    public DailyBeforeListBean call(DailyBeforeListBean dailyBeforeListBean) {
                        List<DailyListBean.StoriesBean> list = dailyBeforeListBean.getStories();
                        for(DailyListBean.StoriesBean item : list) {
                            item.setReadState(mRealmHelper.queryNewsId(item.getId()));
                        }
                        return dailyBeforeListBean;
                    }
                })
                .subscribe(new CommonSubscriber<DailyBeforeListBean>(mView) {
                    @Override
                    public void onNext(DailyBeforeListBean dailyBeforeListBean) {
                        int year = Integer.valueOf(dailyBeforeListBean.getDate().substring(0,4));
                        int month = Integer.valueOf(dailyBeforeListBean.getDate().substring(4,6));
                        int day = Integer.valueOf(dailyBeforeListBean.getDate().substring(6,8));
                        mView.showMoreContent(String.format("%d年%d月%d日",year,month,day),dailyBeforeListBean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getDailyData() {
        Subscription rxSubscription = mRetrofitHelper.fetchDailyListInfo()
                .compose(RxUtil.<DailyListBean>rxSchedulerHelper())
                .map(new Func1<DailyListBean, DailyListBean>() {
                    @Override
                    public DailyListBean call(DailyListBean dailyListBean) {
                        List<DailyListBean.StoriesBean> list = dailyListBean.getStories();
                        for(DailyListBean.StoriesBean item : list) {
                            item.setReadState(mRealmHelper.queryNewsId(item.getId()));
                        }
                        return dailyListBean;
                    }
                })
                .subscribe(new CommonSubscriber<DailyListBean>(mView) {
                    @Override
                    public void onNext(DailyListBean dailyListBean) {
                        topCount = dailyListBean.getTop_stories().size();
                        mView.showContent(dailyListBean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getBeforeData(String date) {
        Subscription rxSubscription = mRetrofitHelper.fetchDailyBeforeListInfo(date)
                .compose(RxUtil.<DailyBeforeListBean>rxSchedulerHelper())
                .map(new Func1<DailyBeforeListBean, DailyBeforeListBean>() {
                    @Override
                    public DailyBeforeListBean call(DailyBeforeListBean dailyBeforeListBean) {
                        List<DailyListBean.StoriesBean> list = dailyBeforeListBean.getStories();
                        for(DailyListBean.StoriesBean item : list) {
                            item.setReadState(mRealmHelper.queryNewsId(item.getId()));
                        }
                        return dailyBeforeListBean;
                    }
                })
                .subscribe(new CommonSubscriber<DailyBeforeListBean>(mView) {
                    @Override
                    public void onNext(DailyBeforeListBean dailyBeforeListBean) {
                        int year = Integer.valueOf(dailyBeforeListBean.getDate().substring(0,4));
                        int month = Integer.valueOf(dailyBeforeListBean.getDate().substring(4,6));
                        int day = Integer.valueOf(dailyBeforeListBean.getDate().substring(6,8));
                        mView.showMoreContent(String.format("%d年%d月%d日",year,month,day),dailyBeforeListBean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void startInterval() {
        intervalSubscription = Observable.interval(INTERVAL_INSTANCE, TimeUnit.SECONDS)
                .compose(RxUtil.<Long>rxSchedulerHelper())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if(currentTopCount == topCount)
                            currentTopCount = 0;
                        mView.doInterval(currentTopCount++);
                    }
                });
        addSubscrebe(intervalSubscription);
    }

    @Override
    public void stopInterval() {
        if (intervalSubscription != null) {
            intervalSubscription.unsubscribe();
        }
    }

    @Override
    public void insertReadToDB(int id) {
        mRealmHelper.insertNewsId(id);
    }
}