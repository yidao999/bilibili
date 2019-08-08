package com.example.bilibilidemo.mvp.ui.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bilibilidemo.R;
import com.example.bilibilidemo.app.utils.NumberUtil;
import com.example.bilibilidemo.mvp.model.entity.SearchArchiveInfo;
import com.jess.arms.base.BaseHolder;

import butterknife.BindView;

/**
 * author: 小川
 * Date: 2019/8/3
 * Description:
 */
public class ArchiveResultsHolder extends BaseHolder<SearchArchiveInfo.DataBean.ItemsBean.ArchiveBean> {

    @BindView(R.id.item_img)
    ImageView mVideoPic;
    @BindView(R.id.item_title)
    TextView mVideoTitle;
    @BindView(R.id.item_play)
    TextView mVideoPlayNum;
    @BindView(R.id.item_review)
    TextView mVideoReviewNum;
    @BindView(R.id.item_user_name)
    TextView mUserName;
    @BindView(R.id.item_duration)
    TextView mDuration;

    public ArchiveResultsHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(@NonNull SearchArchiveInfo.DataBean.ItemsBean.ArchiveBean data, int position) {

        Glide.with(mVideoPic.getContext())
                .load(data.getCover())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(mVideoPic);

        mVideoTitle.setText(data.getTitle());
        mVideoPlayNum.setText(NumberUtil.converString(data.getPlay()));
        mVideoReviewNum.setText(NumberUtil.converString(data.getDanmaku()));
        mUserName.setText(data.getAuthor());
        if (data.getDuration() != null) {
            mDuration.setText(data.getDuration());
        } else {
            mDuration.setText("--:--");
        }
    }
}
