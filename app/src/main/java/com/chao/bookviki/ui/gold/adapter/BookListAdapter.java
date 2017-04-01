package com.chao.bookviki.ui.gold.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chao.bookviki.component.ImageLoader;
import com.chao.bookviki.model.bean.BookListBean;
import com.chao.bookviki.R;
import com.chao.bookviki.ui.gold.activity.BookDetailActivity;
import com.chao.bookviki.util.DateUtil;
import com.chao.bookviki.widget.SquareImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/28.
 */

public class BookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BookListBean> mList;
    private Context mContext;
    private LayoutInflater inflater;

    private String mType;
    private boolean mHotFlag = true;
    private OnHotCloseListener onHotCloseListener;
    private OnItemClickListener onItemClickListener;

    public enum ITEM_TYPE {
        ITEM_TITLE,     //标题
        ITEM_HOT,       //热门
        ITEM_CONTENT    //内容
    }

    public BookListAdapter(Context mContext, List<BookListBean> mList, String typeStr) {
        this.mList = mList;
        this.mContext = mContext;
        this.mType = typeStr;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        if (!mHotFlag) {
            return ITEM_TYPE.ITEM_CONTENT.ordinal();
        } else {
            if(position == 0) {
                return ITEM_TYPE.ITEM_TITLE.ordinal();
            } else if (position > 0 && position <= 3){
                return ITEM_TYPE.ITEM_HOT.ordinal();
            } else {
                return ITEM_TYPE.ITEM_CONTENT.ordinal();
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE.ITEM_TITLE.ordinal()) {
            return new TitleViewHolder(inflater.inflate(R.layout.item_gold_title, parent, false));
        } else if(viewType == ITEM_TYPE.ITEM_HOT.ordinal()) {
            return new HotViewHolder(inflater.inflate(R.layout.item_gold_hot, parent, false));
        }
        return new ContentViewHolder(inflater.inflate(R.layout.item_gold, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int contentPosition;
        contentPosition = position;
        BookListBean bean = mList.get(0);//标题
        if (position > 0) {
            bean = mList.get(position -1);
        }
        final BookListBean finalBean = bean;
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(id);
                }
            }
        });*/

        if (holder instanceof ContentViewHolder) {
            if (finalBean.getScreenshot() != null && finalBean.getScreenshot().getUrl() != null) {
                ImageLoader.load(mContext, finalBean.getScreenshot().getUrl(), ((ContentViewHolder) holder).ivImg);
            } else {
                ((ContentViewHolder) holder).ivImg.setImageResource(R.mipmap.ic_launcher);//设置默认图片
            }
            ((ContentViewHolder) holder).tvTitle.setText(bean.getTitle());
            ((ContentViewHolder) holder).tvInfo.setText(getItemInfoStr(finalBean.getCollectionCount(),
                    finalBean.getCommentsCount(),
                    finalBean.getUser().getUsername(),
                    DateUtil.formatDate2String(DateUtil.subStandardTime(finalBean.getCreatedAt()))));
           // holder.itemView.setOnClickListener(new MyOnClickListener(--position,bean));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null) {
                        ImageView iv = (ImageView) view.findViewById(R.id.iv_gold_item_img);
                        onItemClickListener.onItemClick(contentPosition,iv,finalBean);
                    }
                }
            });



        } else if (holder instanceof HotViewHolder) {
            if (finalBean.getScreenshot() != null && finalBean.getScreenshot().getUrl() != null) {
                ImageLoader.load(mContext, finalBean.getScreenshot().getUrl(), ((HotViewHolder) holder).ivImg);
            } else {
                ((HotViewHolder) holder).ivImg.setImageResource(R.mipmap.ic_launcher);
            }
            ((HotViewHolder) holder).tvTitle.setText(finalBean.getTitle());
            ((HotViewHolder) holder).tvLike.setText(String.valueOf(finalBean.getCollectionCount()));
            ((HotViewHolder) holder).tvAuthor.setText(String.valueOf(finalBean.getUser().getUsername()));
            ((HotViewHolder) holder).tvTime.setText(DateUtil.formatDate2String(DateUtil.subStandardTime(finalBean.getCreatedAt())));
           // holder.itemView.setOnClickListener(new MyOnClickListener(--position,bean));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null) {
                        ImageView iv = (ImageView) view.findViewById(R.id.iv_gold_item_img);
                        onItemClickListener.onItemClick(contentPosition,iv,finalBean);
                    }
                }
            });


        } else {
            ((TitleViewHolder) holder).tvTitle.setText(String.format("%s 热门", mType));
            ((TitleViewHolder) holder).btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mHotFlag = false;
                    for (int i = 0; i< 4 ;i++) {
                        mList.remove(0);
                    }
                    notifyItemRangeRemoved(0, 4);
                    if (onHotCloseListener != null) {
                        onHotCloseListener.onClose();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_gold_item_title)
        TextView tvTitle;
        @BindView(R.id.tv_gold_item_info)
        TextView tvInfo;
        @BindView(R.id.iv_gold_item_img)
        SquareImageView ivImg;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public static class HotViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_gold_item_title)
        TextView tvTitle;
        @BindView(R.id.tv_gold_item_like)
        TextView tvLike;
        @BindView(R.id.tv_gold_item_author)
        TextView tvAuthor;
        @BindView(R.id.tv_gold_item_time)
        TextView tvTime;
        @BindView(R.id.iv_gold_item_img)
        SquareImageView ivImg;

        public HotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_gold_hot_title)
        TextView tvTitle;
        @BindView(R.id.btn_gold_hot_close)
        AppCompatButton btnClose;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private class MyOnClickListener implements View.OnClickListener {

        private int position;
        private BookListBean bean;

        public MyOnClickListener(int position, BookListBean bean) {
            this.position = position;
            if (position < 0) {
                this.position = 0;
            }
            this.bean= bean;
        }

        @Override
        public void onClick(View view) {
            //  String imgUrl = null;
            //  if (mList.get(position).getScreenshot() != null && mList.get(position).getScreenshot().getUrl() != null)
            // imgUrl = mList.get(position).getScreenshot().getUrl();
            /*TechDetailActivity.launch(new TechDetailActivity.Builder()
                    .setContext(mContext)
                    .setId(mList.get(position).getObjectId())
                    .setTitle(mList.get(position).getTitle())
                    .setUrl(mList.get(position).getUrl())
                    .setImgUrl(imgUrl)
                    .setType(Constants.TYPE_GOLD));*/
           /* Intent intent = new Intent();
            intent.setClass(mContext, BookDetailActivity.class);
            intent.putExtra("id",1);
           // ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, shareView, "shareView");
           //,options.toBundle()
            mContext.startActivity(intent);*/
           /* Intent intent = new Intent();
            intent.setClass(mContext, BookDetailActivity.class);
            //详细内容的ID
            intent.putExtra("id",bean.getObjectId());
            //TODO 传进该主题内容 intent
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, shareView, "shareView");
            mContext.startActivity(intent,options.toBundle());*/

        }
    }

    private String getItemInfoStr(int likeNum, int cmtNum, String author, String time) {
        StringBuilder sb = new StringBuilder(String.valueOf(likeNum));
        sb.append("人收藏 · ");
        sb.append(cmtNum);
        sb.append("条评论 · ");
        sb.append(author);
        sb.append(" · ");
        sb.append(time);
        return sb.toString();
    }

    public void updateData(List<BookListBean> list) {
        mList = list;
    }

    public void setHotFlag(boolean hotFlag) {
        this.mHotFlag = hotFlag;
    }

    public boolean getHotFlag() {
        return mHotFlag;
    }

    public interface OnHotCloseListener {
        void onClose();
    }

    public void setOnHotCloseListener(OnHotCloseListener onHotCloseListener) {
        this.onHotCloseListener = onHotCloseListener;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int id,View view,BookListBean bean);
    }

}
