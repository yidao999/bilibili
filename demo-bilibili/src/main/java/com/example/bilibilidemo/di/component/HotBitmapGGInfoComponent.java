package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.HotBitmapGGInfoModule;
import com.example.bilibilidemo.mvp.contract.HotBitmapGGInfoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.bilibilidemo.mvp.ui.activity.HotBitmapGGInfoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/04/2019 11:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = HotBitmapGGInfoModule.class, dependencies = AppComponent.class)
public interface HotBitmapGGInfoComponent {
    void inject(HotBitmapGGInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HotBitmapGGInfoComponent.Builder view(HotBitmapGGInfoContract.View view);

        HotBitmapGGInfoComponent.Builder appComponent(AppComponent appComponent);

        HotBitmapGGInfoComponent build();
    }
}