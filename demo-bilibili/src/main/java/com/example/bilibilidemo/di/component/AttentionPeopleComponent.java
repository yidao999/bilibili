package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.AttentionPeopleModule;
import com.example.bilibilidemo.mvp.contract.AttentionPeopleContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.bilibilidemo.mvp.ui.fragment.AttentionPeopleFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/04/2019 11:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = AttentionPeopleModule.class, dependencies = AppComponent.class)
public interface AttentionPeopleComponent {
    void inject(AttentionPeopleFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AttentionPeopleComponent.Builder view(AttentionPeopleContract.View view);

        AttentionPeopleComponent.Builder appComponent(AppComponent appComponent);

        AttentionPeopleComponent build();
    }
}