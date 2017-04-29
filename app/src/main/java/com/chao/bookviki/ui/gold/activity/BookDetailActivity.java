package com.chao.bookviki.ui.gold.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.transition.Transition;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chao.bookviki.R;
import com.chao.bookviki.base.BaseActivity;
import com.chao.bookviki.component.ImageLoader;
import com.chao.bookviki.model.bean.BookDetailBean;
import com.chao.bookviki.model.bean.BookDetailExtraBean;
import com.chao.bookviki.model.bean.BookListBean;
import com.chao.bookviki.model.bean.RepliesListBean;
import com.chao.bookviki.presenter.BookDetailPresenter;
import com.chao.bookviki.presenter.contract.BookDetailContract;
import com.chao.bookviki.ui.userinfo.activity.LoginActivity;
import com.chao.bookviki.ui.gold.adapter.RepliesAdapter;
import com.chao.bookviki.util.ShareUtil;
import com.chao.bookviki.util.SnackbarUtil;
import com.chao.bookviki.widget.CommonItemDecoration;
import com.chao.bookviki.widget.ProgressImageView;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by codeest on 16/8/13.
 */

public class BookDetailActivity extends BaseActivity<BookDetailPresenter> implements BookDetailContract.View {

    @BindView(R.id.detail_bar_image)
    ImageView detailBarImage;
    @BindView(R.id.detail_bar_copyright)
    TextView detailBarCopyright;
    @BindView(R.id.view_toolbar)
    Toolbar viewToolbar;
    @BindView(R.id.clp_toolbar)
    CollapsingToolbarLayout clpToolbar;
    /*@BindView(R.id.wv_detail_content)
    WebView wvDetailContent;*/
    @BindView(R.id.iv_progress)
    ProgressImageView ivProgress;
    @BindView(R.id.nsv_scroller)
    NestedScrollView nsvScroller;
    @BindView(R.id.tv_detail_bottom_like)
    TextView tvDetailBottomLike;
    @BindView(R.id.tv_detail_bottom_comment)
    TextView tvDetailBottomComment;
    @BindView(R.id.tv_detail_bottom_share)
    TextView tvDetailBottomShare;
    @BindView(R.id.ll_detail_bottom)
    FrameLayout llDetailBottom;
    @BindView(R.id.fab_like)
    FloatingActionButton fabLike;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    int id = 0;
    int allNum = 0;
    int shortNum = 0;
    int longNum = 0;
    String shareUrl;
    String imgUrl;
    boolean isBottomShow = true;
    boolean isImageShow = false;
    boolean isTransitionEnd = false;
    boolean isNotTransition = false;
    private RepliesAdapter mAdapter;
    private BookListBean mTopBean;
 //   private String topicId;
    private MaterialDialog.Builder mInputDialog;
    private String objectId;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(viewToolbar,"");
        Intent intent = getIntent();
       /* mTopBean = getIntent().getParcelableExtra("beanInfo");
        id = Integer.parseInt(mTopBean.getObjectId());
        mPresenter.queryLikeData(id);*/

        Bundle bundle = intent.getExtras();
     //   bundle.setClassLoader(BookListBean.class.getClassLoader());
try {
    //手动触发不是从adpter进来的null
    mTopBean = bundle.getParcelable("beanInfo");
    objectId = mTopBean.getObjectId();
}catch (NullPointerException e) {
//此处为了推送进来的入口
   // mTopBean = initBean();
     objectId = intent.getStringExtra("objectId");
    mPresenter.getSingleBookList(objectId);

}
//保存到presenter 供保存Like
mPresenter.save2DetailPrestener(mTopBean);



       // topicId = mTopBean.getObjectId();
        //isNotTransition = intent.getBooleanExtra("isNotTransition",false);

        //mPresenter.getDetailData(id);
       // mPresenter.getExtraData(id);

       // topicId = getIntent().getExtras().getString(Constants.IT_VTEX_TOPIC_ID);

        //mTopBean = initBean();

