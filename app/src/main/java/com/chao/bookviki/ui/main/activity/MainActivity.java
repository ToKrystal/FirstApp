package com.chao.bookviki.ui.main.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.baidu.android.pushservice.PushManager;
import com.chao.bookviki.R;
import com.chao.bookviki.app.Constants;
import com.chao.bookviki.base.BaseActivity;
import com.chao.bookviki.component.RxBus;
import com.chao.bookviki.component.UpdateService;
import com.chao.bookviki.model.bean.LogOutBean;
import com.chao.bookviki.model.bean.LoginBean;
import com.chao.bookviki.model.bean.MyPushBean;
import com.chao.bookviki.model.event.SearchEvent;
import com.chao.bookviki.presenter.MainPresenter;
import com.chao.bookviki.presenter.contract.MainContract;
import com.chao.bookviki.ui.gank.fragment.GankMainFragment;
import com.chao.bookviki.ui.gold.activity.BookDetailActivity;
import com.chao.bookviki.ui.gold.fragment.BookMainFragment;
import com.chao.bookviki.ui.main.fragment.AboutFragment;
import com.chao.bookviki.ui.main.fragment.LikeFragment;
import com.chao.bookviki.ui.main.fragment.SettingFragment;
import com.chao.bookviki.ui.userinfo.activity.LoginActivity;
import com.chao.bookviki.ui.userinfo.fragment.UserInfoFragment;
import com.chao.bookviki.ui.zhihu.fragment.ZhihuMainFragment;
import com.chao.bookviki.util.SharedPreferenceUtil;
import com.chao.bookviki.util.SnackbarUtil;
import com.chao.bookviki.util.SystemUtil;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by codeest on 16/8/9.
 */

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.drawer)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.navigation)
    NavigationView mNavigationView;
    @BindView(R.id.view_search)
    MaterialSearchView mSearchView;
    //注销
    View btnLogout;
    //登录头像
    View img_avatar;
    //用户名
    TextView tv_login_name;

    private LoginBean mLoginBean;

    ZhihuMainFragment mZhihuFragment;
    GankMainFragment mGankFragment;
    // GoldMainFragment mGoldFragment;
    LikeFragment mLikeFragment;
    SettingFragment mSettingFragment;
    AboutFragment mAboutFragment;
    UserInfoFragment userInfoFragment;
    BookMainFragment mBookMainFragment;

    MenuItem mLastMenuItem;
    MenuItem mSearchMenuItem;
    ActionBarDrawerToggle mDrawerToggle;

    private int hideFragment = Constants.TYPE_ZHIHU;
    private int showFragment = Constants.TYPE_ZHIHU;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    /**
     * 由于recreate 需要特殊处理夜间模式
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            SharedPreferenceUtil.setNightModeState(false);
        } else {
            showFragment = SharedPreferenceUtil.getCurrentItem();
            hideFragment = Constants.TYPE_ZHIHU;

            showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
            mNavigationView.getMenu().findItem(R.id.drawer_zhihu).setChecked(false);
            //设置toolbar文字为Menu Item 文字
            mToolbar.setTitle(mNavigationView.getMenu().findItem(getCurrentItem(showFragment)).getTitle().toString());
            hideFragment = showFragment;
        }
    }

    @Override
    protected void initEventAndData() {
        setToolBar(mToolbar,"发现阅读");
        mZhihuFragment = new ZhihuMainFragment();
        mGankFragment = new GankMainFragment();
        // mGoldFragment = new GoldMainFragment();
        mBookMainFragment = new BookMainFragment();
        mLikeFragment = new LikeFragment();
        mSettingFragment = new SettingFragment();
        mAboutFragment = new AboutFragment();
        userInfoFragment = new UserInfoFragment();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mLastMenuItem = mNavigationView.getMenu().findItem(R.id.drawer_gank);
        loadMultipleRootFragment(R.id.fl_main_content,0,mZhihuFragment,mGankFragment,mBookMainFragment,mLikeFragment,mSettingFragment,mAboutFragment,userInfoFragment);
        btnLogout =  mNavigationView.getHeaderView(0).findViewById(R.id.btn_logout);
        img_avatar = mNavigationView.getHeaderView(0).findViewById(R.id.img_avatar);
        tv_login_name = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.tv_login_name);
        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImgAvatarClick();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnLogoutClick();
            }
        });
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {//点击导航事件
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.drawer_zhihu:
                        showFragment = Constants.TYPE_ZHIHU;
                        mSearchMenuItem.setVisible(false);
                        break;
                    case R.id.drawer_gank:
                        showFragment = Constants.TYPE_GANK;
                        mSearchMenuItem.setVisible(true);
                        break;
                    /*case R.id.drawer_gold:
                        showFragment = Constants.TYPE_GOLD;
                        mSearchMenuItem.setVisible(false);
                        break;*/
                    case R.id.drawer_book:
                        showFragment = Constants.TYPE_GOLD;
                        mSearchMenuItem.setVisible(false);
                        break;

                    case R.id.drawer_setting:
                        showFragment = Constants.TYPE_SETTING;
                        mSearchMenuItem.setVisible(false);
                        break;
                    case R.id.drawer_like:
                        showFragment = Constants.TYPE_LIKE;
                        mSearchMenuItem.setVisible(false);
                        break;
                    case R.id.drawer_about:
                        showFragment = Constants.TYPE_ABOUT;
                        mSearchMenuItem.setVisible(false);
                        break;
                    case  R.id.drawer_userInfo:
                        showFragment = Constants.TYPE_USER_INFO;
                        mSearchMenuItem.setVisible(false);
                        break;
                }
                if(mLastMenuItem != null) {
                    mLastMenuItem.setChecked(false);
                }
                mLastMenuItem = menuItem;
                //保存正在展示的fragment标志
                SharedPreferenceUtil.setCurrentItem(showFragment);
                menuItem.setChecked(true);
                mToolbar.setTitle(menuItem.getTitle());
                mDrawerLayout.closeDrawers();
                showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                hideFragment = showFragment;
                return true;
            }
        });
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(showFragment == Constants.TYPE_GANK) {
                    mGankFragment.doSearch(query);
                } else if(showFragment == Constants.TYPE_WECHAT) {
                    RxBus.getDefault().post(new SearchEvent(query, Constants.TYPE_WECHAT));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        if (!SharedPreferenceUtil.getVersionPoint() && SystemUtil.isWifiConnected()) {
            SharedPreferenceUtil.setVersionPoint(true);
            try {
                PackageManager pm = getPackageManager();
                PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
                String versionName = pi.versionName;
                mPresenter.checkVersion(versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        mPresenter.queryLoginState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        mSearchView.setMenuItem(item);
        mSearchMenuItem = item;
        return true;
    }

    @Override
    public void showError(String msg) {
        SnackbarUtil.showShort(mToolbar,msg);
    }

    @Override
    public void onBackPressedSupport() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            showExitDialog();
        }
    }

    private void showExitDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出GeekNews吗");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PushManager.stopWork(mContext);
                SharedPreferenceUtil.setBaiYunBindState(false);
            //  App.getInstance().exitApp();
               // RxBus.getDefault().post(new MyPushBean());

            }
        });
        builder.show();
    }

    private SupportFragment getTargetFragment(int item) {
        switch (item) {
            case Constants.TYPE_ZHIHU:
                return mZhihuFragment;
            case Constants.TYPE_GANK:
                return mGankFragment;
            case Constants.TYPE_GOLD:
                // return mGoldFragment;

                return mBookMainFragment;
            case Constants.TYPE_LIKE:
                return mLikeFragment;
            case Constants.TYPE_SETTING:
                return mSettingFragment;
            case Constants.TYPE_ABOUT:
                return mAboutFragment;
            case Constants.TYPE_USER_INFO:
                return userInfoFragment;
        }
        return mZhihuFragment;
    }

    private int getCurrentItem(int item) {
        switch (item) {
            case Constants.TYPE_ZHIHU:
                return R.id.drawer_zhihu;

            case Constants.TYPE_GANK:
                return R.id.drawer_gank;

            case Constants.TYPE_GOLD:
                return R.id.drawer_book;


            case Constants.TYPE_LIKE:
                return R.id.drawer_like;
            case Constants.TYPE_SETTING:
                return R.id.drawer_setting;
            case Constants.TYPE_ABOUT:
                return R.id.drawer_about;
            case Constants.TYPE_USER_INFO:
                return R.id.drawer_userInfo;

        }
        return R.id.drawer_zhihu;
    }

    @Override
    public void showUpdateDialog(String versionContent) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("检测到新版本!");
        builder.setMessage(versionContent);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkPermissions();
            }
        });
        builder.show();
    }

    /**
     * 注销按钮
     */
    void onBtnLogoutClick() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("确定要注销吗？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.doLogout();
               if (mLoginBean != null){
                   mPresenter.deleteLoginBean(mLoginBean);
                   mLoginBean = null;
                  RxBus.getDefault().post(new LogOutBean());
               }
            }
        });
        builder.show();
    }

    /**
     * 头像点击
     */
    void onImgAvatarClick() {
        //保存正在展示的fragment标志
       /* showFragment = Constants.TYPE_LOGIN;
        mSearchMenuItem.setVisible(false);
        SharedPreferenceUtil.setCurrentItem(Constants.TYPE_LOGIN);
        mDrawerLayout.closeDrawers();
        showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
        hideFragment = showFragment;*/
        Intent intent = new Intent(this, LoginActivity.class);
        //intent.putExtra(Constants.IT_GOLD_MANAGER, mBean);
        mContext.startActivity(intent);
    }



    @Override
    public void startDownloadService() {
        startService(new Intent(mContext, UpdateService.class));
    }

    @Override
    public void jump2PushSucc(MyPushBean bean) {
        Intent notifyIntent =
                new Intent(this, BookDetailActivity.class);
    //    notifyIntent.putExtra("objectId",bean.ojectId);
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

//bean.ojectId

        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化NotificationCompat.Builde并设置相关属性
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                //设置小图标
                .setSmallIcon(R.mipmap.chao3)
                //设置通知标题
                .setContentTitle("这是一篇有趣的文章")
                //设置通知内容
                .setContentText("我们希望这种情况永远不会发生。如果有降落伞").setContentIntent(notifyPendingIntent);
        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        //.setWhen(System.currentTimeMillis());
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        notifyManager.notify(1, builder.build());



    }

    /*public void showLoginOk() {
        Intent intent = getIntent();
        if(intent.getBooleanExtra("loginOk",false)){
            SnackbarUtil.showShort(getWindow().getDecorView(),"登陆成功");
            Bundle bundle = intent.getExtras();
            LoginBean bean = bundle.getParcelable("beanInfo");
            mLoginBean = bean;
            //userName = bean.name;
            tv_login_name.setText(bean.name);

        }
    }*/

    @Override
    public void showLogInInfo(LoginBean bean) {
      //  super.showLogInOutInfo(bean);
        mLoginBean = bean;
        if(mLoginBean != null){
            mDrawerLayout.closeDrawers();
            SnackbarUtil.showShort(getWindow().getDecorView(),"登陆成功");
            tv_login_name.setText(bean.name);
            btnLogout.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void showLogOutInfo() {
        mDrawerLayout.closeDrawers();
        SnackbarUtil.showShort(getWindow().getDecorView(),"注销成功");
        tv_login_name.setText("点击头像登录");
        btnLogout.setVisibility(View.GONE);
    }

    @Override
    public void showDefaultUserInfo() {
         //   tv_login_name.setText(loginBean.getName());
       //     btnLogout.setVisibility(View.VISIBLE);

            tv_login_name.setText("点击头像登录");
            btnLogout.setVisibility(View.GONE);
    }

    public void checkPermissions() {
        mPresenter.checkPermissions(new RxPermissions(this));
    }
}
