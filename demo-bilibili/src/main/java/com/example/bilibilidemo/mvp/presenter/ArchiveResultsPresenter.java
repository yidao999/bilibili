package com.example.bilibilidemo.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.example.bilibilidemo.mvp.model.entity.SearchArchiveInfo;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.example.bilibilidemo.mvp.contract.ArchiveResultsContract;
import com.jess.arms.utils.RxLifecycleUtils;


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
public class ArchiveResultsPresenter extends BasePresenter<ArchiveResultsContract.Model, ArchiveResultsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ArchiveResultsPresenter(ArchiveResultsContract.Model model, ArchiveResultsContract.View rootView) {
        super(model, rootView);
    }

    public void loadData(String content, int page, int pageSize) {
        mModel.loadData(content, page, pageSize)
                .observeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 3))
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .map(SearchArchiveInfo::getData)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(dataBean -> {
                    if (dataBean.getItems().getArchive().size() < pageSize) {
                        mRootView.removeFootView();
                    }
                })
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(dataBean -> {
                    mRootView.finishTask(dataBean.getItems().getArchive());
                }, throwable -> {
                    mRootView.showEmptyView();
                    mRootView.removeFootView();
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
