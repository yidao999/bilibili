package com.example.bilibilidemo.mvp.ui.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bilibilidemo.R;
import com.example.bilibilidemo.app.utils.GlideImageLoader;
import com.example.bilibilidemo.mvp.model.entity.LiveAppIndexInfo;
import com.example.bilibilidemo.mvp.model.entity.LiveHeader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * author: 小川
 * Date: 2019/7/23
 * Description:
 */
public class LiveHeaderItemViewBinder extends ItemViewBinder<LiveHeader, LiveHeaderItemViewBinder.Holder> {

    @NonNull
    @Override
    protected Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_live_banner, parent, false);
        return new Holder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, @NonNull LiveHeader item) {
        initBanner(holder,item);
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.banner)
        Banner banner;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    private void initBanner(Holder holder, LiveHeader item) {
        List<String> images = new ArrayList<>();
        for (int i = 0; i < item.getBanner().size(); i++) {
            LiveAppIndexInfo.DataBean.BannerBean bannerBean = item.getBanner().get(i);
            images.add(bannerBean.getImg());
        }
        holder.banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        holder.banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        holder.banner.start();
    }
}
