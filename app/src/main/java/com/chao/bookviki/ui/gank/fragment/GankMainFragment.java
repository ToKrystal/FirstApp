package com.chao.bookviki.ui.gank.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.chao.bookviki.app.Constants;
import com.chao.bookviki.base.SimpleFragment;
import com.chao.bookviki.component.RxBus;
import com.chao.bookviki.model.event.SearchEvent;
import com.chao.bookviki.ui.gank.adapter.GankMainAdapter;
import com.chao.bookviki.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by codeest on 16/8/19.小事
 */

public class GankMainFragment extends SimpleFragment {

    @BindView(R.id.tab_gank_main)
    TabLayout mTabLayout;
    @BindView(R.id.vp_gank_main)
    ViewPager mViewPager;

    public static String[] tabTitle = new String[]{"精选文章","语录","前端","福利"};
    List<Fragment> fragments = new ArrayList<>();

    GankMainAdapter mAdapter;
    TechFragment androidFragment;
  //  TechFragment iOSFragment;
    YingWenYuLuFragment yingWenYuLuFragment;
 //   TechFragment webFragment;
  //  GirlFragment girlFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_main;
    }

    @Override
    protected void initEventAndData() {
        androidFragment = new TechFragment();
        yingWenYuLuFragment = new YingWenYuLuFragment();
      //  iOSFragment = new TechFragment();
    //    webFragment = new TechFragment();
    //    girlFragment = new GirlFragment();
        Bundle androidBundle = new Bundle();
        androidBundle.putString(Constants.JING_XUAN, tabTitle[0]);
        androidBundle.putInt(Constants.JING_XUAN_CODE, Constants.JING_XUAN_CONSTANT);
        androidFragment.setArguments(androidBundle);

        Bundle yuluBundle = new Bundle();
        yuluBundle.putString(Constants.YU_LU, tabTitle[1]);
        yuluBundle.putInt(Constants.YU_LU_CODE, Constants.YU_LU_CONSTATNT);
        yingWenYuLuFragment.setArguments(yuluBundle);
       /* Bundle iosBundle = new Bundle();
        iosBundle.putString(Constants.IT_GANK_TYPE, tabTitle[1]);
        iosBundle.putInt(Constants.IT_GANK_TYPE_CODE, Constants.TYPE_IOS);
        iOSFragment.setArguments(iosBundle);
        Bundle webBundle = new Bundle();
        webBundle.putString(Constants.IT_GANK_TYPE, tabTitle[2]);
        webBundle.putInt(Constants.IT_GANK_TYPE_CODE, Constants.TYPE_WEB);
        webFragment.setArguments(webBundle);*/

       /* Bundle iosBundle = new Bundle();
        iosBundle.putString(Constants.IT_GANK_TYPE, tabTitle[1]);
        iosBundle.putInt(Constants.IT_GANK_TYPE_CODE, Constants.TYPE_IOS);
        iOSFragment.setArguments(iosBundle);*/

        fragments.add(androidFragment);
        fragments.add(yingWenYuLuFragment);
     //   fragments.add(webFragment);
     //   fragments.add(girlFragment);
        mAdapter = new GankMainAdapter(getChildFragmentManager(),fragments);
        mViewPager.setAdapter(mAdapter);

        //TabLayout配合ViewPager有时会出现不显示Tab文字的Bug,需要按如下顺序
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[1]));
   //     mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[2]));
   //     mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[3]));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(tabTitle[0]);
        mTabLayout.getTabAt(1).setText(tabTitle[1]);
   //     mTabLayout.getTabAt(2).setText(tabTitle[2]);
     //   mTabLayout.getTabAt(3).setText(tabTitle[3]);
    }

    public void doSearch(String query) {
        switch (mViewPager.getCurrentItem()) {
            case 0:
                RxBus.getDefault().post(new SearchEvent(query, Constants.TYPE_ANDROID));
                break;
            case 1:
                RxBus.getDefault().post(new SearchEvent(query, Constants.TYPE_IOS));
                break;
            case 2:
                RxBus.getDefault().post(new SearchEvent(query, Constants.TYPE_WEB));
                break;
            case 3:
                RxBus.getDefault().post(new SearchEvent(query, Constants.TYPE_GIRL));
                break;
        }
    }
}
