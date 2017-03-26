package com.codeest.geeknews.ui.gold.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.codeest.geeknews.R;
import com.codeest.geeknews.app.Constants;
import com.codeest.geeknews.base.BaseFragment;
import com.codeest.geeknews.model.bean.GoldListBean;
import com.codeest.geeknews.presenter.GoldPresenter;
import com.codeest.geeknews.presenter.contract.GoldContract;
import com.codeest.geeknews.ui.gold.activity.BookDetailActivity;
import com.codeest.geeknews.ui.gold.adapter.GoldListAdapter;
import com.codeest.geeknews.util.LogUtil;
import com.codeest.geeknews.util.SnackbarUtil;
import com.codeest.geeknews.widget.GoldItemDecoration;
import com.codeest.geeknews.widget.ProgressImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by codeest on 16/11/27.
 */

public class GoldPagerFragment extends BaseFragment<GoldPresenter> implements GoldContract.View {

    @BindView(R.id.rv_content)
    RecyclerView rvGoldListRecycleView;
    @BindView(R.id.iv_progress)
    ProgressImageView ivProgress;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private GoldListAdapter mAdapter;
    private GoldItemDecoration mDecoration;

    private boolean isLoadingMore = false;
    private String mType;
    private static int bookId = 0;


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_common_list;
    }

    @Override
    protected void initEventAndData() {
        mType = getArguments().getString(Constants.IT_GOLD_TYPE);
        mDecoration = new GoldItemDecoration();
        mAdapter = new GoldListAdapter(mContext, new ArrayList<GoldListBean>(), getArguments().getString(Constants.IT_GOLD_TYPE_STR));


        mAdapter.setOnItemClickListener(new GoldListAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position,View shareView) {
                Intent intent = new Intent();
                intent.setClass(mContext, BookDetailActivity.class);
                intent.putExtra("id",9305940);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, shareView, "shareView");
                mContext.startActivity(intent,options.toBundle());
            }
        });



        rvGoldListRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        rvGoldListRecycleView.setAdapter(mAdapter);
        rvGoldListRecycleView.addItemDecoration(mDecoration);
        rvGoldListRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) rvGoldListRecycleView.getLayoutManager()).findLastVisibleItemPosition();
                int totalItemCount = rvGoldListRecycleView.getLayoutManager().getItemCount();
                if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {
                    if(!isLoadingMore){
                        isLoadingMore = true;
                        mPresenter.getMoreGoldData();
                    }
                }
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!mAdapter.getHotFlag()) {
                    rvGoldListRecycleView.addItemDecoration(mDecoration);
                }
                mAdapter.setHotFlag(true);
                mPresenter.getGoldData(mType);
            }
        });
        mAdapter.setOnHotCloseListener(new GoldListAdapter.OnHotCloseListener() {
            @Override
            public void onClose() {
                rvGoldListRecycleView.removeItemDecoration(mDecoration);
            }
        });
        ivProgress.start();
        mPresenter.getGoldData(mType);
    }

    @Override
    public void showContent(List<GoldListBean> goldListBean) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.stop();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("insert into book_list(objectid,type,createdat,title,collectioncount,commentscount,url,user,screenshot) values");
        for(GoldListBean bean : goldListBean){
            String strId = String.valueOf(bookId);
                stringBuilder.append("('");
                stringBuilder.append(strId+"','");
                stringBuilder.append("type"+"','");
                stringBuilder.append(bean.getCreatedAt()+"','");
                stringBuilder.append(bean.getTitle()+"',");
                stringBuilder.append(bean.getCollectionCount()+",");
                stringBuilder.append(bean.getCommentsCount()+",'");
                stringBuilder.append(bean.getUrl()+"','");
                stringBuilder.append(bean.getUser().getUsername()+"','");
                stringBuilder.append(bean.getScreenshot()== null ? "')":bean.getScreenshot().getUrl()     +"')");
                stringBuilder.append(",");
            bookId++;

            }
        LogUtil.i(stringBuilder.toString());

        mAdapter.updateData(goldListBean);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreContent(List<GoldListBean> goldMoreListBean, int start, int end) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("insert into book_list(objectid,type,createdat,title,collectioncount,commentscount,url,user,screenshot) values");
        for(GoldListBean bean : goldMoreListBean){
            String strId = String.valueOf(bookId);
            stringBuilder.append("('");
            stringBuilder.append(strId+"','");
            stringBuilder.append("type"+"','");
            stringBuilder.append(bean.getCreatedAt()+"','");
            stringBuilder.append(bean.getTitle()+"',");
            stringBuilder.append(bean.getCollectionCount()+",");
            stringBuilder.append(bean.getCommentsCount()+",'");
            stringBuilder.append(bean.getUrl()+"','");
            stringBuilder.append(bean.getUser().getUsername()+"','");
            stringBuilder.append(bean.getScreenshot()== null ? "')":bean.getScreenshot().getUrl()     +"')");
            stringBuilder.append(",");
            bookId++;

        }
        LogUtil.i(stringBuilder.toString());




        mAdapter.updateData(goldMoreListBean);
        mAdapter.notifyItemRangeInserted(start, end);
        isLoadingMore = false;
    }

    @Override
    public void showError(String msg) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.stop();
        }
        SnackbarUtil.showShort(rvGoldListRecycleView, msg);
    }
}
