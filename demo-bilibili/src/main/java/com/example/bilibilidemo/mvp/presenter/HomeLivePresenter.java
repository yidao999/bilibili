package com.example.bilibilidemo.mvp.presenter;

import android.app.Application;
import android.widget.Toast;

import com.example.bilibilidemo.R;
import com.example.bilibilidemo.mvp.model.entity.LiveAppIndexInfo;
import com.example.bilibilidemo.mvp.model.entity.LiveHeader;
import com.example.bilibilidemo.mvp.ui.viewbinder.LiveEntranceItemViewBinder;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.Items;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.example.bilibilidemo.mvp.contract.HomeLiveContract;
import com.jess.arms.utils.RxLifecycleUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 16:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomeLivePresenter extends BasePresenter<HomeLiveContract.Model, HomeLiveContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HomeLivePresenter(HomeLiveContract.Model model, HomeLiveContract.View rootView) {
        super(model, rootView);
    }

    public void loadData() {
        mModel.getData()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .observeOn(Schedulers.newThread())
                .map(new Function<LiveAppIndexInfo, Items>() {
                    @Override
                    public Items apply(LiveAppIndexInfo liveAppIndexInfo) throws Exception {
                        return getItems(liveAppIndexInfo);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<Items>(mErrorHandler) {
                    @Override
                    public void onNext(Items items) {
                        mRootView.onDataUpdated(items);
                    }
                });
    }

    private Items getItems(LiveAppIndexInfo data) {
        Items items = new Items();
        //header
        LiveHeader liveHeader = new LiveHeader();
        liveHeader.setBanner(data.getData().getBanner());
        items.add(liveHeader);
        //entrance
        items.add(new LiveEntranceItemViewBinder.LiveEntranceItem());
        //partition+item
        items.add(data.getData().getPartitions().get(0).getPartition());

        return items;
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
