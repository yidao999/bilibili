package com.example.bilibilidemo.mvp.ui.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bilibilidemo.R;
import com.example.bilibilidemo.app.utils.GlideImageLoader;
import com.example.bilibilidemo.mvp.model.entity.LiveAppIndexInfo;
import com.example.bilibilidemo.mvp.model.entity.LiveHeader;
import com.example.bilibilidemo.mvp.model.entity.RecommendBannerInfo;
import com.example.bilibilidemo.mvp.ui.activity.BrowserActivity;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

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
public class RecommendedHeaderItemViewBinder extends ItemViewBinder<RecommendBannerInfo,RecommendedHeaderItemViewBinder.Holder> {

    @NonNull
    @Override
    protected Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View inflate = inflater.inflate(R.layout.item_recommended_banner, parent, false);
        return new Holder(inflate);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, @NonNull RecommendBannerInfo item) {
        initBanner(holder,item);
        listener(holder,item);
    }

    private void initBanner(Holder holder, RecommendBannerInfo item) {
        List<String> images = new ArrayList<>();
        for (int i = 0; i < item.getData().size(); i++) {
            RecommendBannerInfo.DataBean dataBean = item.getData().get(i);
            images.add(dataBean.getImage());
        }
        holder.banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        holder.banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        holder.banner.start();
    }

    private void listener(Holder holder, RecommendBannerInfo item) {
                holder.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Context context = holder.banner.getContext();
                BrowserActivity.launch(context,item.getData().get(position).getValue(),item.getData().get(position).getTitle());
            }
        });
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        Banner banner;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
