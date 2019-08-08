package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.VipModule;
import com.example.bilibilidemo.mvp.contract.VipContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.bilibilidemo.mvp.ui.activity.VipActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/29/2019 12:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = VipModule.class, dependencies = AppComponent.class)
public interface VipComponent {
    void inject(VipActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        VipComponent.Builder view(VipContract.View view);

        VipComponent.Builder appComponent(AppComponent appComponent);

        VipComponent build();
    }
}