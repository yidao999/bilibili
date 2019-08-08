package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.UserInfoDetailsModule;
import com.example.bilibilidemo.mvp.contract.UserInfoDetailsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.bilibilidemo.mvp.ui.activity.UserInfoDetailsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/29/2019 22:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = UserInfoDetailsModule.class, dependencies = AppComponent.class)
public interface UserInfoDetailsComponent {
    void inject(UserInfoDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UserInfoDetailsComponent.Builder view(UserInfoDetailsContract.View view);

        UserInfoDetailsComponent.Builder appComponent(AppComponent appComponent);

        UserInfoDetailsComponent build();
    }
}