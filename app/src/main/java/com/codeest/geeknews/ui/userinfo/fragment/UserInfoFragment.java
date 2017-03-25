package com.codeest.geeknews.ui.userinfo.fragment;

import android.content.Intent;
import android.view.View;

import com.codeest.geeknews.R;
import com.codeest.geeknews.base.BaseFragment;
import com.codeest.geeknews.presenter.UserInfoPresenter;
import com.codeest.geeknews.presenter.contract.UserInfoContract;
import com.codeest.geeknews.ui.userinfo.activity.UserEditActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jessica on 2017/3/25.
 */

public class UserInfoFragment extends BaseFragment<UserInfoPresenter> implements UserInfoContract.View {

   /* @BindView(R.id.user_name_ll)
    LinearLayout mlinearLayout;
    @BindView(R.id.name_tv)
    TextView  name_tv;*/
   /*@BindView(R.id.user_avatar_civ)
    MySimpleDraweeView mySimpleDraweeView;*/
   @BindView(R.id.edit_tv)
   View deit;




    @Override
    public void showError(String msg) {

    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_info;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void jumpToEdit() {
        Intent intent = new Intent(getActivity(), UserEditActivity.class);
        //intent.putExtra(Constants.IT_GOLD_MANAGER, mBean);
        mContext.startActivity(intent);
    }


    @OnClick(R.id.edit_tv)
    public void onClick(View view) {
        mPresenter.seteditData();
    }

}
