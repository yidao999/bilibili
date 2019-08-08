package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.IFavoritesModule;
import com.example.bilibilidemo.mvp.contract.IFavoritesContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.bilibilidemo.mvp.ui.fragment.IFavoritesFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/04/2019 10:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = IFavoritesModule.class, dependencies = AppComponent.class)
public interface IFavoritesComponent {
    void inject(IFavoritesFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        IFavoritesComponent.Builder view(IFavoritesContract.View view);

        IFavoritesComponent.Builder appComponent(AppComponent appComponent);

        IFavoritesComponent build();
    }
}