package com.chao.bookviki.model.http.api;

import com.chao.bookviki.model.bean.NodeBean;
import com.chao.bookviki.model.bean.RepliesListBean;
import com.chao.bookviki.model.bean.NodeListBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


public interface VtexApis {

    String HOST = "https://www.v2ex.com/";

    String TAB_HOST = "https://www.v2ex.com/?tab=";

    String REPLIES_URL = "https://www.v2ex.com/t/";

    /**
     * 获取节点信息
     * @return
     */
    @GET("/api/nodes/show.json")
    Observable<NodeBean> getNodeInfo(@Query("name") String name);

    /**
     * 获取主题列表
     * @return
     */
    @GET("/api/topics/show.json")
    Observable<List<NodeListBean>> getTopicList(@Query("node_name") String name);

    /**
     * 获取主题信息
     * @return
     */
    @GET("/api/topics/show.json")
    Observable<List<NodeListBean>> getTopicInfo(@Query("id") String id);

    /**
     * 获取主题回复
     * @return
     */
    @GET("/api/replies/show.json")
    Observable<List<RepliesListBean>> getRepliesList(@Query("topic_id") String id);
}
