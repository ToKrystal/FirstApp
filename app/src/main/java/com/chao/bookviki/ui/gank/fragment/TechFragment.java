package com.chao.bookviki.ui.gank.fragment;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chao.bookviki.R;
import com.chao.bookviki.app.Constants;
import com.chao.bookviki.base.BaseFragment;
import com.chao.bookviki.component.ImageLoader;
import com.chao.bookviki.model.bean.JingXuanNewsBean;
import com.chao.bookviki.presenter.TechPresenter;
import com.chao.bookviki.presenter.contract.TechContract;
import com.chao.bookviki.ui.gank.activity.TechDetailActivity;
import com.chao.bookviki.ui.gank.adapter.TechAdapter;
import com.chao.bookviki.util.SnackbarUtil;
import com.chao.bookviki.util.SystemUtil;
import com.chao.bookviki.widget.ProgressImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by codeest on 16/8/19.
 */

public class TechFragment extends BaseFragment<TechPresenter> implements TechContract.View {

    @BindView(R.id.rv_tech_content)
    RecyclerView rvTechContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.iv_progress)
    ProgressImageView ivProgress;
    @BindView(R.id.iv_tech_blur)
    ImageView ivBlur;
    @BindView(R.id.iv_tech_origin)
    ImageView ivOrigin;
    @BindView(R.id.tv_tech_copyright)
    TextView tvCopyright;
    @BindView(R.id.tech_appbar)
    AppBarLayout appbar;

  //  List<GankItemBean> mList;
    List<JingXuanNewsBean> mList;
    TechAdapter mAdapter;

    boolean isLoadingMore = false;
    String tech;
    int type;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tech;
    }

    @Override
    protected void initEventAndData() {
        mPresenter.getGirlImage();
        mList = new ArrayList<>();
        //android
        tech = getArguments().getString(Constants.IT_GANK_TYPE);
        //104
        type = getArguments().getInt(Constants.IT_GANK_TYPE_CODE);
        mAdapter = new TechAdapter(mContext,mList,tech);
        rvTechContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvTechContent.setAdapter(mAdapter);
        ivProgress.start();
        //获取精选新闻
        mPresenter.getJingXuanNews("war");
     //   mPresenter.getGankData(tech, type);
        //点击跳转
        mAdapter.setOnItemClickListener(new TechAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View shareView) {
                TechDetailActivity.launch(new TechDetailActivity.Builder()
                        .setContext(mContext)
                        .setId(String.valueOf(mList.get(position).getId()))
                        .setTitle(mList.get(position).getTitle())
                        .setUrl(mList.get(position).getDocurl())
                        .setType(type)
                .setAnimConfig(mActivity, shareView));
            }
        });
        rvTechContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) rvTechContent.getLayoutManager()).findLastVisibleItemPosition();
                int totalItemCount = rvTechContent.getLayoutManager().getItemCount();
                if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {  //还剩2个Item时加载更多
                    if(!isLoadingMore){
                        isLoadingMore = true;
                        mPresenter.getMoreJingXuanNews("war");
                    }
                }
            }
        });
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    swipeRefresh.setEnabled(true);
                } else {
                    swipeRefresh.setEnabled(false);
                    float rate = (float)(SystemUtil.dp2px(mContext, 256) + verticalOffset * 2) / SystemUtil.dp2px(mContext, 256);
                    if (rate >= 0)
                    ivOrigin.setAlpha(rate);
                }
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getJingXuanNews("war");
            }
        });
    }

    @Override
    public void showError(String msg) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.stop();
        }
        SnackbarUtil.showShort(rvTechContent,msg);
    }

   /* @Override
    public void showContent(List<GankItemBean> list) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.stop();
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }*/

   /* @Override
    public void showMoreContent(List<GankItemBean> list) {
        ivProgress.stop();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
        isLoadingMore = false;
    }*/

    @Override
    public void showGirlImage(String url, String copyright) {
        ImageLoader.load(mContext, url, ivOrigin);
        Glide.with(mContext).load(url).bitmapTransform(new BlurTransformation(mContext)).into(ivBlur);
        tvCopyright.setText(String.format("by: %s",copyright));
    }

    @Override
    public void showJingXuanItem(List<JingXuanNewsBean> beans) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.stop();
        }
        mList.clear();
        mList.addAll(beans);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void showMoreJingXuanItem(List<JingXuanNewsBean> beans) {
        ivProgress.stop();
        mList.addAll(beans);
        mAdapter.notifyDataSetChanged();
        isLoadingMore = false;
    }
}
