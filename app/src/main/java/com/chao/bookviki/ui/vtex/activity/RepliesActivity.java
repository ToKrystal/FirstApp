package com.chao.bookviki.ui.vtex.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.chao.bookviki.R;
import com.chao.bookviki.app.Constants;
import com.chao.bookviki.base.BaseActivity;
import com.chao.bookviki.model.bean.NodeListBean;
import com.chao.bookviki.model.bean.RealmLikeBean;
import com.chao.bookviki.model.bean.RepliesListBean;
import com.chao.bookviki.model.http.api.VtexApis;
import com.chao.bookviki.presenter.RepliesPresenter;
import com.chao.bookviki.presenter.contract.RepliesContract;
import com.chao.bookviki.ui.vtex.adapter.RepliesAdapter;
import com.chao.bookviki.util.ShareUtil;
import com.chao.bookviki.util.SnackbarUtil;
import com.chao.bookviki.util.SystemUtil;
import com.chao.bookviki.widget.CommonItemDecoration;
import com.chao.bookviki.widget.ProgressImageView;

import java.util.List;

import butterknife.BindView;

/**
 * 帖子详情
 * 主要用该rec
 */

public class RepliesActivity extends BaseActivity<RepliesPresenter> implements RepliesContract.View {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.iv_progress)
    ProgressImageView ivProgress;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private RepliesAdapter mAdapter;
    private NodeListBean mTopBean;
    private MenuItem menuItem;
    private String topicId;
    private boolean isLiked;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_replies;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, "帖子详情");
        topicId = getIntent().getExtras().getString(Constants.IT_VTEX_TOPIC_ID);
        mTopBean = getIntent().getParcelableExtra(Constants.IT_VTEX_REPLIES_TOP);
       // mAdapter = new RepliesAdapter(mContext, new ArrayList<RepliesListBean>(), mTopBean);
        CommonItemDecoration mDecoration = new CommonItemDecoration(2, CommonItemDecoration.UNIT_PX);
        rvContent.addItemDecoration(mDecoration);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getContent(topicId);
            }
        });
        ivProgress.start();
        mPresenter.getContent(topicId);
        if (mTopBean == null) {
            mPresenter.getTopInfo(topicId);
        }
    }

    @Override
    public void showError(String msg) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.stop();
        }
        SnackbarUtil.showShort(toolBar, msg);
    }

    @Override
    public void showContent(List<RepliesListBean> mList) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.stop();
        }
        mAdapter.setContentData(mList);
    }

    @Override
    public void showTopInfo(NodeListBean mTopInfo) {
        mTopBean = mTopInfo;
      //  mAdapter.setTopData(mTopInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tech_meun, menu);
        menuItem = menu.findItem(R.id.action_like);
        setLikeState(mPresenter.query(topicId));
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_like:
                if(isLiked) {
                    item.setIcon(R.mipmap.ic_toolbar_like_n);
                    mPresenter.delete(topicId);
                    isLiked = false;
                } else {
                    item.setIcon(R.mipmap.ic_toolbar_like_p);
                    RealmLikeBean bean = new RealmLikeBean();
                    bean.setId(topicId);
                    bean.setImage(mTopBean.getMember().getavatar_normal());
//                    bean.setUrl(url);
                    bean.setTitle(mTopBean.getTitle());
                    bean.setType(Constants.TYPE_VTEX);
                    bean.setTime(System.currentTimeMillis());
                    mPresenter.insert(bean);
                    isLiked = true;
                }
                break;
            case R.id.action_copy:
                SystemUtil.copyToClipBoard(mContext, VtexApis.REPLIES_URL + id);
                return true;
            case R.id.action_share:
                ShareUtil.shareText(mContext, VtexApis.REPLIES_URL + id, "分享一篇文章");
        }
        return super.onOptionsItemSelected(item);
    }

    private void setLikeState(boolean state) {
        if(state) {
            menuItem.setIcon(R.mipmap.ic_toolbar_like_p);
            isLiked = true;
        } else {
            menuItem.setIcon(R.mipmap.ic_toolbar_like_n);
            isLiked = false;
        }
    }
}
