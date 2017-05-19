package com.chao.bookviki.ui.best.fragment;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chao.bookviki.R;
import com.chao.bookviki.app.Constants;
import com.chao.bookviki.base.BaseFragment;
import com.chao.bookviki.component.ImageLoader;
import com.chao.bookviki.model.bean.YingWenYuLuBean;
import com.chao.bookviki.presenter.YingWenYuLuPresenter;
import com.chao.bookviki.presenter.contract.YingWenYuLuContract;
import com.chao.bookviki.ui.best.adapter.YingWenYuLuAdapter;
import com.chao.bookviki.util.EnCodeSign;
import com.chao.bookviki.util.SnackbarUtil;
import com.chao.bookviki.util.SystemUtil;
import com.chao.bookviki.widget.ProgressImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Jessica on 2017/5/7.
 */

public class YingWenYuLuFragment extends BaseFragment<YingWenYuLuPresenter> implements YingWenYuLuContract.View {

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

    List<YingWenYuLuBean> mList;
    YingWenYuLuAdapter mAdapter;

    boolean isLoadingMore = false;
    String tech;
    int type;


    @Override
    public void showError(String msg) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.stop();
        }
        SnackbarUtil.showShort(rvTechContent,msg);
    }

    @Override
    public void showYingWenYuLus(List<YingWenYuLuBean> beans) {
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
    public void showMoreYinWenYuLus(List<YingWenYuLuBean> beans) {
        ivProgress.stop();
        mList.addAll(beans);
        mAdapter.notifyDataSetChanged();
        isLoadingMore = false;
    }

    @Override
    public void showImage(String url, String name) {
        ImageLoader.load(mContext, url, ivOrigin);
        Glide.with(mContext).load(url).bitmapTransform(new BlurTransformation(mContext)).into(ivBlur);
        tvCopyright.setText(String.format("by: %s",name));
    }

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
        mPresenter.getRandomImage();
        //android
        tech = getArguments().getString(Constants.YU_LU);
        //104
        type = getArguments().getInt(Constants.YU_LU_CODE);

      //  tech = "";
    //   type = 101;
        mList = new ArrayList<>();
        mAdapter = new YingWenYuLuAdapter(mContext,mList,tech);
        rvTechContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvTechContent.setAdapter(mAdapter);
        ivProgress.start();
        mPresenter.getYingWenYuLu(10,37526, EnCodeSign.md5Encode(10,37526,"412cbe4f62db4cd09c27c7c13b4a8fc1") ,false);
        rvTechContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) rvTechContent.getLayoutManager()).findLastVisibleItemPosition();
                int totalItemCount = rvTechContent.getLayoutManager().getItemCount();
                if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {  //还剩2个Item时加载更多
                    if(!isLoadingMore){
                        isLoadingMore = true;
                        mPresenter.getYingWenYuLu(10,37526, EnCodeSign.md5Encode(10,37526,"412cbe4f62db4cd09c27c7c13b4a8fc1"),true);
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
                mPresenter.getYingWenYuLu(10,37526, EnCodeSign.md5Encode(10,37526,"412cbe4f62db4cd09c27c7c13b4a8fc1"),false);
            }
        });

    }
}
