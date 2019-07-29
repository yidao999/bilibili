package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.HomePageModule;
import com.example.bilibilidemo.mvp.contract.HomePageContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.bilibilidemo.mvp.ui.fragment.HomePageFragment;


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
@FragmentScope
@Component(modules = HomePageModule.class, dependencies = AppComponent.class)
public interface HomePageComponent {
    void inject(HomePageFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomePageComponent.Builder view(HomePageContract.View view);

        HomePageComponent.Builder appComponent(AppComponent appComponent);

        HomePageComponent build();
    }
}