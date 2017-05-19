package com.chao.bookviki.di.module;

import com.chao.bookviki.BuildConfig;
import com.chao.bookviki.app.App;
import com.chao.bookviki.app.Constants;
import com.chao.bookviki.di.qualifier.BookUrl;
import com.chao.bookviki.di.qualifier.JingXuanNewsUrl;
import com.chao.bookviki.di.qualifier.YingWenYuLuUrl;
import com.chao.bookviki.model.http.api.BookApis;
import com.chao.bookviki.model.http.api.JingXuanNewsApis;
import com.chao.bookviki.model.http.api.YingWenYuLuApis;
import com.chao.bookviki.util.SystemUtil;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class HttpModule {

   // public static ClearableCookieJar mCookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getInstance()));

    @Singleton
    @Provides
    ClearableCookieJar provideClearableCookieJar() {
        return new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getInstance()));
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }


    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }



    @Singleton
    @Provides
    @BookUrl
    Retrofit provideBookRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, BookApis.HOST);
    }

    @Singleton
    @Provides
    @JingXuanNewsUrl
    Retrofit provideJingXuanNewsRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, JingXuanNewsApis.HOST);
    }

    @Singleton
    @Provides
    @YingWenYuLuUrl
    Retrofit provideYingWenYuLuRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, YingWenYuLuApis.HOST);
    }


    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder,ClearableCookieJar mcookieJar) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!SystemUtil.isNetworkConnected()) {//没有网络
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (SystemUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
//        Interceptor apikey = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                request = request.newBuilder()
//                        .addHeader("apikey",Constants.KEY_API)
//                        .build();
//                return chain.proceed(request);
//            }
//        }
//        设置统一的请求头部参数
//        builder.addInterceptor(apikey);
        /*CookieHandler cookieHandler = new CookieManager(new PersistentCookieStore(context),
                CookiePolicy.ACCEPT_ALL);*/

        //ClearableCookieJar cookieJar = mcookieJar;
                //new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getInstance()));
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        builder.cookieJar(mcookieJar);
        return builder.build();
    }


    @Singleton
    @Provides
    BookApis provideBookService(@BookUrl Retrofit retrofit) {
        return retrofit.create(BookApis.class);
    }

    @Singleton
    @Provides
    JingXuanNewsApis provideJingXuanNewsService(@JingXuanNewsUrl Retrofit retrofit) {
        return retrofit.create(JingXuanNewsApis.class);
    }

    @Singleton
    @Provides
    YingWenYuLuApis provideYingWenYuLuService(@YingWenYuLuUrl Retrofit retrofit) {
        return retrofit.create(YingWenYuLuApis.class);
    }


    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        //if (client.cookieJar() == CookieJar.NO_COOKIES) return;
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
