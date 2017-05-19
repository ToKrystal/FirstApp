package com.chao.bookviki.ui.daily.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.chao.bookviki.R;
import com.chao.bookviki.app.Constants;
import com.chao.bookviki.base.BaseActivity;
import com.chao.bookviki.component.RxBus;
import com.chao.bookviki.model.bean.BookManagerBean;
import com.chao.bookviki.model.bean.BookManagerItemBean;
import com.chao.bookviki.presenter.FollowPresenter;
import com.chao.bookviki.presenter.contract.FollowContract;
import com.chao.bookviki.ui.daily.adapter.BookFollowAdapter;
import com.chao.bookviki.util.SnackbarUtil;
import com.chao.bookviki.widget.DefaultItemTouchHelpCallback;

import java.util.Collections;

import butterknife.BindView;
import io.realm.RealmList;

/**
 *设置展示列表显示显示哪些内容
 */

public class BookFollowActivity extends BaseActivity<FollowPresenter> implements FollowContract.View {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rv_gold_manager_list)
    RecyclerView rvGoldManagerListRecycleView;

    RealmList<BookManagerItemBean> mList;
    BookFollowAdapter mAdapter;
    DefaultItemTouchHelpCallback mCallback;//滑动和拖拽Item回调

    @Override
    protected int getLayout() {
        return R.layout.activity_book_manager;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, "关注");
        //获取从main界面传过来的关注数据
        mList = ((BookManagerBean) getIntent().getParcelableExtra(Constants.IT_GOLD_MANAGER)).getManagerList();
        mAdapter = new BookFollowAdapter(mContext, mList);
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

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String msg) {
        SnackbarUtil.showShort(getWindow().getDecorView(),"操作失败");
    }

    @Override
    public void showFollowSuc() {
        SnackbarUtil.showShort(getWindow().getDecorView(),"关注成功");

    }

    @Override
    public void showUnFollowSuc() {
        SnackbarUtil.showShort(getWindow().getDecorView(),"取消关注成功");
    }
}
