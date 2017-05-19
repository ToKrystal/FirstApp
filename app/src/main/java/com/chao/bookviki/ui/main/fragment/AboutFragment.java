package com.chao.bookviki.ui.main.fragment;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.TextView;

import com.baidu.android.pushservice.PushManager;
import com.chao.bookviki.R;
import com.chao.bookviki.base.SimpleFragment;
import com.chao.bookviki.util.SharedPreferenceUtil;
import com.chao.bookviki.util.SnackbarUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class AboutFragment extends SimpleFragment {

    @BindView(R.id.versionTxt)
    TextView version_txt;
    String applicationName = "ss";
    String versionName = "0.0.0";
    private int clickNum = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initEventAndData() {
        try {
            PackageManager pm = getActivity().getPackageManager();
            ApplicationInfo applicationInfo = pm.getApplicationInfo(getActivity().getPackageName(),0);
            PackageInfo pi = pm.getPackageInfo(getActivity().getPackageName(), PackageManager.GET_ACTIVITIES);
            //1.0.0
            versionName = pi.versionName;
            applicationName = (String) pm.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version_txt.setText(applicationName+"\t\t"+versionName);
    }

    @OnClick(R.id.cv_about_award)
    void awardAuthor() {
        //    if (AlipayUtil.hasInstalledAlipayClient(mContext)) {
        //       AlipayUtil.startAlipayClient(getActivity(), Constants.KEY_ALIPAY);
        //   } else {
        SnackbarUtil.showShort(getActivity().getWindow().getDecorView(), "少年还没有安装微信客户端哦!");
        //   }
    }

    @OnClick(R.id.tuisong)
    void stopTuisong() {
        clickNum++;
        if (clickNum >=5){
            //停止推送
            clickNum = 0;
              PushManager.stopWork(mContext);
              SharedPreferenceUtil.setBaiYunBindState(false);
            SnackbarUtil.showShort(getActivity().getWindow().getDecorView(),"正在停止推送绑定");

        }
    }

}
