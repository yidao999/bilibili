package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.LivePlayerModule;
import com.example.bilibilidemo.mvp.contract.LivePlayerContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.bilibilidemo.mvp.ui.activity.LivePlayerActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/29/2019 16:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LivePlayerModule.class, dependencies = AppComponent.class)
public interface LivePlayerComponent {
    void inject(LivePlayerActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LivePlayerComponent.Builder view(LivePlayerContract.View view);

        LivePlayerComponent.Builder appComponent(AppComponent appComponent);

        LivePlayerComponent build();
    }
}