package com.example.bilibilidemo.mvp.model.api.service;

import com.example.bilibilidemo.mvp.model.entity.RecommendBannerInfo;
import com.example.bilibilidemo.mvp.model.entity.RecommendInfo;
import com.example.bilibilidemo.mvp.model.entity.SearchArchiveInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.example.bilibilidemo.mvp.model.api.Api.APP_BASE_URL_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * author: 小川
 * Date: 2019/8/3
 * Description:
 */
public interface BiliAppService {
    /**
     * 综合搜索
     */
    @Headers({DOMAIN_NAME_HEADER + APP_BASE_URL_DOMAIN_NAME})
    @GET("x/v2/search?actionKey=appkey&appkey=27eb53fc9058f8c3&build=3710&device=phone&duration=0&mobi_app=iphone&order=default&platform=ios&rid=0")
    Observable<SearchArchiveInfo> searchArchive(
            @Query("keyword") String content, @Query("pn") int page, @Query("ps") int pagesize);

    /**
     * 首页推荐banner
     */
    @Headers({DOMAIN_NAME_HEADER + APP_BASE_URL_DOMAIN_NAME})
    @GET("x/banner?plat=4&build=411007&channel=bilih5")
    Observable<RecommendBannerInfo> getRecommendedBannerInfo();

    /**
     * 首页推荐数据
     */
    @Headers({DOMAIN_NAME_HEADER + APP_BASE_URL_DOMAIN_NAME})
    @GET("x/show/old?platform=android&device=&build=412001")
    Observable<RecommendInfo> getRecommendedInfo();
}
