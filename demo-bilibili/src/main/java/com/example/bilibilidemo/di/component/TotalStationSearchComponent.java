package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.TotalStationSearchModule;
import com.example.bilibilidemo.mvp.contract.TotalStationSearchContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.bilibilidemo.mvp.ui.activity.TotalStationSearchActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 12:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TotalStationSearchModule.class, dependencies = AppComponent.class)
public interface TotalStationSearchComponent {
    void inject(TotalStationSearchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TotalStationSearchComponent.Builder view(TotalStationSearchContract.View view);

        TotalStationSearchComponent.Builder appComponent(AppComponent appComponent);

        TotalStationSearchComponent build();
    }
}