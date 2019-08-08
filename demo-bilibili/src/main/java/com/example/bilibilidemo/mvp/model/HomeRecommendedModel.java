package com.example.bilibilidemo.mvp.model;

import android.app.Application;

import com.example.bilibilidemo.mvp.model.api.service.BiliAppService;
import com.example.bilibilidemo.mvp.model.entity.RecommendBannerInfo;
import com.example.bilibilidemo.mvp.model.entity.RecommendInfo;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.example.bilibilidemo.mvp.contract.HomeRecommendedContract;

import io.reactivex.Observable;


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
public class HomeRecommendedModel extends BaseModel implements HomeRecommendedContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HomeRecommendedModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<RecommendBannerInfo> getRecommendedBannerInfo() {
        return mRepositoryManager.obtainRetrofitService(BiliAppService.class)
                .getRecommendedBannerInfo();
    }

    @Override
    public Observable<RecommendInfo> getRecommendedInfo() {
        return mRepositoryManager.obtainRetrofitService(BiliAppService.class)
                .getRecommendedInfo();
    }
}