package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.bean.BookListBean;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.response.BookHttpResponse;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;
import com.chao.bookviki.presenter.contract.BookContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

/**
 * Created by Administrator on 2017/3/28.
 */

public class BookPresenter extends RxPresenter<BookContract.View> implements BookContract.Presenter {

    private static final int NUM_EACH_PAGE = 20;
    private static final int NUM_HOT_LIMIT = 3;
    private  int START = 0;

    private RetrofitHelper mRetrofitHelper;
    private List<BookListBean> totalList = new ArrayList<>();

    private boolean isHotList = true;
    private int currentPage = 0;
    private String mType;

    @Inject
    public BookPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void getBookData(String type) {
        mType = type;
        currentPage = 0;
        totalList.clear();
        Observable<List<BookListBean>> list = mRetrofitHelper.fetchBookList(type, START+(NUM_EACH_PAGE*(currentPage++)), NUM_EACH_PAGE)//获取数据
                .compose(RxUtil.<BookHttpResponse<List<BookListBean>>>rxSchedulerHelper())//io -> main
                .compose(RxUtil.<List<BookListBean>>handleBookResult());//response-> bean
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -3);

        Observable<List<BookListBean>> hotList = mRetrofitHelper.fetchBookHotList(type,
                new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()), NUM_HOT_LIMIT)
                .compose(RxUtil.<BookHttpResponse<List<BookListBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<BookListBean>>handleBookResult());

        Subscription rxSubscription = Observable.concat(hotList, list)
                .subscribe(new CommonSubscriber<List<BookListBean>>(mView) {
                    @Override
                    public void onNext(List<BookListBean> goldListBean) {
                        if (isHotList) {
                            isHotList = false;
                            totalList.addAll(goldListBean);
                        } else {
                            isHotList = true;
                            totalList.addAll(goldListBean);

                            mView.showContent(totalList);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getMoreBookData() {
        Subscription rxSubscription = mRetrofitHelper.fetchBookList(mType, START+(NUM_EACH_PAGE*(currentPage++)), NUM_EACH_PAGE)
                .compose(RxUtil.<BookHttpResponse<List<BookListBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<BookListBean>>handleBookResult())
                .subscribe(new CommonSubscriber<List<BookListBean>>(mView) {
                    @Override
                    public void onNext(List<BookListBean> goldListBeen) {
                        totalList.addAll(goldListBeen);
                        mView.showMoreContent(totalList, totalList.size(), totalList.size() + NUM_EACH_PAGE);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
