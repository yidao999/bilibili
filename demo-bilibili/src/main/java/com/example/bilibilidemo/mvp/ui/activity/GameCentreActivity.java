package com.example.bilibilidemo.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bilibilidemo.app.utils.HeaderViewRecyclerAdapter;
import com.example.bilibilidemo.di.component.DaggerGameCentreComponent;
import com.example.bilibilidemo.mvp.model.entity.GameCenterInfo;
import com.example.bilibilidemo.mvp.model.entity.VipGameInfo;
import com.example.bilibilidemo.mvp.ui.adapter.GameCentreAdapter;
import com.example.bilibilidemo.mvp.ui.widget.CircleProgressView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.bilibilidemo.mvp.contract.GameCentreContract;
import com.example.bilibilidemo.mvp.presenter.GameCentrePresenter;

import com.example.bilibilidemo.R;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class GameCentreActivity extends BaseActivity<GameCentrePresenter> implements GameCentreContract.View {

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbars)
    Toolbar mToolbar;
    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    @Inject
    GameCentreAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    List<GameCenterInfo.ItemsBean> mData;

    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGameCentreComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_game_centre; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolbar();
        initRecyclerView();
        mPresenter.loadData();
    }

    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
    }

    private void initToolbar() {
        mToolbar.setTitle("游戏中心");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void showLoading() {
        mRecyclerView.setVisibility(View.GONE);
        mCircleProgressView.spin();
    }

    @Override
    public void hideLoading() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mCircleProgressView.stopSpinning();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void createHeadView(VipGameInfo.DataBean items) {
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_vip_game_head_view, mRecyclerView, false);
        ImageView mVipGameImage = (ImageView) headView.findViewById(R.id.vip_game_image);
        Glide.with(getActivity()).load(items.getImgPath())
                .into(mVipGameImage);
        mVipGameImage.setOnClickListener(v -> BrowserActivity.launch(GameCentreActivity.this,
                items.getLink(), "年度大会员游戏礼包专区"));
        mHeaderViewRecyclerAdapter.addHeaderView(headView);
    }

    @Override
    public void finishTask(List<GameCenterInfo.ItemsBean> items) {
        mRecyclerView.setAdapter(mHeaderViewRecyclerAdapter);
        mData.clear();
        mData.addAll(items);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
