package com.example.bilibilidemo.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.bilibilidemo.mvp.contract.HomePageContract;
import com.example.bilibilidemo.mvp.model.HomePageModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 17:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HomePageModule {

    @Binds
    abstract HomePageContract.Model bindHomePageModel(HomePageModel model);
}