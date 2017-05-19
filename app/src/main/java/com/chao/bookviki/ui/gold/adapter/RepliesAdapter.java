package com.chao.bookviki.ui.gold.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chao.bookviki.R;
import com.chao.bookviki.component.ImageLoader;
import com.chao.bookviki.model.bean.BookListBean;
import com.chao.bookviki.model.bean.RepliesListBean;
import com.chao.bookviki.util.DateUtil;
import com.chao.bookviki.widget.SquareImageView;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 详情页面adapter
 */

public class RepliesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<RepliesListBean> mList;
    private BookListBean mTopBean;

    public RepliesAdapter(Context context, List<RepliesListBean> mList, BookListBean mTopBean) {
        this.mContext = context;
        this.mList = mList;
        this.mTopBean = mTopBean;
        inflater = LayoutInflater.from(mContext);
    }

    public enum ITEM_TYPE {
        ITEM_TOP,           //作者发言
        ITEM_CONTENT,       //回复
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE.ITEM_TOP.ordinal();
        }
        return ITEM_TYPE.ITEM_CONTENT.ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TOP.ordinal()) {
            return new TopViewHolder(inflater.inflate(R.layout.item_replies_top, parent, false));
        }
        return new ViewHolder(inflater.inflate(R.layout.item_replies, parent, false));
    }

    public static String parseImg(String str) {
        return "http:" + str;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopViewHolder) {
            TopViewHolder topHolder = ((TopViewHolder) holder);
            if (mTopBean == null) {//楼主
                return;
            }
            ImageLoader.load(mContext, parseImg(mTopBean.getUrl()), topHolder.ivRepliesTopFace);
            topHolder.tvRepliesTopContent.setHtml(mTopBean.getContent(), new HtmlHttpImageGetter(topHolder.tvRepliesTopContent));
            topHolder.tvRepliesTopName.setText(mTopBean.getUser().getUsername());
            topHolder.tvRepliesTopTitle.setText(mTopBean.getTitle());
            topHolder.tvRepliesTopNum.setText(String.format("%s,   共%s条回复", DateUtil.formatTime2String(Long.valueOf("1423253523525")), mTopBean.getCommentsCount()));
        } else {//回复
            ViewHolder contentHolder = ((ViewHolder) holder);
            RepliesListBean bean = mList.get(position - 1);
            if (bean == null)
                return;
            ImageLoader.load(mContext, bean.getMember().getavatar_normal(), contentHolder.ivRepliesFace);//评论人的头像
            contentHolder.tvRepliesName.setText(bean.getMember().getUsername());//评论人的名字
            contentHolder.tvRepliesTips.setText(String.format("%d楼 %s", position, DateUtil.formatTime2String(bean.getCreated())));//楼和时间
            contentHolder.tvRepliesContent.setHtml(bean.getContent_rendered(), new HtmlHttpImageGetter(contentHolder.tvRepliesContent));//评论内容
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    public static class TopViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_replies_top_title)
        TextView tvRepliesTopTitle;
        @BindView(R.id.iv_replies_top_face)
        SquareImageView ivRepliesTopFace;
        @BindView(R.id.tv_replies_top_name)
        TextView tvRepliesTopName;
        @BindView(R.id.tv_replies_top_num)
        TextView tvRepliesTopNum;
        @BindView(R.id.tv_replies_top_content)
        HtmlTextView tvRepliesTopContent;

        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_replies_face)
        SquareImageView ivRepliesFace;
        @BindView(R.id.tv_replies_name)
        TextView tvRepliesName;
        @BindView(R.id.tv_replies_tips)
        TextView tvRepliesTips;
        @BindView(R.id.tv_replies_content)
        HtmlTextView tvRepliesContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setTopData(BookListBean mTopBean) {
        this.mTopBean = mTopBean;
        notifyItemChanged(0);
    }

    public void setContentData(List<RepliesListBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
}
