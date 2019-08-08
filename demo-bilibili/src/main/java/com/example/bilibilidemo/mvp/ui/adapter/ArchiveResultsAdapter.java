package com.example.bilibilidemo.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.bilibilidemo.R;
import com.example.bilibilidemo.mvp.model.entity.SearchArchiveInfo;
import com.example.bilibilidemo.mvp.ui.holder.ArchiveResultsHolder;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * author: 小川
 * Date: 2019/8/3
 * Description:
 */
public class ArchiveResultsAdapter extends DefaultAdapter<SearchArchiveInfo.DataBean.ItemsBean.ArchiveBean> {

    public ArchiveResultsAdapter(List<SearchArchiveInfo.DataBean.ItemsBean.ArchiveBean> infos) {
        super(infos);
    }

    @NonNull
    @Override
    public BaseHolder<SearchArchiveInfo.DataBean.ItemsBean.ArchiveBean> getHolder(@NonNull View v, int viewType) {
        return new ArchiveResultsHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_search_archive;
    }
}
