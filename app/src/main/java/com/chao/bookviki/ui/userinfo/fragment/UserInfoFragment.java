package com.chao.bookviki.ui.userinfo.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chao.bookviki.R;
import com.chao.bookviki.base.BaseFragment;
import com.chao.bookviki.component.ImageLoader;
import com.chao.bookviki.model.bean.LoginBean;
import com.chao.bookviki.presenter.UserInfoPresenter;
import com.chao.bookviki.presenter.contract.UserInfoContract;
import com.chao.bookviki.ui.userinfo.activity.UserEditActivity;

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
    @BindView(R.id.user_reply_tv)
    TextView replayTxt;
    @BindView(R.id.user_follow_tv)
    TextView followTxt;
    @BindView(R.id.user_name_tv)
    TextView userName;
    @BindView(R.id.user_real_name_tv)
    TextView nickName;
    @BindView(R.id.signature_tv)
    TextView signatureName;
    @BindView(R.id.user_avatar_civ)
    ImageView avatar_image;






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
        mPresenter.queryLoginBean();

    }

    @Override
    public void showContent(LoginBean bean) {
        replayTxt.setText(bean.getReplayNum()+"");
        followTxt.setText(bean.getFollowNum()+"");
        userName.setText(bean.getName() == null ? "请设置":bean.getName());
        nickName.setText(bean.getNickName() == null ? "":bean.getNickName());
        signatureName.setText(bean.getSignUpName() == null ? "" :bean.getSignUpName());
        ImageLoader.load(mContext,bean.getIcon_url(),avatar_image);

    }

    @Override
    public void showDefaultUserInfo() {
        super.showDefaultUserInfo();
        replayTxt.setText("0");
        followTxt.setText("0");
        userName.setText("暂无");
        nickName.setText("");
        signatureName.setText("");
        avatar_image.setImageResource(R.drawable.image_placeholder);
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
