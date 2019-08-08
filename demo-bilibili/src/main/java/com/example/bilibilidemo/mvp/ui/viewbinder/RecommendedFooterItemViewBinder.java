package com.example.bilibilidemo.mvp.ui.viewbinder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bilibilidemo.R;
import com.example.bilibilidemo.mvp.model.entity.RecommendInfo;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * author: 小川
 * Date: 2019/8/5
 * Description:
 */
public class RecommendedFooterItemViewBinder extends ItemViewBinder<RecommendInfo.ResultBean, RecommendedFooterItemViewBinder.Holder> {

    private static final String TYPE_RECOMMENDED = "recommend";
    private static final String TYPE_BANGUMI = "bangumi_2";
    private static final String TYPE_ACTIVITY = "activity";

    @NonNull
    @Override
    protected Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View inflate = inflater.inflate(R.layout.item_recommended_footer, parent, false);
        return new Holder(inflate);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, @NonNull RecommendInfo.ResultBean item) {
        Random mRandom = new Random();
        Context mContext = holder.mBangumiIndexBtn.getContext();
        holder.mDynamic.setText(String.valueOf(mRandom.nextInt(200)) + "条新动态,点这里刷新");
        holder.mRefreshBtn.setOnClickListener(v -> holder.mRefreshBtn
                .animate()
                .rotation(360)
                .setInterpolator(new LinearInterpolator())
                .setDuration(1000).start());
        holder.mRecommendRefresh.setOnClickListener(v -> holder.mRecommendRefresh
                .animate()
                .rotation(360)
                .setInterpolator(new LinearInterpolator())
                .setDuration(1000).start());
        // TODO: 2019/8/5 item点击监听 等待处理
        //        holder.mBangumiIndexBtn.setOnClickListener(v -> mContext.startActivity(
//                new Intent(mContext, BangumiIndexActivity.class)));
//        holder.mBangumiTimelineBtn.setOnClickListener(v -> mContext.startActivity(
//                new Intent(mContext, BangumiScheduleActivity.class)));
        switch (item.getType()) {
            case TYPE_RECOMMENDED:
                holder.mMoreBtn.setVisibility(View.GONE);
                holder.mRefreshLayout.setVisibility(View.GONE);
                holder.mBangumiLayout.setVisibility(View.GONE);
                holder.mRecommendRefreshLayout.setVisibility(View.VISIBLE);
                break;
            case TYPE_BANGUMI:
                holder.mMoreBtn.setVisibility(View.GONE);
                holder.mRefreshLayout.setVisibility(View.GONE);
                holder.mRecommendRefreshLayout.setVisibility(View.GONE);
                holder.mBangumiLayout.setVisibility(View.VISIBLE);
                break;
            case TYPE_ACTIVITY:
                holder.mRecommendRefreshLayout.setVisibility(View.GONE);
                holder.mBangumiLayout.setVisibility(View.GONE);
                holder.mMoreBtn.setVisibility(View.GONE);
                holder.mRefreshLayout.setVisibility(View.GONE);
                break;
            default:
                holder.mRecommendRefreshLayout.setVisibility(View.GONE);
                holder.mBangumiLayout.setVisibility(View.GONE);
                holder.mMoreBtn.setVisibility(View.VISIBLE);
                holder.mRefreshLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_btn_more)
        Button mMoreBtn;
        @BindView(R.id.item_dynamic)
        TextView mDynamic;
        @BindView(R.id.item_btn_refresh)
        ImageView mRefreshBtn;
        @BindView(R.id.item_refresh_layout)
        LinearLayout mRefreshLayout;
        @BindView(R.id.item_recommend_refresh_layout)
        LinearLayout mRecommendRefreshLayout;
        @BindView(R.id.item_recommend_refresh)
        ImageView mRecommendRefresh;
        @BindView(R.id.item_bangumi_layout)
        LinearLayout mBangumiLayout;
        @BindView(R.id.item_btn_bangumi_index)
        ImageView mBangumiIndexBtn;
        @BindView(R.id.item_btn_bangumi_timeline)
        ImageView mBangumiTimelineBtn;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
