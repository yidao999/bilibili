package com.example.bilibilidemo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.bilibilidemo.app.utils.SystemUiVisibilityUtil;
import com.example.bilibilidemo.mvp.model.Constant;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.lifecycle.Lifecycleable;
import com.jess.arms.utils.ArmsUtils;

import com.example.bilibilidemo.di.component.DaggerSplashComponent;
import com.example.bilibilidemo.mvp.contract.SplashContract;
import com.example.bilibilidemo.mvp.presenter.SplashPresenter;

import com.example.bilibilidemo.R;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 15:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSplashComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_splash; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        SystemUiVisibilityUtil.hideStatusBar(getWindow(), true);
        setUpSplash();

    }

    private void setUpSplash() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .compose(RxLifecycleUtils.bindToLifecycle((Lifecycleable) this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> finishTask());
    }

    private void finishTask() {
        int intergerSF = DataHelper.getIntergerSF(this, Constant.KEY);//1为true,默认-1为false
        if (intergerSF == 1) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        SplashActivity.this.finish();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
