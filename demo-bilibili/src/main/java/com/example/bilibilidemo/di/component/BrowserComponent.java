package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.BrowserModule;
import com.example.bilibilidemo.mvp.contract.BrowserContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.bilibilidemo.mvp.ui.activity.BrowserActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/29/2019 15:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BrowserModule.class, dependencies = AppComponent.class)
public interface BrowserComponent {
    void inject(BrowserActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BrowserComponent.Builder view(BrowserContract.View view);

        BrowserComponent.Builder appComponent(AppComponent appComponent);

        BrowserComponent build();
    }
}