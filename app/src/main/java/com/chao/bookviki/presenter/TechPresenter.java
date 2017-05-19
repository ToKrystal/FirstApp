package com.chao.bookviki.presenter;

import com.chao.bookviki.app.Constants;
import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.component.RxBus;
import com.chao.bookviki.model.bean.ImageBean;
import com.chao.bookviki.model.bean.JingXuanNewsBean;
import com.chao.bookviki.model.event.SearchEvent;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.response.BookHttpResponse;
import com.chao.bookviki.model.http.response.JingXuanNewsResponse;
import com.chao.bookviki.presenter.contract.TechContract;
import com.chao.bookviki.ui.gank.fragment.GankMainFragment;
import com.chao.bookviki.util.JingXuanDiverdedUtil;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Func1;

/**
 *
 */

public class TechPresenter extends RxPresenter<TechContract.View> implements TechContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
    private static final int NUM_OF_PAGE = 9;
    private Map<String,TypeObj> type2ObjMap;
    private Map<String,List<JingXuanNewsBean>> type2NewsBeanMap;
    public   Map<Integer,JingXuanDiverdedUtil.TypeObj> type2PageMap;
    private Random random ;
    private List<JingXuanNewsBean> mList;
    public static class TypeObj{
        public Integer currentPage;

        public TypeObj(Integer currentPage) {
            this.currentPage = currentPage;
        }
    }

    private int currentPage = 1;
    private String queryStr = null;
    private String currentTech = GankMainFragment.tabTitle[0];
    private int currentType = Constants.JING_XUAN_CONSTANT;



    /**
     * war 	军事
     2 	sport 	体育
     3 	tech 	科技
     4 	edu 	教育
     5 	ent 	娱乐
     6 	money 	财经
     7 	gupiao 	股票
     8 	travel 	旅游
     9 	lady 	女人
     * @param mRetrofitHelper
     */

    @Inject
    public TechPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
        mList = new ArrayList<>();
        type2ObjMap = new HashMap<>(JingXuanDiverdedUtil.map.size());
        type2NewsBeanMap = new HashMap<>(JingXuanDiverdedUtil.map.size());
        //查询事件
        registerEvent();
        random = new Random();
        type2PageMap = new HashMap<>(JingXuanDiverdedUtil.map.size());
        int i = 0;
        for (Map.Entry<String,String> entry : JingXuanDiverdedUtil.map.entrySet()){
            String type = entry.getKey();
            JingXuanDiverdedUtil.TypeObj obj = new JingXuanDiverdedUtil.TypeObj(type,1);
            type2PageMap.put(i,obj);
            i++;
        }
    }

    private  JingXuanDiverdedUtil.TypeObj getRanDomType(){
        int index = random.nextInt(type2PageMap.size());
        return type2PageMap.get(index);
    }

    private void registerEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(SearchEvent.class)
                .compose(RxUtil.<SearchEvent>rxSchedulerHelper())
                .filter(new Func1<SearchEvent, Boolean>() {
                    @Override
                    public Boolean call(SearchEvent searchEvent) {
                        return searchEvent.getType() == currentType;
                    }
                })
                .map(new Func1<SearchEvent, String>() {
                    @Override
                    public String call(SearchEvent searchEvent) {
                        return searchEvent.getQuery();
                    }
                })
                .subscribe(new CommonSubscriber<String>(mView, "搜索失败") {
                    @Override
                    public void onNext(String s) {
                        queryStr = s;
                     //   getSearchTechData();
                       // getSearchNewsDate();
                        //本地搜索
                        getSearch(s);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    private void getSearch(String s) {
        List<JingXuanNewsBean> list = new ArrayList<>(mList.size());
        for (JingXuanNewsBean bean : mList){
            if (bean.getTitle().contains(s)) list.add(bean);
        }
        mView.showJingXuanItem(list);
    }

    private void getSearchNewsDate() {
        currentPage = 1;
        Subscription rxSubscription = mRetrofitHelper.getJingXuanNewsList("war",currentPage,NUM_OF_PAGE)
                .compose(RxUtil.<JingXuanNewsResponse<List<JingXuanNewsBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<JingXuanNewsBean>>handleJingXuanNewsResult())
                .map(new Func1<List<JingXuanNewsBean>, List<JingXuanNewsBean>>() {
                    @Override
                    public List<JingXuanNewsBean> call(List<JingXuanNewsBean> beans) {
                        List<JingXuanNewsBean> newList = new ArrayList<>();
                        for (JingXuanNewsBean item : beans) {
                            if (item.getTitle().contains(queryStr)){
                                newList.add(item);
                            }
                        }
                        return newList;
                    }
                })
                .subscribe(new CommonSubscriber<List<JingXuanNewsBean>>(mView) {
                    @Override
                    public void onNext(List<JingXuanNewsBean> beans) {
                        mView.showJingXuanItem(beans);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    /*private void getSearchTechData() {
        currentPage = 1;
        Subscription rxSubscription = mRetrofitHelper.fetchGankSearchList(queryStr, currentTech, NUM_OF_PAGE, currentPage)
                .compose(RxUtil.<GankHttpResponse<List<GankSearchItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<GankSearchItemBean>>handleResult())
                .map(new Func1<List<GankSearchItemBean>, List<GankItemBean>>() {
                    @Override
                    public List<GankItemBean> call(List<GankSearchItemBean> gankSearchItemBeen) {
                        List<GankItemBean> newList = new ArrayList<>();
                        for (GankSearchItemBean item : gankSearchItemBeen) {
                            GankItemBean bean = new GankItemBean();
                            bean.set_id(item.getGanhuo_id());
                            bean.setDesc(item.getDesc());
                            bean.setPublishedAt(item.getPublishedAt());
                            bean.setWho(item.getWho());
                            bean.setUrl(item.getUrl());
                            newList.add(bean);
                        }
                        return newList;
                    }
                })
                .subscribe(new CommonSubscriber<List<GankItemBean>>(mView) {
                    @Override
                    public void onNext(List<GankItemBean> gankItemBeen) {
                        mView.showContent(gankItemBeen);
                    }
                });
        addSubscrebe(rxSubscription);
    }*/

   /* @Override
    public void getGankData(String tech, int type) {
        queryStr = null;
        currentPage = 1;
        currentTech = tech;
        currentType = type;
        Subscription rxSubscription = mRetrofitHelper.fetchTechList(tech,NUM_OF_PAGE,currentPage)
                .compose(RxUtil.<GankHttpResponse<List<GankItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<GankItemBean>>handleResult())
                .subscribe(new CommonSubscriber<List<GankItemBean>>(mView) {
                    @Override
                    public void onNext(List<GankItemBean> gankItemBeen) {
                        mView.showContent(gankItemBeen);
                    }
                });
        addSubscrebe(rxSubscription);
    }*/

   /* @Override
    public void getJingXuanNews(String type) {
        //随机生成所有type,大小为limit
        Map<String,Integer> type2NumMap = JingXuanDiverdedUtil.diverided(NUM_OF_PAGE);
        //请求
        final List<JingXuanNewsBean> toShowList = new ArrayList<>(NUM_OF_PAGE);
        for (Map.Entry<String,Integer> entry : type2NumMap.entrySet()){
            String newsType = entry.getKey();
            final int needNum = entry.getValue();
            List<JingXuanNewsBean> list = type2NewsBeanMap.get(newsType);
            if (list == null){
                list = new ArrayList<>(NUM_OF_PAGE*2);
                type2NewsBeanMap.put(newsType,list);
            }
            //填充list
            final List<JingXuanNewsBean> finalList = list;
            if (list.size() < needNum){//不够了
                TypeObj obj = type2ObjMap.get(newsType);
                int currentPage;
                if (obj == null){
                    obj = new TypeObj(1);
                    type2ObjMap.put(newsType,obj);
                }else {
                    obj.currentPage++;
                }
                currentPage = obj.currentPage;
                Subscription rxSubscription = mRetrofitHelper.getJingXuanNewsList(newsType,currentPage,NUM_OF_PAGE)
                        .compose(RxUtil.<JingXuanNewsResponse<List<JingXuanNewsBean>>>rxSchedulerHelper())
                        .compose(RxUtil.<List<JingXuanNewsBean>>handleJingXuanNewsResult())
                        .subscribe(new CommonSubscriber<List<JingXuanNewsBean>>(mView) {
                            @Override
                            public void onNext(List<JingXuanNewsBean> beans) {
                                //mView.showJingXuanItem(jingXuanNewsBean);
                                //完成了
                                finalList.addAll(beans);
                                //发事件解决
                            }
                        });
                addSubscrebe(rxSubscription);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //保证完成
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String,Integer> entry : type2NumMap.entrySet()){
            String tt = entry.getKey();
            int needNum = entry.getValue();
            LogUtil.i(type2NewsBeanMap.get(tt).size()+"");
            List<JingXuanNewsBean> list = type2NewsBeanMap.get(tt);
            toShowList.addAll(list.subList(0,needNum));
            list.subList(0,needNum).clear();
        }
        mView.showJingXuanItem(toShowList);
    }*/

    @Override
    public void getJingXuanNews(String type) {
        JingXuanDiverdedUtil.TypeObj obj = getRanDomType();
        Subscription rxSubscription = mRetrofitHelper.getJingXuanNewsList(obj.type,obj.currentPage,NUM_OF_PAGE)
                .compose(RxUtil.<JingXuanNewsResponse<List<JingXuanNewsBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<JingXuanNewsBean>>handleJingXuanNewsResult())
                .subscribe(new CommonSubscriber<List<JingXuanNewsBean>>(mView, "加载更多数据失败ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(List<JingXuanNewsBean> beans) {
                        mView.showJingXuanItem(beans);
                        mList.clear();
                        mList.addAll(beans);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getMoreJingXuanNews(String type) {
        if(queryStr != null) {
            //TODO 搜索的
          //  getMoreSearchGankData();
            //getMoreSearchNewsData();
            return;
        }
        JingXuanDiverdedUtil.TypeObj obj = getRanDomType();
        Subscription rxSubscription = mRetrofitHelper.getJingXuanNewsList(obj.type,++obj.currentPage,NUM_OF_PAGE)
                .compose(RxUtil.<JingXuanNewsResponse<List<JingXuanNewsBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<JingXuanNewsBean>>handleJingXuanNewsResult())
                .subscribe(new CommonSubscriber<List<JingXuanNewsBean>>(mView, "加载更多数据失败ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(List<JingXuanNewsBean> beans) {
                        mView.showMoreJingXuanItem(beans);
                        mList.addAll(beans);
                    }
                });
        addSubscrebe(rxSubscription);
    }




    /*@Override
    public void getMoreGankData(String tech) {
        if(queryStr != null) {
            getMoreSearchGankData();
            return;
        }
        Subscription rxSubscription = mRetrofitHelper.fetchTechList(tech,NUM_OF_PAGE,++currentPage)
                .compose(RxUtil.<GankHttpResponse<List<GankItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<GankItemBean>>handleResult())
                .subscribe(new CommonSubscriber<List<GankItemBean>>(mView, "加载更多数据失败ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(List<GankItemBean> gankItemBeen) {
                        mView.showMoreContent(gankItemBeen);
                    }
                });
        addSubscrebe(rxSubscription);
    }*/

    /*private void getMoreSearchGankData() {
        Subscription rxSubscription = mRetrofitHelper.fetchGankSearchList(queryStr, currentTech, NUM_OF_PAGE, ++currentPage)
                .compose(RxUtil.<GankHttpResponse<List<GankSearchItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<GankSearchItemBean>>handleResult())
                .map(new Func1<List<GankSearchItemBean>, List<GankItemBean>>() {
                    @Override
                    public List<GankItemBean> call(List<GankSearchItemBean> gankSearchItemBeen) {
                        List<GankItemBean> newList = new ArrayList<>();
                        for (GankSearchItemBean item : gankSearchItemBeen) {
                            GankItemBean bean = new GankItemBean();
                            bean.set_id(item.getGanhuo_id());
                            bean.setDesc(item.getDesc());
                            bean.setPublishedAt(item.getPublishedAt());
                            bean.setWho(item.getWho());
                            bean.setUrl(item.getUrl());
                            newList.add(bean);
                        }
                        return newList;
                    }
                })
                .subscribe(new CommonSubscriber<List<GankItemBean>>(mView) {
                    @Override
                    public void onNext(List<GankItemBean> gankItemBeen) {
                        mView.showMoreContent(gankItemBeen);
                    }
                });
        addSubscrebe(rxSubscription);
    }*/

    private void getMoreSearchNewsData() {
        Subscription rxSubscription = mRetrofitHelper.getJingXuanNewsList("war",currentPage,NUM_OF_PAGE)
                .compose(RxUtil.<JingXuanNewsResponse<List<JingXuanNewsBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<JingXuanNewsBean>>handleJingXuanNewsResult())
                .map(new Func1<List<JingXuanNewsBean>, List<JingXuanNewsBean>>() {
                    @Override
                    public List<JingXuanNewsBean> call(List<JingXuanNewsBean> beans) {
                        List<JingXuanNewsBean> newList = new ArrayList<>();
                        for (JingXuanNewsBean item : beans) {
                            if (item.getTitle().contains(queryStr)){
                                newList.add(item);
                            }
                        }
                        return newList;
                    }
                })
                .subscribe(new CommonSubscriber<List<JingXuanNewsBean>>(mView) {
                    @Override
                    public void onNext(List<JingXuanNewsBean> beans) {
                        mView.showJingXuanItem(beans);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getGirlImage() {
       /* Subscription rxSubscription = mRetrofitHelper.fetchRandomGirl(1)
                .compose(RxUtil.<GankHttpResponse<List<GankItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<GankItemBean>>handleResult())
                .subscribe(new CommonSubscriber<List<GankItemBean>>(mView, "加载封面失败") {
                    @Override
                    public void onNext(List<GankItemBean> gankItemBean) {
                      //  mView.showGirlImage(gankItemBean.get(0).getUrl(), gankItemBean.get(0).getWho());
                        mView.showGirlImage("http://chenyuchao.com.cn/1.jpg","chaoge");
                    }
                });
        addSubscrebe(rxSubscription);*/

        Subscription rxSubscription = mRetrofitHelper.getRandomImages(1)
                .compose(RxUtil.<BookHttpResponse<ImageBean>>rxSchedulerHelper())
                .compose(RxUtil.<ImageBean>handleBookResult())
                .subscribe(new CommonSubscriber<ImageBean>(mView, "加载封面失败") {
                    @Override
                    public void onNext(ImageBean gankItemBean) {
                        //  mView.showGirlImage(gankItemBean.get(0).getUrl(), gankItemBean.get(0).getWho());
                     //   mView.showGirlImage("http://chenyuchao.com.cn/1.jpg","chaoge");
                        mView.showGirlImage(gankItemBean.url,gankItemBean.name);
                    }
                });
        addSubscrebe(rxSubscription);
    }


}
