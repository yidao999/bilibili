package com.example.bilibilidemo.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.bilibilidemo.mvp.model.entity.SearchArchiveInfo;
import com.example.bilibilidemo.mvp.ui.adapter.ArchiveResultsAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.bilibilidemo.mvp.contract.ArchiveResultsContract;
import com.example.bilibilidemo.mvp.model.ArchiveResultsModel;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 15:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ArchiveResultsModule {

    @Binds
    abstract ArchiveResultsContract.Model bindArchiveResultsModel(ArchiveResultsModel model);

    @FragmentScope
    @Provides
    static LinearLayoutManager provideLayoutManager(ArchiveResultsContract.View view){
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    static List<SearchArchiveInfo.DataBean.ItemsBean.ArchiveBean> provideList(){
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static ArchiveResultsAdapter provideArchiveResultsAdapter(List<SearchArchiveInfo.DataBean.ItemsBean.ArchiveBean> list){
        return new ArchiveResultsAdapter(list);
    }
}