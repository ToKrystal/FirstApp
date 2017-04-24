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
import com.chao.bookviki.presenter.VtexPresenter;
import com.chao.bookviki.util.DateUtil;
import com.chao.bookviki.widget.SquareImageView;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by codeest on 16/12/19.
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopViewHolder) {
            TopViewHolder topHolder = ((TopViewHolder) holder);
            if (mTopBean == null) {//楼主
                return;
            }
            ImageLoader.load(mContext, VtexPresenter.parseImg(mTopBean.getUrl()), topHolder.ivRepliesTopFace);
            topHolder.tvRepliesTopContent.setHtml("没有降落伞跳下飞机……谁都知道这糟透了，因为很有可能就一命呜呼了！但是，如果你必须从飞机上跳下来，而且这架飞机离地面几千米，在自由下落中幸存几率有多少？ 孤独一掷\n" +
                    "\n" +
                    "首先，我们希望这种情况永远不会发生。如果有降落伞，可能还有一线生机。但如果没有降落伞，而跳下飞机是唯一选择，那该做的第一件事就是在地面上寻找大海或河流等有水的地方，然后尝试瞄准目标。假设一切做得完美，那么生存几率是多少？\n" +
                    "\n" +
                    "缓冲物体\n" +
                    "\n" +
                    "在跳下之前，首先找到一个大物体，缓冲一下即将来临的自由落体，最好能将自己包裹成一团。缓冲物体能吸收掉一部分人体掉落时的动能，使生还成为了可能。 ", new HtmlHttpImageGetter(topHolder.tvRepliesTopContent));
            topHolder.tvRepliesTopName.setText(mTopBean.getUser().getUsername());
            topHolder.tvRepliesTopTitle.setText(mTopBean.getTitle());
            topHolder.tvRepliesTopNum.setText(String.format("%s,   共%s条回复", DateUtil.formatTime2String(Long.valueOf("1423253523525")), mTopBean.getCommentsCount()));
        } else {//回复
            ViewHolder contentHolder = ((ViewHolder) holder);
            RepliesListBean bean = mList.get(position - 1);
            if (bean == null)
                return;
            ImageLoader.load(mContext, VtexPresenter.parseImg(bean.getMember().getavatar_normal()), contentHolder.ivRepliesFace);//评论人的头像
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
