package com.example.bilibilidemo.mvp.model.entity;

import java.util.List;

/**
 * author: 小川
 * Date: 2019/7/24
 * Description:
 */
public class LiveHeader {
    private List<LiveAppIndexInfo.DataBean.BannerBean> banner;

    public List<LiveAppIndexInfo.DataBean.BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<LiveAppIndexInfo.DataBean.BannerBean> banner) {
        this.banner = banner;
    }
}
