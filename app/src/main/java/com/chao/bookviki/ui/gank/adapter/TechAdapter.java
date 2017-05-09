package com.chao.bookviki.ui.gank.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chao.bookviki.R;
import com.chao.bookviki.model.bean.JingXuanNewsBean;
import com.chao.bookviki.util.DateUtil;
import com.chao.bookviki.util.JingXuanDiverdedUtil;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by codeest on 16/8/20.
 */

public class TechAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<JingXuanNewsBean> mList;
    private OnItemClickListener onItemClickListener;
    /**
     * 1	war	军事
     2	sport	体育
     3	tech	科技
     4	edu	教育
     5	ent	娱乐
     6	money	财经
     7	gupiao	股票
     8	travel	旅游
     9	lady	女人
     */
    private Map<String,String> map;
    private Map<String,Integer> iconMap;

    private String tech;

    public TechAdapter(Context mContext, List<JingXuanNewsBean> mList, String tech) {
        inflater = LayoutInflater.from(mContext);
        this.mList = mList;
        this.tech = "Android";
        this.map = JingXuanDiverdedUtil.map;
        this.iconMap = JingXuanDiverdedUtil.iconMap;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_tech, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).ivIcon.setImageResource(iconMap.get(mList.get(position).getChannelname()));
        ((ViewHolder)holder).tvContent.setText(mList.get(position).getTitle());
        ((ViewHolder)holder).tvAuthor.setText(map.get(mList.get(position).getChannelname()));
        ((ViewHolder)holder).tvTime.setText(DateUtil.formatDateTime(mList.get(position).getTime(), true));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null) {
                    CardView cv = (CardView) view.findViewById(R.id.cv_tech_content);
                    onItemClickListener.onItemClick(holder.getAdapterPosition(),cv);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_tech_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_tech_title)
        TextView tvContent;
        @BindView(R.id.tv_tech_author)
        TextView tvAuthor;
        @BindView(R.id.tv_tech_time)
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position,View view);
    }
}
