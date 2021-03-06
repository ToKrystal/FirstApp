package com.chao.bookviki.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chao.bookviki.app.App;
import com.chao.bookviki.util.SystemUtil;

/**
 * Created by Administrator on 2017/3/28.
 */

public class BookItemDecoration extends RecyclerView.ItemDecoration{

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position > -1) {
            if (position == 0) {
                outRect.set(0, SystemUtil.dp2px(App.getInstance(), 15), 0, 0);
            } else if (position == 3) {
                outRect.set(0, SystemUtil.dp2px(App.getInstance(), 0.5f), 0, SystemUtil.dp2px(App.getInstance(), 15));
            } else {
                outRect.set(0, SystemUtil.dp2px(App.getInstance(), 0.5f), 0, 0);
            }
        }
    }
}
