package com.chao.bookviki.ui.main.fragment;

import com.chao.bookviki.app.Constants;
import com.chao.bookviki.base.SimpleFragment;
import com.chao.bookviki.util.SnackbarUtil;
import com.chao.bookviki.R;
import com.chao.bookviki.util.AlipayUtil;

import butterknife.OnClick;

/**
 * Created by codeest on 16/8/23.
 */

public class AboutFragment extends SimpleFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initEventAndData() {
    }

    @OnClick(R.id.cv_about_award)
    void awardAuthor() {
        if (AlipayUtil.hasInstalledAlipayClient(mContext)) {
            AlipayUtil.startAlipayClient(getActivity(), Constants.KEY_ALIPAY);
        } else {
            SnackbarUtil.showShort(getActivity().getWindow().getDecorView(), "木有检测到支付宝客户端 T T");
        }
    }

}
