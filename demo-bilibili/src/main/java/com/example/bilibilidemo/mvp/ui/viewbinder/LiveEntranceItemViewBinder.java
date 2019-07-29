package com.example.bilibilidemo.mvp.ui.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bilibilidemo.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * author: 小川
 * Date: 2019/7/24
 * Description:
 */
public class LiveEntranceItemViewBinder extends ItemViewBinder<LiveEntranceItemViewBinder.LiveEntranceItem, LiveEntranceItemViewBinder.Holder> {

    @NonNull
    @Override
    protected Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_live_entrance,parent,false);
        return new Holder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, @NonNull LiveEntranceItem item) {

    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class LiveEntranceItem {
    }
}
