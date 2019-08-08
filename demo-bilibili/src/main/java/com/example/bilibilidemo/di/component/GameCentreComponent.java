package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.GameCentreModule;
import com.example.bilibilidemo.mvp.contract.GameCentreContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.bilibilidemo.mvp.ui.activity.GameCentreActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/02/2019 22:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GameCentreModule.class, dependencies = AppComponent.class)
public interface GameCentreComponent {
    void inject(GameCentreActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GameCentreComponent.Builder view(GameCentreContract.View view);

        GameCentreComponent.Builder appComponent(AppComponent appComponent);

        GameCentreComponent build();
    }
}