package com.chao.bookviki.ui.userinfo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chao.bookviki.R;
import com.chao.bookviki.base.BaseActivity;
import com.chao.bookviki.model.bean.LoginBean;
import com.chao.bookviki.presenter.LoginPresenter;
import com.chao.bookviki.presenter.contract.LoginContract;
import com.chao.bookviki.util.SnackbarUtil;

import butterknife.BindView;

/**
 * Created by Jessica on 2017/4/4.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.link_signup)
    TextView create_account;
    @BindView(R.id.btn_login)
    AppCompatButton login_btn;
    @BindView(R.id.input_email)
    EditText email_txt;
    @BindView(R.id.input_password)
    EditText pass_txt;
    ProgressDialog progressDialog;

    private String email;
    private String pass;



    @Override
    public void showError(String msg) {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        login_btn.setEnabled(true);
        SnackbarUtil.showShort(getWindow().getDecorView(),"用户名或者密码错误");
    }

    @Override
    public void jump2LoginSucc(LoginBean bean) {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        mPresenter.insertLoginData(bean);
        //Intent intent = new Intent();
      //  intent.setClass(this,MainActivity.class);
       // startActivity(intent);

        finish();
        //overridePendingTransition(android.R.anim., android.R.anim.fade_out);

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CreateAccountActivity.class);
                mContext.startActivity(intent);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                   // onLoginFailed();
                    return;
                }
                login_btn.setEnabled(false);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();
                mPresenter.postLogin(email,pass);


            }
        });
    }

    public boolean validate() {
        boolean valid = true;
        String email = email_txt.getText().toString().trim();
        String password = pass_txt.getText().toString().trim();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_txt.setError("enter a valid email");
            valid = false;
        } else {
            email_txt.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            pass_txt.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            pass_txt.setError(null);
        }

        this.email = email;
        this.pass = password;
        return valid;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