        mAdapter = new RepliesAdapter(mContext, new ArrayList<RepliesListBean>(), mTopBean);
        CommonItemDecoration mDecoration = new CommonItemDecoration(2, CommonItemDecoration.UNIT_PX);
        rvContent.addItemDecoration(mDecoration);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);



        ivProgress.start();

       // imgUrl = "https://pic4.zhimg.com/v2-0983ac630d50d798ba099a0cce8c0ca3.jpg";
       // imgUrl = intent.getExtras().getString("url");
        //获取该主题的评论
        mPresenter.getContent(objectId);
        /*if (mTopBean == null) {
            mPresenter.getTopInfo(topicId);
        }*/

        nsvScroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY - oldScrollY > 0 && isBottomShow) {  //下移隐藏
                    isBottomShow = false;
                    llDetailBottom.animate().translationY(llDetailBottom.getHeight());
                } else if(scrollY - oldScrollY < 0 && !isBottomShow){    //上移出现
                    isBottomShow = true;
                    llDetailBottom.animate().translationY(0);
                }
            }
        });
        (getWindow().getSharedElementEnterTransition()).addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }
            @Override
            public void onTransitionEnd(Transition transition) {
                /**
                 * 测试发现部分手机(如红米note2)上加载图片会变形,没有达到centerCrop效果
                 * 查阅资料发现Glide配合SharedElementTransition是有坑的,需要在Transition动画结束后再加载图片
                 * https://github.com/TWiStErRob/glide-support/blob/master/src/glide3/java/com/bumptech/glide/supportapp/github/_847_shared_transition/DetailFragment.java
                 */
                isTransitionEnd = true;
                if (imgUrl != null) {
                    isImageShow = true;
                    ImageLoader.load(mContext, imgUrl, detailBarImage);
                }
            }
            @Override
            public void onTransitionCancel(Transition transition) {
            }
            @Override
            public void onTransitionPause(Transition transition) {
            }
            @Override
            public void onTransitionResume(Transition transition) {
            }
        });

        initDialog();
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

    private BookListBean initBean() {
        BookListBean bean = new BookListBean();
        bean.setCollectionCount(2);
        bean.setCommentsCount(1);
        bean.setCreatedAt("432434343");
        bean.setObjectId("123445");
        BookListBean.GoldListScreenshotBean xxBean = new BookListBean.GoldListScreenshotBean();
        xxBean.setUrl("34343434");
        bean.setScreenshot(xxBean);
        bean.setTitle("xxx");
        bean.setUrl("34343434");
        BookListBean.GoldListUserBean xBean  = new BookListBean.GoldListUserBean();
        xBean.setUsername("43434434");
        bean.setUser(xBean);
        return  bean;
    }

    @Override
    public void showContent(BookDetailBean bookDetailBean) {
        ivProgress.stop();
        imgUrl = bookDetailBean.getImage();
        shareUrl = bookDetailBean.getShare_url();
        if (isNotTransition) {
            ImageLoader.load(mContext, bookDetailBean.getImage(), detailBarImage);
        } else {
            if (!isImageShow && isTransitionEnd) {
                ImageLoader.load(mContext, bookDetailBean.getImage(), detailBarImage);
            }
        }
        clpToolbar.setTitle(bookDetailBean.getTitle());
        detailBarCopyright.setText(bookDetailBean.getImage_source());
        //String htmlData = HtmlUtil.createHtmlData(zhihuDetailBean.getBody(),zhihuDetailBean.getCss(),zhihuDetailBean.getJs());
        //wvDetailContent.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public void showExtraInfo(BookDetailExtraBean bookDetailExtraBean) {
        ivProgress.stop();
        tvDetailBottomLike.setText(String.format("%d个赞",bookDetailExtraBean.getPopularity()));
        tvDetailBottomComment.setText(String.format("%d条评论",bookDetailExtraBean.getComments()));
        allNum = bookDetailExtraBean.getComments();
        shortNum = bookDetailExtraBean.getShort_comments();
        longNum = bookDetailExtraBean.getLong_comments();
    }

    @Override
    public void setLikeButtonState(boolean isLiked) {
        fabLike.setSelected(isLiked);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
       /* if ((keyCode == KeyEvent.KEYCODE_BACK) && wvDetailContent.canGoBack()) {
            wvDetailContent.goBack();
            return true;
        }*/
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showError(String msg) {
        ivProgress.stop();
        SnackbarUtil.showShort(getWindow().getDecorView(),msg);
    }

    @OnClick(R.id.tv_detail_bottom_comment)
    void gotoComment() {
        /*Intent intent = getIntent();
        intent.setClass(this,CommentActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("allNum",allNum);
        intent.putExtra("shortNum",shortNum);
        intent.putExtra("longNum",longNum);
        startActivity(intent);*/
        mInputDialog.title("评论");
        mInputDialog.input("","",commentCallback);
        mInputDialog.show();



        }

    MaterialDialog.InputCallback commentCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
            if (!TextUtils.isEmpty(input)) {
                mPresenter.replay(mTopBean.getObjectId(),input.toString());
                //  mUserEntity.setCity(input.toString());
                //  mPresenter.saveUserInfoById(mUserEntity.getId(), mUserEntity);
            }
        }
    };





    @OnClick(R.id.tv_detail_bottom_share)
    void shareUrl() {
        ShareUtil.shareText(mContext,shareUrl,"分享一篇文章");
    }

    @OnClick(R.id.fab_like)
    void likeArticle() {
        if (fabLike.isSelected()) {
            fabLike.setSelected(false);
            mPresenter.deleteLikeData();
        } else {
            fabLike.setSelected(true);
            mPresenter.insertLikeData();
        }
    }

    //展示评论
    @Override
    public void showReplay(List<RepliesListBean> mList) {

        mAdapter.setContentData(mList);
    }

    //展示主题信息
    @Override
    public void showTopInfo(BookListBean mTopInfo) {
        mTopBean = mTopInfo;
        mAdapter.setTopData(mTopInfo);
    }

    //TODO 换种方式 adapter replayBean
    @Override
    public void showReplaySucc() {
        SnackbarUtil.showShort(getWindow().getDecorView(),"发表评论成功");
        //mPresenter.getContent(mTopBean.getObjectId());
    }

    @Override
    public void jump2LoginPage() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }


    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            finishAfterTransition();
        }
    }
}
