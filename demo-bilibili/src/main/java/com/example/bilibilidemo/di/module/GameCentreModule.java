package com.example.bilibilidemo.di.module;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.bilibilidemo.mvp.model.entity.GameCenterInfo;
import com.example.bilibilidemo.mvp.ui.adapter.GameCentreAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.bilibilidemo.mvp.contract.GameCentreContract;
import com.example.bilibilidemo.mvp.model.GameCentreModel;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/02/2019 22:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class GameCentreModule {

    @Binds
    abstract GameCentreContract.Model bindGameCentreModel(GameCentreModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(GameCentreContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<GameCenterInfo.ItemsBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static GameCentreAdapter provideGameCentreAdapter(List<GameCenterInfo.ItemsBean> list){
        return new GameCentreAdapter(list);
    }
}