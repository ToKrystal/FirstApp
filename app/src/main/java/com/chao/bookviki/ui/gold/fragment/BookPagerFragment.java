package com.chao.bookviki.ui.gold.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chao.bookviki.ui.gold.activity.BookDetailActivity;
import com.chao.bookviki.util.LogUtil;
import com.chao.bookviki.util.SnackbarUtil;
import com.chao.bookviki.widget.BookItemDecoration;
import com.chao.bookviki.widget.ProgressImageView;
import com.chao.bookviki.R;
import com.chao.bookviki.app.Constants;
import com.chao.bookviki.base.BaseFragment;
import com.chao.bookviki.model.bean.BookListBean;
import com.chao.bookviki.presenter.BookPresenter;
import com.chao.bookviki.presenter.contract.BookContract;
import com.chao.bookviki.ui.gold.adapter.BookListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/28.
 */

public class BookPagerFragment extends BaseFragment<BookPresenter> implements BookContract.View {

    @BindView(R.id.rv_content)
    RecyclerView rvGoldListRecycleView;
    @BindView(R.id.iv_progress)
    ProgressImageView ivProgress;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private BookListAdapter mAdapter;
    private BookItemDecoration mDecoration;

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
        mType = getArguments().getString(Constants.IT_BOOK_TYPE);
        mDecoration = new BookItemDecoration();
        mAdapter = new BookListAdapter(mContext, new ArrayList<BookListBean>(), getArguments().getString(Constants.IT_BOOK_TYPE_STR));


        mAdapter.setOnItemClickListener(new BookListAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position,View shareView,BookListBean bean) {
                Intent intent = new Intent();
                intent.setClass(mContext, BookDetailActivity.class);
                //详细内容的ID
                intent.putExtra("id",bean.getObjectId());
                intent.putExtra("url","https://pic4.zhimg.com/v2-0983ac630d50d798ba099a0cce8c0ca3.jpg");
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
                        mPresenter.getMoreBookData();
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
                mPresenter.getBookData(mType);
            }
        });
        mAdapter.setOnHotCloseListener(new BookListAdapter.OnHotCloseListener() {
            @Override
            public void onClose() {
                rvGoldListRecycleView.removeItemDecoration(mDecoration);
            }
        });
        ivProgress.start();
        mPresenter.getBookData(mType);
    }

    @Override
    public void showContent(List<BookListBean> goldListBean) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.stop();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("insert into book_list(objectid,type,createdat,title,collectioncount,commentscount,url,user,screenshot) values");
        for(BookListBean bean : goldListBean){
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
    public void showMoreContent(List<BookListBean> goldMoreListBean, int start, int end) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("insert into book_list(objectid,type,createdat,title,collectioncount,commentscount,url,user,screenshot) values");
        for(BookListBean bean : goldMoreListBean){
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
