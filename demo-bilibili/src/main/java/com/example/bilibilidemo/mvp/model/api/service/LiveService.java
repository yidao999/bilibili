package com.example.bilibilidemo.mvp.model.api.service;

import com.example.bilibilidemo.mvp.model.entity.LiveAppIndexInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

import static com.example.bilibilidemo.mvp.model.api.Api.HOME_LIVE_DOMAIN;
import static com.example.bilibilidemo.mvp.model.api.Api.HOME_LIVE_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * author: 小川
 * Date: 2019/7/24
 * Description:
 */
public interface LiveService {

    /**
     * 首页直播
     */
    @Headers({DOMAIN_NAME_HEADER + HOME_LIVE_DOMAIN_NAME})
    @GET("/AppIndex/home?_device=android&_hwid=51e96f5f2f54d5f9&_ulv=10000&access_key=563d6046f06289cbdcb472601ce5a761&appkey=c1b107428d337928&build=410000&platform=android&scale=xxhdpi&sign=fbdcfe141853f7e2c84c4d401f6a8758")
    Observable<LiveAppIndexInfo> getLive();
}
