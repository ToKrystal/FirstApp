package com.codeest.geeknews.ui.gold.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.codeest.geeknews.R;
import com.codeest.geeknews.app.Constants;
import com.codeest.geeknews.base.BaseFragment;
import com.codeest.geeknews.model.bean.BookManagerBean;
import com.codeest.geeknews.model.bean.BookManagerItemBean;
import com.codeest.geeknews.presenter.BookMainPresenter;
import com.codeest.geeknews.presenter.contract.BookMainContract;
import com.codeest.geeknews.ui.gold.activity.BookManagerActivity;
import com.codeest.geeknews.ui.gold.adapter.BookPagerAdapter;
import com.codeest.geeknews.util.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/28.
 */

public class BookMainFragment extends BaseFragment<BookMainPresenter> implements BookMainContract.View {

    @BindView(R.id.tab_gold_main)
    TabLayout mTabLayout;
    @BindView(R.id.vp_gold_main)
    ViewPager mViewPager;

    public static String[] typeStr = {"Android", "iOS", "前端", "后端", "设计", "产品", "阅读", "工具资源"};
    public static String[] type = {"android", "ios", "frontend", "backend", "design", "product", "article", "freebie"};

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

    @Override
    public void jumpToManager(BookManagerBean mBean) {
        Intent intent = new Intent(getActivity(), BookManagerActivity.class);
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
