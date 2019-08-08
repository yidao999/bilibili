package com.example.bilibilidemo.mvp.model.api.service;

import com.example.bilibilidemo.mvp.model.entity.UserDetailsInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.example.bilibilidemo.mvp.model.api.Api.DETAIL_ACCOUNT_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * author: 小川
 * Date: 2019/8/2
 * Description:
 */
public interface DetailAccountService {

    /**
     * 用户详情数据
     */
    @Headers({DOMAIN_NAME_HEADER + DETAIL_ACCOUNT_DOMAIN_NAME})
    @GET("api/member/getCardByMid")
    Observable<UserDetailsInfo> getUserInfoById(@Query("mid") int mid);

}
