package com.chao.bookviki.ui.gold.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chao.bookviki.base.BaseFragment;
import com.chao.bookviki.presenter.contract.BookCommentContract;
import com.chao.bookviki.ui.gold.adapter.BookCommentAdapter;
import com.chao.bookviki.widget.ProgressImageView;
import com.chao.bookviki.R;
import com.chao.bookviki.model.bean.BookCommentBean;
import com.chao.bookviki.presenter.BookCommentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by codeest on 16/8/18.
 */

public class BookCommentFragment extends BaseFragment<BookCommentPresenter> implements BookCommentContract.View {

    @BindView(R.id.rv_comment_list)
    RecyclerView rvCommentList;
    @BindView(R.id.iv_progress)
    ProgressImageView ivProgress;

    BookCommentAdapter mAdapter;
    List<BookCommentBean.CommentsBean> mList;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initEventAndData() {
        Bundle bundle = getArguments();
       // mPresenter.getCommentData(bundle.getInt("id"),bundle.getInt("kind"));
        ivProgress.start();
        rvCommentList.setVisibility(View.INVISIBLE);
        mList = new ArrayList<>();
        mAdapter = new BookCommentAdapter(mContext,mList);
        rvCommentList.setLayoutManager(new LinearLayoutManager(mContext));
        rvCommentList.setAdapter(mAdapter);
    }

   /* @Override
    public void showContent(BookCommentBean bookCommentBean) {
        ivProgress.stop();
        rvCommentList.setVisibility(View.VISIBLE);
        mList.clear();
        mList.addAll(bookCommentBean.getComments());
        mAdapter.notifyDataSetChanged();
    }*/

    @Override
    public void showError(String msg) {
        ivProgress.stop();
        rvCommentList.setVisibility(View.VISIBLE);
    }
}
