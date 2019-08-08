package com.example.bilibilidemo.mvp.presenter;

import android.app.Application;

import com.example.bilibilidemo.mvp.model.entity.RecommendBannerInfo;
import com.example.bilibilidemo.mvp.model.entity.RecommendInfo;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.Items;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.example.bilibilidemo.mvp.contract.HomeRecommendedContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 01:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomeRecommendedPresenter extends BasePresenter<HomeRecommendedContract.Model, HomeRecommendedContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private RecommendBannerInfo recommendBannerInfos;

    @Inject
    public HomeRecommendedPresenter(HomeRecommendedContract.Model model, HomeRecommendedContract.View rootView) {
        super(model, rootView);
    }

    public void loadData() {
        mModel.getRecommendedBannerInfo()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 3))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .observeOn(Schedulers.io())
                .flatMap(new Function<RecommendBannerInfo, ObservableSource<RecommendInfo>>() {
                    @Override
                    public ObservableSource<RecommendInfo> apply(RecommendBannerInfo recommendBannerInfo) throws Exception {
                        //header
                        recommendBannerInfos = recommendBannerInfo;
                        return mModel.getRecommendedInfo();
                    }
                })
                .map(RecommendInfo::getResult)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<List<RecommendInfo.ResultBean>>(mErrorHandler) {
                    @Override
                    public void onNext(List<RecommendInfo.ResultBean> resultBeans) {
                        itemData(resultBeans);
                    }
                });
    }

    private void itemData(List<RecommendInfo.ResultBean> resultBeans) {
        //一定要每次都new Items  解决滑动崩溃Bug
        Items items = new Items();
        //header
        items.add(recommendBannerInfos);
        //partition + live
        for (int i = 0; i < resultBeans.size(); i++) {
            partition(items, resultBeans, i);
            for (int j = 0; j < 4; j++) {
                items.add(resultBeans.get(i).getBody().get(j));
                if(j == 3){
                    items.add(resultBeans.get(i));
                }
            }
        }

        //最后添加
        mRootView.finishTask(items);
    }

    private void partition(Items items, List<RecommendInfo.ResultBean> resultBeans, int i) {
        items.add(resultBeans.get(i).getHead());
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
