package com.example.bilibilidemo.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.bilibilidemo.mvp.contract.AttentionPeopleContract;
import com.example.bilibilidemo.mvp.model.AttentionPeopleModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/04/2019 11:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class AttentionPeopleModule {

    @Binds
    abstract AttentionPeopleContract.Model bindAttentionPeopleModel(AttentionPeopleModel model);
}