package com.chao.bookviki.util;

import com.chao.bookviki.model.http.exception.ApiException;
import com.chao.bookviki.model.http.response.BookHttpResponse;
import com.chao.bookviki.model.http.response.GankHttpResponse;
import com.chao.bookviki.model.http.response.GoldHttpResponse;
import com.chao.bookviki.model.http.response.JingXuanNewsResponse;
import com.chao.bookviki.model.http.response.MyHttpResponse;
import com.chao.bookviki.model.http.response.YingWenYuLuResponse;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by codeest on 2016/8/3.
 */
public class RxUtil {

    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())//io
                        .observeOn(AndroidSchedulers.mainThread());//changeTo mainThread
            }
        };
    }

    /**
     * 统一返回结果处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<GankHttpResponse<T>, T> handleResult() {   //compose判断结果
        return new Observable.Transformer<GankHttpResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<GankHttpResponse<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<GankHttpResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(GankHttpResponse<T> tGankHttpResponse) {
                        if(!tGankHttpResponse.getError()) {
                            return createData(tGankHttpResponse.getResults());
                        } else {
                            return Observable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }


    /**
     * 统一返回结果处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<MyHttpResponse<T>, T> handleMyResult() {   //compose判断结果
        return new Observable.Transformer<MyHttpResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<MyHttpResponse<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<MyHttpResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(MyHttpResponse<T> tMyHttpResponse) {
                        if(tMyHttpResponse.getCode() == 200) {
                            return createData(tMyHttpResponse.getData());
                        } else {
                            return Observable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 统一返回结果处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<GoldHttpResponse<T>, T> handleGoldResult() {   //compose判断结果
        return new Observable.Transformer<GoldHttpResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<GoldHttpResponse<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<GoldHttpResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(GoldHttpResponse<T> tGoldHttpResponse) {
                        if(tGoldHttpResponse.getResults() != null) {
                            return createData(tGoldHttpResponse.getResults());
                        } else {
                            return Observable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    public static <T> Observable.Transformer<BookHttpResponse<T>, T> handleBookResult() {   //compose判断结果
        return new Observable.Transformer<BookHttpResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<BookHttpResponse<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<BookHttpResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BookHttpResponse<T> tBookHttpResponse) {
                        if(tBookHttpResponse.getResults() != null && tBookHttpResponse.getStatus() == 200) {
                            return createData(tBookHttpResponse.getResults());
                        } else {
                            return Observable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    //处理精选阅读新闻
    public static <T> Observable.Transformer<JingXuanNewsResponse<T>, T> handleJingXuanNewsResult() {   //compose判断结果
        return new Observable.Transformer<JingXuanNewsResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<JingXuanNewsResponse<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<JingXuanNewsResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(JingXuanNewsResponse<T> tBookHttpResponse) {
                        if(tBookHttpResponse.getList() != null) {
                            return createData(tBookHttpResponse.getList());
                        } else {
                            return Observable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    //处理精选阅读-英文语录body
    public static <T> Observable.Transformer<YingWenYuLuResponse<T>, T> handleYingWenYuLuResult() {   //compose判断结果
        return new Observable.Transformer<YingWenYuLuResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<YingWenYuLuResponse<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<YingWenYuLuResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(YingWenYuLuResponse<T> tBookHttpResponse) {
                        YingWenYuLuResponse.BodyResponse<T> body = tBookHttpResponse.getShowapi_res_body();
                        if(body != null) {
                            if (body.getData() != null){
                                return createData(body.getData());
                            }else {
                                return Observable.error(new ApiException("服务器返回error"));
                            }

                        } else {
                            return Observable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 生成Observable
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
