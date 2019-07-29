package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.HomeRecommendedModule;
import com.example.bilibilidemo.mvp.contract.HomeRecommendedContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.bilibilidemo.mvp.ui.fragment.HomeRecommendedFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 01:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeRecommendedModule.class, dependencies = AppComponent.class)
public interface HomeRecommendedComponent {
    void inject(HomeRecommendedFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeRecommendedComponent.Builder view(HomeRecommendedContract.View view);

        HomeRecommendedComponent.Builder appComponent(AppComponent appComponent);

        HomeRecommendedComponent build();
    }
}