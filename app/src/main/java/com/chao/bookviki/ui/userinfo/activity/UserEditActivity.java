package com.chao.bookviki.ui.userinfo.activity;

import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chao.bookviki.R;
import com.chao.bookviki.base.BaseActivity;
import com.chao.bookviki.model.bean.LoginBean;
import com.chao.bookviki.model.bean.PersonalInfoBean;
import com.chao.bookviki.presenter.UserEditPresenter;
import com.chao.bookviki.presenter.contract.UserEditContract;
import com.chao.bookviki.util.SnackbarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jessica on 2017/3/25.
 */

public class UserEditActivity extends BaseActivity<UserEditPresenter> implements UserEditContract.View {



    private MaterialDialog.Builder mInputDialog;
    private LoginBean userBean;
    @BindView(R.id.name_tv)
     TextView name_tv;
    @BindView(R.id.signature_tv)
     TextView signature_tv;
    @BindView(R.id.intro_tv)
     TextView intro_tv;

    @Override
    public void showError(String msg) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);

    }

    @Override
    protected int getLayout() {
        return R.layout.user_edit;
    }

    @Override
    protected void initEventAndData() {
        initDialog();
        userBean = mPresenter.queryLoginBean();
        if (userBean != null){
            name_tv.setText(userBean.getNickName());
            signature_tv.setText(userBean.getSignUpName());
            intro_tv.setText(userBean.getSimpleDesc());
        }
    }

    private void initDialog() {

        mInputDialog = new MaterialDialog.Builder(this);
        mInputDialog.negativeText(getString(R.string.dialog_btn_cancel));
        mInputDialog.positiveText(getString(R.string.dialog_btn_confirm));
        mInputDialog.positiveColorRes(R.color.colorPrimary);
        mInputDialog.negativeColorRes(R.color.colorPrimary);
        mInputDialog.titleColorRes(R.color.text_common);
        mInputDialog.inputType(InputType.TYPE_CLASS_TEXT);
        mInputDialog.widgetColorRes(R.color.colorPrimary);
    }
//, R.id.user_address_ll, R.id.user_github_ll, R.id.user_blog_ll, R.id.user_twitter_ll
    @OnClick({R.id.user_name_ll, R.id.user_signature_ll, R.id.user_intro_ll})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.user_name_ll:
            case R.id.user_signature_ll:
            case R.id.user_intro_ll:
           /* case R.id.user_address_ll:
            case R.id.user_github_ll:
            case R.id.user_blog_ll:
            case R.id.user_twitter_ll:*/
                showDialogs(id);
                break;
            default:
                finish();
                break;
        }
    }

    private void showDialogs(int id) {
        switch (id) {
            case R.id.user_name_ll:
                mInputDialog.title(getString(R.string.dialog_edit_name));
                mInputDialog.input("", userBean != null? userBean.getNickName() : "",nameCallback);
                break;
            case R.id.user_signature_ll:
                mInputDialog.title(getString(R.string.dialog_edit_signature));
                mInputDialog.input("", userBean !=null? userBean.getSignUpName() : "", signatureCallback);
                break;
            case R.id.user_intro_ll:
                mInputDialog.title(getString(R.string.dialog_edit_intro));
                mInputDialog.input( "", userBean !=null? userBean.getSimpleDesc() : "", descCallback);
                break;
           /* case R.id.user_address_ll:
                mInputDialog.title(getString(R.string.dialog_edit_address));
                mInputDialog.input("", "", addressCallback);
                break;
            case R.id.user_github_ll:
                mInputDialog.title(getString(R.string.dialog_edit_github));
                mInputDialog.input("", "", githubCallback);
                break;
            case R.id.user_blog_ll:
                mInputDialog.title(getString(R.string.dialog_edit_blog));
                mInputDialog.input("", "", blogCallback);
                break;
            case R.id.user_twitter_ll:
                mInputDialog.title(getString(R.string.dialog_edit_twitter));
                mInputDialog.input("", "", twitterCallback);
                break;*/
        }

        mInputDialog.show();
    }


    MaterialDialog.InputCallback nameCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
            if (!TextUtils.isEmpty(input)) {
                PersonalInfoBean bean = new PersonalInfoBean();
                bean.setNickName(input.toString());
                name_tv.setText(input.toString());
                mPresenter.postUpdatePersonalInfo(bean);
                mPresenter.updateNick(input.toString());
               // mUserEntity.setName(input.toString());
               // mPresenter.saveUserInfoById(mUserEntity.getId(), mUserEntity);
            }
        }
    };

    MaterialDialog.InputCallback descCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
            if (!TextUtils.isEmpty(input)) {
                PersonalInfoBean bean = new PersonalInfoBean();
                bean.setSimpleDesc(input.toString());
                intro_tv.setText(input.toString());
                mPresenter.postUpdatePersonalInfo(bean);
                mPresenter.updateDesc(input.toString());
               // RxBus.getDefault().post(userBean);
               // mUserEntity.setIntroduction(input.toString());
               // mPresenter.saveUserInfoById(mUserEntity.getId(), mUserEntity);
            }
        }
    };

    MaterialDialog.InputCallback signatureCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
            if (!TextUtils.isEmpty(input)) {
                PersonalInfoBean bean = new PersonalInfoBean();
                bean.setSignUpName(input.toString());
                signature_tv.setText(input.toString());
                mPresenter.postUpdatePersonalInfo(bean);
                mPresenter.updateSigna(input.toString());
              //  mUserEntity.setSignature(input.toString());
              // mPresenter.saveUserInfoById(mUserEntity.getId(), mUserEntity);
            }
        }
    };

    @Override
    public void showUpdateSuc() {
        SnackbarUtil.showShort(getWindow().getDecorView(),"修改成功");
    }

    @Override
    public void showUserInfo(LoginBean bean) {
        this.userBean = bean;
    }
/*

    MaterialDialog.InputCallback addressCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
            if (!TextUtils.isEmpty(input)) {
              //  mUserEntity.setCity(input.toString());
              //  mPresenter.saveUserInfoById(mUserEntity.getId(), mUserEntity);
            }
        }
    };

    MaterialDialog.InputCallback githubCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
            if (!TextUtils.isEmpty(input)) {
               // mUserEntity.setGithub_name(input.toString());
               // mPresenter.saveUserInfoById(mUserEntity.getId(), mUserEntity);
            }
        }
    };

    MaterialDialog.InputCallback blogCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
            if (!TextUtils.isEmpty(input)) {
              //  mUserEntity.setPersonal_website(input.toString());
              //  mPresenter.saveUserInfoById(mUserEntity.getId(), mUserEntity);
            }
        }
    };

    MaterialDialog.InputCallback twitterCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
            if (!TextUtils.isEmpty(input)) {
              //  mUserEntity.setTwitter_account(input.toString());
               // mPresenter.saveUserInfoById(mUserEntity.getId(), mUserEntity);
            }
        }
    };
*/




}
