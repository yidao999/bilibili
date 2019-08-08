package com.example.bilibilidemo.mvp.ui.viewbinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bilibilidemo.R;
import com.example.bilibilidemo.mvp.model.Constant;
import com.example.bilibilidemo.mvp.model.entity.LiveAppIndexInfo;
import com.example.bilibilidemo.mvp.ui.activity.LivePlayerActivity;
import com.example.bilibilidemo.mvp.ui.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * author: 小川
 * Date: 2019/7/29
 * Description:
 */
public class LiveLiveItemViewBinder extends ItemViewBinder<LiveAppIndexInfo.DataBean.PartitionsBean.LivesBeanX, LiveLiveItemViewBinder.Holder> {
    @NonNull
    @Override
    protected Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View inflate = inflater.inflate(R.layout.item_live_live, parent, false);
        return new Holder(inflate);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, @NonNull LiveAppIndexInfo.DataBean.PartitionsBean.LivesBeanX item) {

        Context context = holder.itemLiveCount.getContext();

        Glide.with(context)
                .load(item.getCover().getSrc())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(holder.itemLiveCover);

        Glide.with(context)
                .load(item.getCover().getSrc())
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.ico_user_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemLiveUserCover);

        holder.itemLiveTitle.setText(item.getTitle());
        holder.itemLiveUser.setText(item.getOwner().getName());
        holder.itemLiveCount.setText(String.valueOf(item.getOnline()));
        holder.itemLiveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(context, LivePlayerActivity.class);
                mIntent.putExtra(Constant.EXTRA_CID, item.getRoom_id());
                mIntent.putExtra(Constant.EXTRA_TITLE, item.getTitle());
                mIntent.putExtra(Constant.EXTRA_ONLINE, item.getOnline());
                mIntent.putExtra(Constant.EXTRA_FACE, item.getOwner().getFace());
                mIntent.putExtra(Constant.EXTRA_NAME, item.getOwner().getName());
                mIntent.putExtra(Constant.EXTRA_MID, item.getOwner().getMid());
                mIntent.putExtra(Constant.PLAY_URL, item.getPlayurl());
                context.startActivity(mIntent);
            }
        });
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_live_cover)
        ImageView itemLiveCover;
        @BindView(R.id.item_live_user)
        TextView itemLiveUser;
        @BindView(R.id.item_live_title)
        TextView itemLiveTitle;
        @BindView(R.id.item_live_user_cover)
        CircleImageView itemLiveUserCover;
        @BindView(R.id.item_live_count)
        TextView itemLiveCount;
        @BindView(R.id.item_live_layout)
        CardView itemLiveLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
