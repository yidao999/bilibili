package com.example.bilibilidemo.mvp.ui.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bilibilidemo.R;
import com.example.bilibilidemo.mvp.model.entity.LiveAppIndexInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * author: 小川
 * Date: 2019/7/24
 * Description:
 */
public class LivePartitionItemViewBinder extends ItemViewBinder<LiveAppIndexInfo.DataBean.PartitionsBean.PartitionBeanX, LivePartitionItemViewBinder.Holder> {

    @NonNull
    @Override
    protected Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View inflate = inflater.inflate(R.layout.item_live_partition, parent, false);
        return new Holder(inflate);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, @NonNull LiveAppIndexInfo.DataBean.PartitionsBean.PartitionBeanX item) {
        Context context = holder.itemIcon.getContext();
        Glide.with(context)
                .load(item.getSub_icon().getSrc())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemIcon);
        holder.itemTitle.setText(item.getName());
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(
                "当前" + item.getCount() + "个直播");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(
                context.getResources().getColor(R.color.pink_text_color));
        stringBuilder.setSpan(foregroundColorSpan, 2,
                stringBuilder.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.itemCount.setText(stringBuilder);
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_live_partition_icon)
        ImageView itemIcon;
        @BindView(R.id.item_live_partition_title)
        TextView itemTitle;
        @BindView(R.id.item_live_partition_count)
        TextView itemCount;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
