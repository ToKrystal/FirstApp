package com.chao.bookviki.ui.best.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.chao.bookviki.app.Constants;
import com.chao.bookviki.base.SimpleFragment;
import com.chao.bookviki.component.RxBus;
import com.chao.bookviki.model.event.SearchEvent;
import com.chao.bookviki.ui.best.adapter.BestMainAdapter;
import com.chao.bookviki.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class BestMainFragment extends SimpleFragment {

    @BindView(R.id.tab_gank_main)
    TabLayout mTabLayout;
    @BindView(R.id.vp_gank_main)
    ViewPager mViewPager;

    public static String[] tabTitle = new String[]{"精选文章","语录","备用","备用"};
    List<Fragment> fragments = new ArrayList<>();

    BestMainAdapter mAdapter;
    TechFragment androidFragment;
    YingWenYuLuFragment yingWenYuLuFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_best_main;
    }

    @Override
    protected void initEventAndData() {
        androidFragment = new TechFragment();
        yingWenYuLuFragment = new YingWenYuLuFragment();
        Bundle androidBundle = new Bundle();
        androidBundle.putString(Constants.JING_XUAN, tabTitle[0]);
        androidBundle.putInt(Constants.JING_XUAN_CODE, Constants.JING_XUAN_CONSTANT);
        androidFragment.setArguments(androidBundle);

        Bundle yuluBundle = new Bundle();
        yuluBundle.putString(Constants.YU_LU, tabTitle[1]);
        yuluBundle.putInt(Constants.YU_LU_CODE, Constants.YU_LU_CONSTATNT);
        yingWenYuLuFragment.setArguments(yuluBundle);


        fragments.add(androidFragment);
        fragments.add(yingWenYuLuFragment);
        mAdapter = new BestMainAdapter(getChildFragmentManager(),fragments);
        mViewPager.setAdapter(mAdapter);

        //TabLayout配合ViewPager有时会出现不显示Tab文字的Bug,需要按如下顺序
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[1]));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(tabTitle[0]);
        mTabLayout.getTabAt(1).setText(tabTitle[1]);
    }

    public void doSearch(String query) {
        switch (mViewPager.getCurrentItem()) {
            case 0:
                RxBus.getDefault().post(new SearchEvent(query, Constants.JING_XUAN_CONSTANT));
                break;
            case 1:
                RxBus.getDefault().post(new SearchEvent(query, Constants.YU_LU_CONSTATNT));
                break;

        }
    }
}
