package com.chao.bookviki.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chao.bookviki.R;
import com.chao.bookviki.app.Constants;
import com.chao.bookviki.model.bean.RealmLikeBean;
import com.chao.bookviki.ui.best.activity.TechDetailActivity;
import com.chao.bookviki.ui.daily.activity.BookDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by codeest on 16/8/23.
 */

public class LikeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<RealmLikeBean> mList;
    private LayoutInflater inflater;

    private static final int TYPE_ARTICLE = 0;
    private static final int TYPE_GIRL = 1;

    public LikeAdapter(Context mContext,List<RealmLikeBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        if(mList.get(position).getType() == Constants.TYPE_GIRL) {
            return TYPE_GIRL;
        } else {
            return TYPE_ARTICLE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ArticleViewHolder(inflater.inflate(R.layout.item_like_article, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ArticleViewHolder) {
            ((ArticleViewHolder) holder).title.setText(mList.get(position).getTitle());
            switch (mList.get(position).getType()) {
                case Constants.TYPE_BOOK:
                  //  if (mList.get(position).getImage() != null) {
                  //      ImageLoader.load(mContext, mList.get(position).getImage(), ((ArticleViewHolder) holder).image);
                 //   } else {
                        ((ArticleViewHolder) holder).image.setImageResource(R.mipmap.richang);
                //    }
                    ((ArticleViewHolder) holder).from.setText("日常");
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            gotoDailyDetail(Integer.valueOf(mList.get(holder.getAdapterPosition()).getId()));
                        }
                    });
                    break;
                case Constants.JING_XUAN_CONSTANT:
                    ((ArticleViewHolder) holder).image.setImageResource(R.mipmap.yuedu);
                    ((ArticleViewHolder) holder).from.setText("精选阅读");
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            gotoTechDetail(mList.get(holder.getAdapterPosition()).getUrl(), null, mList.get(holder.getAdapterPosition()).getTitle()
                                    ,mList.get(holder.getAdapterPosition()).getId(), Constants.JING_XUAN_CONSTANT);
                        }
                    });
                    break;
                case Constants.YU_LU_CONSTATNT:
                    ((ArticleViewHolder) holder).image.setImageResource(R.mipmap.yingwenyuluicon);
                    ((ArticleViewHolder) holder).from.setText("英文语录");
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            gotoTechDetail(mList.get(holder.getAdapterPosition()).getUrl(), null, mList.get(holder.getAdapterPosition()).getTitle()
                                    ,mList.get(holder.getAdapterPosition()).getId(), Constants.YU_LU_CONSTATNT);
                        }
                    });
                    break;

            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_article_image)
        ImageView image;
        @BindView(R.id.tv_article_title)
        TextView title;
        @BindView(R.id.tv_article_from)
        TextView from;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

    public static class GirlViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_girl_image)
        ImageView image;

        public GirlViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void gotoDailyDetail(int id) {
        Intent intent = new Intent();
        intent.setClass(mContext, BookDetailActivity.class);
        intent.putExtra("objectId",String.valueOf(id));
        intent.putExtra("isNotTransition",true);
        mContext.startActivity(intent);
    }

    public void gotoTechDetail(String url,String imgUrl, String title,String id,int type) {
        TechDetailActivity.launch(new TechDetailActivity.Builder()
                .setContext(mContext)
                .setUrl(url)
                .setImgUrl(imgUrl)
                .setId(id)
                .setTitle(title)
                .setType(type));
    }




}
