package com.example.bilibilidemo.mvp.ui.holder;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bilibilidemo.R;
import com.example.bilibilidemo.mvp.model.entity.GameCenterInfo;
import com.example.bilibilidemo.mvp.ui.activity.BrowserActivity;
import com.jess.arms.base.BaseHolder;

import butterknife.BindView;

/**
 * author: 小川
 * Date: 2019/8/2
 * Description:
 */
public class GameCenterHolder extends BaseHolder<GameCenterInfo.ItemsBean> {

    @BindView(R.id.item_img)
    ImageView mImageView;
    @BindView(R.id.item_title)
    TextView mTitle;
    @BindView(R.id.item_desc)
    TextView mDesc;
    @BindView(R.id.item_btn)
    Button mButton;

    public GameCenterHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(@NonNull GameCenterInfo.ItemsBean data, int position) {
        Glide.with(mImageView.getContext())
                .load(data.getCover())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(mImageView);
        mTitle.setText(data.getTitle());
        mDesc.setText(data.getSummary());

        mButton.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           BrowserActivity.launch(mTitle.getContext(),data.getDownload_link(),data.getTitle());
                                       }
                                   }
        );

    }
}
