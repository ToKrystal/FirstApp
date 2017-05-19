package com.chao.bookviki.ui.gold.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chao.bookviki.ui.gold.activity.BookFollowActivity;
import com.chao.bookviki.util.LogUtil;
import com.chao.bookviki.util.SnackbarUtil;
import com.chao.bookviki.R;
import com.chao.bookviki.app.Constants;
import com.chao.bookviki.base.BaseFragment;
import com.chao.bookviki.model.bean.BookManagerBean;
import com.chao.bookviki.model.bean.BookManagerItemBean;
import com.chao.bookviki.presenter.BookMainPresenter;
import com.chao.bookviki.presenter.contract.BookMainContract;
import com.chao.bookviki.ui.gold.adapter.BookPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 日常主界面
 */

public class BookMainFragment extends BaseFragment<BookMainPresenter> implements BookMainContract.View {

    @BindView(R.id.tab_gold_main)
    TabLayout mTabLayout;
    @BindView(R.id.vp_gold_main)
    ViewPager mViewPager;

    public static String[] typeStr = {"日常", "美文", "格言", "生活", "体育", "娱乐", "电影", "游戏"};
    public static String[] type = {"daily", "beautifultxt", "geyan", "shenghuo", "tiyu", "yule", "dianying", "youxi"};

    List<BookPagerFragment> fragments = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frgament_book_main;
    }

    @Override
    protected void initEventAndData() {
        LogUtil.i("首页InitEventAdaData调用");
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab滚动
        mTabLayout.setupWithViewPager(mViewPager);
        mPresenter.initManagerList();
    }

    @Override
    public void updateTab(List<BookManagerItemBean> mList) {
        fragments.clear();
        mTabLayout.removeAllTabs();
        for (BookManagerItemBean item : mList) {
            if (item.getIsSelect()) {
                BookPagerFragment fragment = new BookPagerFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.IT_GOLD_TYPE, type[item.getIndex()]);
                bundle.putString(Constants.IT_GOLD_TYPE_STR, typeStr[item.getIndex()]);
                mTabLayout.addTab(mTabLayout.newTab().setText(typeStr[item.getIndex()]));
                fragment.setArguments(bundle);
                fragments.add(fragment);
            }
        }
        BookPagerAdapter mAdapter = new BookPagerAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        for (BookManagerItemBean item : mList) {
            if (item.getIsSelect()) {
                mTabLayout.getTabAt(currentIndex++).setText(typeStr[item.getIndex()]);
            }
        }
        currentIndex = 0;
    }

    /**
     * 本地存的
     * @param mBean
     */
    @Override
    public void jumpToManager(BookManagerBean mBean) {
        Intent intent = new Intent(getActivity(), BookFollowActivity.class);
        intent.putExtra(Constants.IT_GOLD_MANAGER, mBean);
        mContext.startActivity(intent);
    }

    @Override
    public void showError(String msg) {
        SnackbarUtil.showShort(mTabLayout, msg);
    }

    @OnClick(R.id.iv_gold_menu)
    public void onClick(View view) {
        mPresenter.setManagerList();
    }
}
