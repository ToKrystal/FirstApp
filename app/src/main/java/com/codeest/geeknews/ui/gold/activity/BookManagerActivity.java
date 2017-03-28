package com.codeest.geeknews.ui.gold.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.codeest.geeknews.R;
import com.codeest.geeknews.app.Constants;
import com.codeest.geeknews.base.SimpleActivity;
import com.codeest.geeknews.component.RxBus;
import com.codeest.geeknews.model.bean.BookManagerBean;
import com.codeest.geeknews.model.bean.BookManagerItemBean;
import com.codeest.geeknews.ui.gold.adapter.BookManagerAdapter;
import com.codeest.geeknews.widget.DefaultItemTouchHelpCallback;

import java.util.Collections;

import butterknife.BindView;
import io.realm.RealmList;

/**
 *设置展示列表显示显示哪些内容
 */

public class BookManagerActivity extends SimpleActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rv_gold_manager_list)
    RecyclerView rvGoldManagerListRecycleView;

    RealmList<BookManagerItemBean> mList;
    BookManagerAdapter mAdapter;
    DefaultItemTouchHelpCallback mCallback;//滑动和拖拽Item回调

    @Override
    protected int getLayout() {
        return R.layout.activity_book_manager;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, "首页特别展示");
        mList = ((BookManagerBean) getIntent().getParcelableExtra(Constants.IT_GOLD_MANAGER)).getManagerList();
        mAdapter = new BookManagerAdapter(mContext, mList);
        rvGoldManagerListRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        rvGoldManagerListRecycleView.setAdapter(mAdapter);
        mCallback = new DefaultItemTouchHelpCallback(new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
            @Override
            public void onSwiped(int adapterPosition) {
            }

            @Override
            public boolean onMove(int srcPosition, int targetPosition) {
                if (mList != null) {
                    Collections.swap(mList, srcPosition, targetPosition);
                    mAdapter.notifyItemMoved(srcPosition, targetPosition);
                    return true;
                }
                return false;
            }
        });
        mCallback.setDragEnable(true);//设置拖拽
        mCallback.setSwipeEnable(false);//设置滑动
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(rvGoldManagerListRecycleView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().post(new BookManagerBean(mList));
    }
}
