package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.HomeLiveModule;
import com.example.bilibilidemo.mvp.contract.HomeLiveContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.bilibilidemo.mvp.ui.fragment.HomeLiveFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 16:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeLiveModule.class, dependencies = AppComponent.class)
public interface HomeLiveComponent {
    void inject(HomeLiveFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeLiveComponent.Builder view(HomeLiveContract.View view);

        HomeLiveComponent.Builder appComponent(AppComponent appComponent);

        HomeLiveComponent build();
    }
}