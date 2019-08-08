package com.example.bilibilidemo.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.bilibilidemo.R;
import com.example.bilibilidemo.mvp.model.entity.GameCenterInfo;
import com.example.bilibilidemo.mvp.ui.holder.GameCenterHolder;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * author: 小川
 * Date: 2019/8/2
 * Description:
 */
public class GameCentreAdapter extends DefaultAdapter<GameCenterInfo.ItemsBean> {

    public GameCentreAdapter(List<GameCenterInfo.ItemsBean> infos) {
        super(infos);
    }

    @NonNull
    @Override
    public BaseHolder<GameCenterInfo.ItemsBean> getHolder(@NonNull View v, int viewType) {
        return new GameCenterHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_game_center;
    }
}
