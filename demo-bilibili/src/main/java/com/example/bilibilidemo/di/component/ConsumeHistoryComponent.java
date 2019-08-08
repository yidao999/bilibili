package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.ConsumeHistoryModule;
import com.example.bilibilidemo.mvp.contract.ConsumeHistoryContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.bilibilidemo.mvp.ui.fragment.ConsumeHistoryFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/04/2019 11:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = ConsumeHistoryModule.class, dependencies = AppComponent.class)
public interface ConsumeHistoryComponent {
    void inject(ConsumeHistoryFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ConsumeHistoryComponent.Builder view(ConsumeHistoryContract.View view);

        ConsumeHistoryComponent.Builder appComponent(AppComponent appComponent);

        ConsumeHistoryComponent build();
    }
}