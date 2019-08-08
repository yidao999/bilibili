package com.example.bilibilidemo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.bilibilidemo.di.module.ArchiveResultsModule;
import com.example.bilibilidemo.mvp.contract.ArchiveResultsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.bilibilidemo.mvp.ui.fragment.ArchiveResultsFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 15:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = ArchiveResultsModule.class, dependencies = AppComponent.class)
public interface ArchiveResultsComponent {
    void inject(ArchiveResultsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ArchiveResultsComponent.Builder view(ArchiveResultsContract.View view);

        ArchiveResultsComponent.Builder appComponent(AppComponent appComponent);

        ArchiveResultsComponent build();
    }
}