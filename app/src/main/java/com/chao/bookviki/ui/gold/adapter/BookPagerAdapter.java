package com.chao.bookviki.ui.gold.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chao.bookviki.ui.gold.fragment.BookPagerFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */

public class BookPagerAdapter extends FragmentStatePagerAdapter {

    private List<BookPagerFragment> fragments;

    public BookPagerAdapter(FragmentManager fm, List<BookPagerFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
