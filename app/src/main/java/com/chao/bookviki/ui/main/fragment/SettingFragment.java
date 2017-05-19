package com.chao.bookviki.ui.main.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chao.bookviki.R;
import com.chao.bookviki.app.Constants;
import com.chao.bookviki.base.BaseFragment;
import com.chao.bookviki.component.ACache;
import com.chao.bookviki.model.bean.VersionBean;
import com.chao.bookviki.presenter.SettingPresenter;
import com.chao.bookviki.presenter.contract.SettingContract;
import com.chao.bookviki.ui.main.activity.MainActivity;
import com.chao.bookviki.util.ShareUtil;
import com.chao.bookviki.util.SharedPreferenceUtil;
import com.chao.bookviki.util.SnackbarUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by codeest on 16/8/23.
 */

public class SettingFragment extends BaseFragment<SettingPresenter> implements CompoundButton.OnCheckedChangeListener, SettingContract.View{

    @BindView(R.id.cb_setting_cache)
    AppCompatCheckBox cbSettingCache;
    @BindView(R.id.cb_setting_image)
    AppCompatCheckBox cbSettingImage;
    @BindView(R.id.ll_setting_feedback)
    LinearLayout llSettingFeedback;
    @BindView(R.id.tv_setting_clear)
    TextView tvSettingClear;
    @BindView(R.id.ll_setting_clear)
    LinearLayout llSettingClear;
    @BindView(R.id.tv_setting_update)
    TextView tvSettingUpdate;
    @BindView(R.id.ll_setting_update)
    LinearLayout llSettingUpdate;

    private File cacheFile;
    private String versionName;
    private boolean isNull = true;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initEventAndData() {
        cacheFile = new File(Constants.PATH_CACHE);
        tvSettingClear.setText(ACache.getCacheSize(cacheFile));
        cbSettingCache.setChecked(SharedPreferenceUtil.getAutoCacheState());
        cbSettingImage.setChecked(SharedPreferenceUtil.getNoImageState());
        cbSettingCache.setOnCheckedChangeListener(this);
        cbSettingImage.setOnCheckedChangeListener(this);
        try {
            PackageManager pm = getActivity().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getActivity().getPackageName(), PackageManager.GET_ACTIVITIES);
            //1.0.0
            versionName = pi.versionName;
            //build gradle
            tvSettingUpdate.setText(String.format("当前版本 v%s",versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        isNull = savedInstanceState == null;
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.ll_setting_feedback)
    void doFeedBack() {
        ShareUtil.sendEmail(mContext, "请选择使用的邮件客户端:");
    }

    @OnClick(R.id.ll_setting_clear)
    void doClear() {
        ACache.deleteDir(cacheFile);
        tvSettingClear.setText(ACache.getCacheSize(cacheFile));
    }

    @OnClick(R.id.ll_setting_update)
    void doUpdate() {
        mPresenter.checkVersion(versionName);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_setting_image:
                SharedPreferenceUtil.setNoImageState(b);
                break;
            case R.id.cb_setting_cache:
                SharedPreferenceUtil.setAutoCacheState(b);
                break;
        }
    }

    @Override
    public void showUpdateDialog(final VersionBean bean) {
        StringBuilder content = new StringBuilder("版本号: v");
        content.append(bean.getCode());
        content.append("\r\n");
        content.append("版本大小: ");
        content.append(bean.getSize());
        content.append("\r\n");
        content.append("更新内容:\r\n");
        content.append(bean.getDes().replace("\\r\\n","\r\n"));
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
        builder.setTitle("检测到新版本!");
        builder.setMessage(content);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Activity mActivity = getActivity();
                if (mActivity instanceof MainActivity) {
                    ((MainActivity) mActivity).checkPermissions(bean.getDownloadUrl());
                }
            }
        });
        builder.show();
    }

    @Override
    public void showError(String msg) {
        SnackbarUtil.showShort(tvSettingUpdate, msg);
    }
}
