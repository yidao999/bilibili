package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.OffLineDownloadModule;
import com.example.bilibilidemo.mvp.contract.OffLineDownloadContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.bilibilidemo.mvp.ui.activity.OffLineDownloadActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/29/2019 13:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = OffLineDownloadModule.class, dependencies = AppComponent.class)
public interface OffLineDownloadComponent {
    void inject(OffLineDownloadActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OffLineDownloadComponent.Builder view(OffLineDownloadContract.View view);

        OffLineDownloadComponent.Builder appComponent(AppComponent appComponent);

        OffLineDownloadComponent build();
    }
}