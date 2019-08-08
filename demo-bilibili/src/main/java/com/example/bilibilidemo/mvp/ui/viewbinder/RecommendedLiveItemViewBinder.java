package com.example.bilibilidemo.mvp.ui.viewbinder;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bilibilidemo.R;
import com.example.bilibilidemo.mvp.model.entity.RecommendInfo;
import com.example.bilibilidemo.mvp.ui.activity.LivePlayerActivity;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * author: 小川
 * Date: 2019/8/4
 * Description:
 */
public class RecommendedLiveItemViewBinder extends ItemViewBinder<RecommendInfo.ResultBean.BodyBean, RecommendedLiveItemViewBinder.Holder> {

    private static final String TYPE_LIVE = "live";
    private static final String GOTO_BANGUMI = "bangumi_list";

    @NonNull
    @Override
    protected Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View inflate = inflater.inflate(R.layout.item_recommended_live, parent, false);
        return new Holder(inflate);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, @NonNull RecommendInfo.ResultBean.BodyBean item) {
        Context mContext = holder.mCardView.getContext();
        Glide.with(mContext)
                .load(Uri.parse(item.getCover()))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(holder.mVideoImg);

        holder.mVideoTitle.setText(item.getTitle());
        // TODO: 2019/8/4 等待处理
                holder.mCardView.setOnClickListener(v -> {
            String gotoX = item.getGotoX();
                    switch (gotoX) {
                case TYPE_LIVE:
                    LivePlayerActivity.launch((Activity) mContext,
                            Integer.valueOf(item.getParam()), item.getTitle(),
                            item.getOnline(), item.getUpFace(), item.getUp(), 0);
                    break;
                case GOTO_BANGUMI:
                    break;
                default:
//                    VideoDetailsActivity.launch((Activity) mContext,
//                            Integer.parseInt(bodyBean.getParam()), bodyBean.getCover());
                    break;
            }
        });

        if (!TextUtils.isEmpty(item.getUpFace())) {
            //直播item
            holder.mLiveLayout.setVisibility(View.VISIBLE);
            holder.mVideoLayout.setVisibility(View.GONE);
            holder.mBangumiLayout.setVisibility(View.GONE);
            holder.mLiveUp.setText(item.getUp());
            holder.mLiveOnline.setText(String.valueOf(item.getOnline()));
        } else if (!TextUtils.isEmpty(item.getDesc1())) {
            // 番剧item
            holder.mLiveLayout.setVisibility(View.GONE);
            holder.mVideoLayout.setVisibility(View.GONE);
            holder.mBangumiLayout.setVisibility(View.VISIBLE);
            holder.mBangumiUpdate.setText(item.getDesc1());
        } else if (item.getStyle().equals("gl_act")) {
            holder.mLiveLayout.setVisibility(View.GONE);
            holder.mVideoLayout.setVisibility(View.GONE);
            holder.mBangumiLayout.setVisibility(View.GONE);
        } else {
            holder.mLiveLayout.setVisibility(View.GONE);
            holder.mBangumiLayout.setVisibility(View.GONE);
            holder.mVideoLayout.setVisibility(View.VISIBLE);
            holder.mVideoPlayNum.setText(item.getPlay());
            holder.mVideoReviewCount.setText(item.getDanmaku());
        }
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView mCardView;
        @BindView(R.id.video_preview)
        ImageView mVideoImg;
        @BindView(R.id.video_title)
        TextView mVideoTitle;
        @BindView(R.id.video_play_num)
        TextView mVideoPlayNum;
        @BindView(R.id.video_review_count)
        TextView mVideoReviewCount;
        @BindView(R.id.layout_live)
        RelativeLayout mLiveLayout;
        @BindView(R.id.layout_video)
        LinearLayout mVideoLayout;
        @BindView(R.id.item_live_up)
        TextView mLiveUp;
        @BindView(R.id.item_live_online)
        TextView mLiveOnline;
        @BindView(R.id.layout_bangumi)
        RelativeLayout mBangumiLayout;
        @BindView(R.id.item_bangumi_update)
        TextView mBangumiUpdate;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
