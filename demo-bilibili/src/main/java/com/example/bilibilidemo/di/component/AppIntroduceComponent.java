package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.AppIntroduceModule;
import com.example.bilibilidemo.mvp.contract.AppIntroduceContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.bilibilidemo.mvp.ui.activity.AppIntroduceActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/04/2019 13:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AppIntroduceModule.class, dependencies = AppComponent.class)
public interface AppIntroduceComponent {
    void inject(AppIntroduceActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppIntroduceComponent.Builder view(AppIntroduceContract.View view);

        AppIntroduceComponent.Builder appComponent(AppComponent appComponent);

        AppIntroduceComponent build();
    }
}