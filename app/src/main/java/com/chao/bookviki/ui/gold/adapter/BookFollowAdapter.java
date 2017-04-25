package com.chao.bookviki.ui.gold.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chao.bookviki.component.RxBus;
import com.chao.bookviki.model.bean.BookManagerItemBean;
import com.chao.bookviki.model.bean.FollowBean;
import com.chao.bookviki.ui.gold.fragment.BookMainFragment;
import com.chao.bookviki.R;
import com.chao.bookviki.util.LogUtil;
import com.chao.bookviki.util.SnackbarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 *
 */

public class BookFollowAdapter extends RecyclerView.Adapter<BookFollowAdapter.ViewHolder> {

    private RealmList<BookManagerItemBean> mList;
    private LayoutInflater inflater;

    public BookFollowAdapter(Context mContext, RealmList<BookManagerItemBean> mList) {
        inflater = LayoutInflater.from(mContext);
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_book_manager, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvType.setText(BookMainFragment.typeStr[mList.get(position).getIndex()]);
        holder.scSwitch.setChecked(mList.get(position).getIsSelect());
        holder.scSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //改变触发事件
                mList.get(holder.getAdapterPosition()).setSelect(b);
                String objectId = mList.get(holder.getAdapterPosition()).getTypeId();
                FollowBean bean = new FollowBean(objectId,b);
                RxBus.getDefault().post(bean);
            }
        });
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i("点击");
            }
        });
    }

    @Override
    public int getItemCount() {
        return BookMainFragment.typeStr.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_gold_manager_type)
        TextView tvType;
        @BindView(R.id.sc_gold_manager_switch)
        SwitchCompat scSwitch;
        @BindView(R.id.image_button)
        AppCompatImageButton imageButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
