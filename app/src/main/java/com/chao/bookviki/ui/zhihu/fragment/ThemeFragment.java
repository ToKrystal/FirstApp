package com.chao.bookviki.ui.zhihu.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chao.bookviki.base.BaseFragment;
import com.chao.bookviki.presenter.contract.ThemeContract;
import com.chao.bookviki.ui.zhihu.activity.ThemeActivity;
import com.chao.bookviki.ui.zhihu.adapter.ThemeAdapter;
import com.chao.bookviki.util.SnackbarUtil;
import com.chao.bookviki.widget.ProgressImageView;
import com.chao.bookviki.R;
import com.chao.bookviki.model.bean.ThemeListBean;
import com.chao.bookviki.presenter.ThemePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by codeest on 2016/8/11.
 */
public class ThemeFragment extends BaseFragment<ThemePresenter> implements ThemeContract.View {

    @BindView(R.id.rv_content)
    RecyclerView rvThemeList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.iv_progress)
    ProgressImageView ivProgress;

    ThemeAdapter mAdapter;
    List<ThemeListBean.OthersBean> mList = new ArrayList<>();

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
        mAdapter = new ThemeAdapter(mContext, mList);
        rvThemeList.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvThemeList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ThemeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int id) {
                Intent intent = new Intent();
                intent.setClass(mContext, ThemeActivity.class);
                intent.putExtra("id", id);
                mContext.startActivity(intent);
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getThemeData();
            }
        });
        mPresenter.getThemeData();
        ivProgress.start();
    }

    @Override
    public void showContent(ThemeListBean themeListBean) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.stop();
        }
        mList.clear();
        mList.addAll(themeListBean.getOthers());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String msg) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.stop();
        }
        SnackbarUtil.showShort(rvThemeList,msg);
    }
}
