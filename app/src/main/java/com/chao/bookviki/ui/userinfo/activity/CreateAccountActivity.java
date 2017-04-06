package com.chao.bookviki.ui.userinfo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chao.bookviki.R;
import com.chao.bookviki.base.BaseActivity;
import com.chao.bookviki.model.bean.CreateAccountBean;
import com.chao.bookviki.model.bean.LoginBean;
import com.chao.bookviki.presenter.CreateAccountPresenter;
import com.chao.bookviki.presenter.contract.CreateAccountContract;
import com.chao.bookviki.ui.main.activity.MainActivity;
import com.chao.bookviki.util.SnackbarUtil;

import butterknife.BindView;

/**
 * Created by Jessica on 2017/4/4.
 */

public class CreateAccountActivity extends BaseActivity<CreateAccountPresenter> implements CreateAccountContract.View {

    @BindView(R.id.link_login)
    TextView loginTxt;
    ProgressDialog progressDialog;
    @BindView(R.id.input_name)
    EditText input_name;
    @BindView(R.id.input_password)
    EditText input_password;
    @BindView(R.id.input_email)
    EditText inout_email;
    @BindView(R.id.btn_signup)
    AppCompatButton create_btn;


    @Override
    public void showError(String msg) {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        create_btn.setEnabled(true);
        SnackbarUtil.showShort(getWindow().getDecorView(),"邮箱已经被注册");
    }

    @Override
    public void jump2CreateSucc(LoginBean bean) {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        mPresenter.insertLoginBean();
        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        intent.putExtra("loginOk",true);
        Bundle bundle = new Bundle();
        bundle.putParcelable("beanInfo",bean);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_signup;
    }

    @Override
    protected void initEventAndData() {
        progressDialog= new ProgressDialog(this);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    // onLoginFailed();
                    return;
                }
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Creating Account...");
                progressDialog.show();
                String name = input_name.getText().toString();
                String email = inout_email.getText().toString();
                String password = input_password.getText().toString();
                CreateAccountBean bean = new CreateAccountBean(name,password,email);
                mPresenter.postCreateAccount(bean);

            }
        });

        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        String name = input_name.getText().toString();
        String email = inout_email.getText().toString();
        String password = input_password.getText().toString();
        if (name.isEmpty() || name.length() < 3) {
            input_name.setError("at least 3 characters");
            valid = false;
        } else {
            input_name.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inout_email.setError("enter a valid email address");
            valid = false;
        } else {
            inout_email.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            input_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            input_password.setError(null);
        }
        return valid;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
