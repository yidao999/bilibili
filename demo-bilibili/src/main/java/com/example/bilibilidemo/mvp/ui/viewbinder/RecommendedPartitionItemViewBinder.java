package com.example.bilibilidemo.mvp.ui.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bilibilidemo.R;
import com.example.bilibilidemo.mvp.model.entity.RecommendInfo;

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
public class RecommendedPartitionItemViewBinder extends ItemViewBinder<RecommendInfo.ResultBean.HeadBean, RecommendedPartitionItemViewBinder.Holder> {

    private int[] icons = new int[]{
            R.drawable.ic_header_hot, R.drawable.ic_head_live,
            R.drawable.ic_category_t13, R.drawable.ic_category_t1,
            R.drawable.ic_category_t3, R.drawable.ic_category_t129,
            R.drawable.ic_category_t4, R.drawable.ic_category_t119,
            R.drawable.ic_category_t36, R.drawable.ic_category_t160,
            R.drawable.ic_category_t155, R.drawable.ic_category_t5,
            R.drawable.ic_category_t11, R.drawable.ic_category_t23
    };

    @NonNull
    @Override
    protected Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View inflate = inflater.inflate(R.layout.item_recommended_partition, parent, false);
        return new Holder(inflate);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, @NonNull RecommendInfo.ResultBean.HeadBean item) {
        holder.mTypeTv.setText(item.getTitle());
        initTypeIcon(holder, item.getTitle());
        if(item.getTitle().equals("热门焦点")){
            holder.mTypeMore.setVisibility(View.GONE);
            holder.mTypeRankBtn.setVisibility(View.VISIBLE);
            holder.mAllLiveNum.setVisibility(View.GONE);
        }else{
            holder.mTypeMore.setVisibility(View.VISIBLE);
            holder.mTypeRankBtn.setVisibility(View.GONE);
            holder.mAllLiveNum.setVisibility(View.GONE);
        }
        // TODO: 2019/8/4 排行榜点击监听 等待处理
//        initListener(mTypeRankBtn);
    }

    private void initListener() {

    }

    private void initTypeIcon(Holder holder, String title) {
        switch (title) {
            case "热门焦点":
                holder.mTypeImg.setImageResource(icons[0]);
                break;
            case "正在直播":
                holder.mTypeImg.setImageResource(icons[1]);
                break;
            case "番剧区":
                holder.mTypeImg.setImageResource(icons[2]);
                break;
            case "动画区":
                holder.mTypeImg.setImageResource(icons[3]);
                break;
            case "音乐区":
                holder.mTypeImg.setImageResource(icons[4]);
                break;
            case "舞蹈区":
                holder.mTypeImg.setImageResource(icons[5]);
                break;
            case "游戏区":
                holder.mTypeImg.setImageResource(icons[6]);
                break;
            case "鬼畜区":
                holder.mTypeImg.setImageResource(icons[7]);
                break;
            case "科技区":
                holder.mTypeImg.setImageResource(icons[8]);
                break;
            case "生活区":
                holder.mTypeImg.setImageResource(icons[9]);
                break;
            case "时尚区":
                holder.mTypeImg.setImageResource(icons[10]);
                break;
            case "娱乐区":
                holder.mTypeImg.setImageResource(icons[11]);
                break;
            case "电视剧区":
                holder.mTypeImg.setImageResource(icons[12]);
                break;
            case "电影区":
                holder.mTypeImg.setImageResource(icons[13]);
                break;
            case "活动中心":
                holder.mTypeMore.setText("进去看看");
                break;
        }
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_type_img)
        ImageView mTypeImg;
        @BindView(R.id.item_type_tv)
        TextView mTypeTv;
        @BindView(R.id.item_type_more)
        TextView mTypeMore;
        @BindView(R.id.item_type_rank_btn)
        TextView mTypeRankBtn;
        @BindView(R.id.item_live_all_num)
        TextView mAllLiveNum;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
