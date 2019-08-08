package com.example.bilibilidemo.mvp.model.api.service;

import com.example.bilibilidemo.mvp.model.entity.VipGameInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

import static com.example.bilibilidemo.mvp.model.api.Api.HOME_LIVE_DOMAIN_NAME;
import static com.example.bilibilidemo.mvp.model.api.Api.VIP_GAME_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * author: 小川
 * Date: 2019/8/2
 * Description:
 */
public interface VipService {

    @Headers({DOMAIN_NAME_HEADER + VIP_GAME_DOMAIN_NAME})
    @GET("api/v1/games/gift?access_key=19946e1ef3b5cad1a756c475a67185bb&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3940&device=phone&mobi_app=iphone&platform=ios&sign=f6a995f30f3d4362a628191d523e3012&ts=1477922329")
    Observable<VipGameInfo> getVipGame();
}
