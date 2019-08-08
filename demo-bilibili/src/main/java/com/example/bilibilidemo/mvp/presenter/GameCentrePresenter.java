package com.example.bilibilidemo.mvp.presenter;

import android.app.Application;
import android.content.res.AssetManager;
import android.support.v7.widget.RecyclerView;
import android.util.TimeUtils;

import com.example.bilibilidemo.mvp.model.entity.GameCenterInfo;
import com.example.bilibilidemo.mvp.model.entity.VipGameInfo;
import com.google.gson.Gson;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.example.bilibilidemo.mvp.contract.GameCentreContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;


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
public class GameCentrePresenter extends BasePresenter<GameCentreContract.Model, GameCentreContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public GameCentrePresenter(GameCentreContract.Model model, GameCentreContract.View rootView) {
        super(model, rootView);
    }

    public void loadData() {
        mModel.getVipGame()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 3))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mRootView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .doOnNext(new Consumer<VipGameInfo>() {
                    @Override
                    public void accept(VipGameInfo vipGameInfo) throws Exception {
                        mRootView.createHeadView(vipGameInfo.getData());
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<VipGameInfo, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(VipGameInfo vipGameInfo) throws Exception {
                        return Observable.just(readAssetsJson());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<String>(mErrorHandler) {
                    @Override
                    public void onNext(String s) {
                        GameCenterInfo gameCenterInfo = new Gson().fromJson(s, GameCenterInfo.class);
                        mRootView.finishTask(gameCenterInfo.getItems());
                    }
                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.hideLoading();
                    }
                });
    }

    /**
     * 读取assets下的json数据
     */
    private String readAssetsJson() {
        AssetManager assetManager = mApplication.getAssets();
        try {
            InputStream is = assetManager.open("gamecenter.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
