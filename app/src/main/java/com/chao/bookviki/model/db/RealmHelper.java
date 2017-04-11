package com.chao.bookviki.model.db;

import android.content.Context;

import com.chao.bookviki.model.bean.BookManagerBean;
import com.chao.bookviki.model.bean.LoginBean;
import com.chao.bookviki.model.bean.ReadStateBean;
import com.chao.bookviki.model.bean.RealmLikeBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by codeest on 16/8/16.
 */

public class RealmHelper {

    private static final String DB_NAME = "myRealm.realm";

    private Realm mRealm;

    public RealmHelper(Context mContext) {
        mRealm = Realm.getInstance(new RealmConfiguration.Builder(mContext)
                .deleteRealmIfMigrationNeeded()
                .name(DB_NAME)
                .build());
    }

    /**
     * 增加 阅读记录
     * @param id
     * 使用@PrimaryKey注解后copyToRealm需要替换为copyToRealmOrUpdate
     */
    public void insertNewsId(int id) {
        ReadStateBean bean = new ReadStateBean();
        bean.setId(id);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }

    /**
     * 查询 阅读记录
     * @param id
     * @return
     */
    public boolean queryNewsId(int id) {
        RealmResults<ReadStateBean> results = mRealm.where(ReadStateBean.class).findAll();
        for(ReadStateBean item : results) {
            if(item.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * 增加 收藏记录
     * @param bean
     */
    public void insertLikeBean(RealmLikeBean bean) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }

    /**
     * 增加登录信息
     * @param bean
     */
    public void insertLoginBean(LoginBean bean){
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }

    /**
     * 删除登录信息
     * @param name
     */
    public void deleteLoginBean(String name){
        LoginBean data = mRealm.where(LoginBean.class).equalTo("name",name).findFirst();
        mRealm.beginTransaction();
        if (data != null){
            data.deleteFromRealm();
        }
        mRealm.commitTransaction();
    }

    /**
     * 查询登录信息
     */
    public boolean queryIfLogin(){
        RealmResults<LoginBean> results = mRealm.where(LoginBean.class).findAll();
        if(results.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * 返回登录的用户信息
     * @return
     */
    public LoginBean returnLoginBean(){
        RealmResults<LoginBean> results = mRealm.where(LoginBean.class).findAll();
        if(results.isEmpty()){
            return null;
        }
        return results.get(0);
    }


    /**
     * 删除 收藏记录
     * @param id
     */
    public void deleteLikeBean(String id) {
        RealmLikeBean data = mRealm.where(RealmLikeBean.class).equalTo("id",id).findFirst();
        mRealm.beginTransaction();
        if (data != null) {
            data.deleteFromRealm();
        }
        mRealm.commitTransaction();
    }

    /**
     * 查询 收藏记录
     * @param id
     * @return
     */
    public boolean queryLikeId(String id) {
        RealmResults<RealmLikeBean> results = mRealm.where(RealmLikeBean.class).findAll();
        for(RealmLikeBean item : results) {
            if(item.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public List<RealmLikeBean> getLikeList() {
        //使用findAllSort ,先findAll再result.sort无效
        RealmResults<RealmLikeBean> results = mRealm.where(RealmLikeBean.class).findAllSorted("time");
        return mRealm.copyFromRealm(results);
    }

    /**
     * 修改 收藏记录 时间戳以重新排序
     * @param id
     * @param time
     * @param isPlus
     */
    public void changeLikeTime(String id ,long time, boolean isPlus) {
        RealmLikeBean bean = mRealm.where(RealmLikeBean.class).equalTo("id", id).findFirst();
        mRealm.beginTransaction();
        if (isPlus) {
            bean.setTime(++time);
        } else {
            bean.setTime(--time);
        }
        mRealm.commitTransaction();
    }

    /**
     * 更新 掘金首页管理列表
     * 先删除后添加
     * @param bean
     */
   /* public void updateGoldManagerList(GoldManagerBean bean) {
        GoldManagerBean data = mRealm.where(GoldManagerBean.class).findFirst();
        mRealm.beginTransaction();
        if (data != null) {
            data.deleteFromRealm();
        }
        mRealm.copyToRealm(bean);
        mRealm.commitTransaction();
    }*/
    /**
     * 更新 掘金首页管理列表
     * 先删除后添加
     * @param bean
     */
    public void updateBookManagerList(BookManagerBean bean) {
        BookManagerBean data = mRealm.where(BookManagerBean.class).findFirst();
        mRealm.beginTransaction();
        if (data != null) {
            data.deleteFromRealm();
        }
        mRealm.copyToRealm(bean);
        mRealm.commitTransaction();
    }

    /**
     * 获取 掘金首页管理列表
     * @return
     */
   /* public GoldManagerBean getGoldManagerList() {
        GoldManagerBean bean = mRealm.where(GoldManagerBean.class).findFirst();
        if (bean == null)
            return null;
        return mRealm.copyFromRealm(bean);
    }*/
    /**
     * 获取 掘金首页管理列表
     * @return
     */
    public BookManagerBean getBookManagerList() {
        BookManagerBean bean = mRealm.where(BookManagerBean.class).findFirst();
        if (bean == null)
            return null;
        return mRealm.copyFromRealm(bean);
    }
}
