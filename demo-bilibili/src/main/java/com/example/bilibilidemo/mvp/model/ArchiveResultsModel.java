package com.example.bilibilidemo.mvp.model;

import android.app.Application;

import com.example.bilibilidemo.mvp.model.api.service.BiliAppService;
import com.example.bilibilidemo.mvp.model.entity.SearchArchiveInfo;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.example.bilibilidemo.mvp.contract.ArchiveResultsContract;

import io.reactivex.Observable;


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
public class ArchiveResultsModel extends BaseModel implements ArchiveResultsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ArchiveResultsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<SearchArchiveInfo> loadData(String content, int page, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(BiliAppService.class)
                .searchArchive(content,page,pageSize);
    }
}