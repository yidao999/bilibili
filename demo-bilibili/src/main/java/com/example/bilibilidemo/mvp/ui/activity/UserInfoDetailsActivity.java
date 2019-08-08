package com.example.bilibilidemo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bilibilidemo.app.utils.NumberUtil;
import com.example.bilibilidemo.app.utils.SystemBarHelper;
import com.example.bilibilidemo.mvp.model.entity.UserDetailsInfo;
import com.example.bilibilidemo.mvp.ui.widget.CircleImageView;
import com.example.bilibilidemo.mvp.ui.widget.CircleProgressView;
import com.example.bilibilidemo.mvp.ui.widget.NoScrollViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.bilibilidemo.di.component.DaggerUserInfoDetailsComponent;
import com.example.bilibilidemo.mvp.contract.UserInfoDetailsContract;
import com.example.bilibilidemo.mvp.presenter.UserInfoDetailsPresenter;

import com.example.bilibilidemo.R;


import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/29/2019 22:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserInfoDetailsActivity extends BaseActivity<UserInfoDetailsPresenter> implements UserInfoDetailsContract.View {

    @BindView(R.id.user_avatar_view)
    CircleImageView mAvatarImage;
    @BindView(R.id.user_name)
    TextView mUserNameText;
    @BindView(R.id.user_desc)
    TextView mDescriptionText;
    @BindView(R.id.tv_follow_users)
    TextView mFollowNumText;
    @BindView(R.id.tv_fans)
    TextView mFansNumText;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.user_lv)
    ImageView mUserLv;
    @BindView(R.id.user_sex)
    ImageView mUserSex;
    @BindView(R.id.author_verified_layout)
    LinearLayout mAuthorVerifiedLayout;
    @BindView(R.id.author_verified_text)
    TextView mAuthorVerifiedText;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.line)
    View mLineView;
    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private int mid;
    private String name = "";
    private String avatar_url;
    private static final String EXTRA_USER_NAME = "extra_user_name", EXTRA_MID = "extra_mid", EXTRA_AVATAR_URL = "extra_avatar_url";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserInfoDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_info_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initIntent();
        initToolBar();
        initShowInfo();
    }

    private void initToolBar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        //设置StatusBar透明
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, mToolbar);
    }

    private void initShowInfo() {
        if (name != null) {
            mUserNameText.setText(name);
        }
        if (avatar_url != null) {
            Glide.with(UserInfoDetailsActivity.this)
                    .load(avatar_url)
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.ico_user_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mAvatarImage);
        }
        //网络设置用户信息
        mPresenter.getData(mid);
        mViewPager.setVisibility(View.INVISIBLE);
    }

    @Override
    public void finishTask(UserDetailsInfo mUserDetailsInfo) {
        //设置用户头像
        Glide.with(UserInfoDetailsActivity.this)
                .load(mUserDetailsInfo.getCard().getFace())
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.ico_user_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mAvatarImage);
        //设置粉丝和关注
        mUserNameText.setText(mUserDetailsInfo.getCard().getName());
        mFollowNumText.setText(String.valueOf(mUserDetailsInfo.getCard().getAttention()));
        mFansNumText.setText(NumberUtil.converString(mUserDetailsInfo.getCard().getFans()));
        //设置用户等级
        setUserLevel(Integer.valueOf(mUserDetailsInfo.getCard().getRank()));
        //设置用户性别
        switch (mUserDetailsInfo.getCard().getSex()) {
            case "男":
                mUserSex.setImageResource(R.drawable.ic_user_male);
                break;
            case "女":
                mUserSex.setImageResource(R.drawable.ic_user_female);
                break;
            default:
                mUserSex.setImageResource(R.drawable.ic_user_gay_border);
                break;
        }
        //设置用户签名信息
        if (!TextUtils.isEmpty(mUserDetailsInfo.getCard().getSign())) {
            mDescriptionText.setText(mUserDetailsInfo.getCard().getSign());
        } else {
            mDescriptionText.setText("这个人懒死了,什么都没有写(・－・。)");
        }
        //设置认证用户信息
        if (mUserDetailsInfo.getCard().isApprove()) {
            //认证用户 显示认证资料
            mAuthorVerifiedLayout.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mUserDetailsInfo.getCard().getDescription())) {
                mAuthorVerifiedText.setText(mUserDetailsInfo.getCard().getDescription());
            } else {
                mAuthorVerifiedText.setText("这个人懒死了,什么都没有写(・－・。)");
            }
        } else {
            //普通用户
            mAuthorVerifiedLayout.setVisibility(View.GONE);
        }
        //获取用户详情全部数据
//        getUserAllData();// TODO: 2019/8/2 api无效  *暂无需处理*
        ArmsUtils.makeText(getApplicationContext(),"用户隐私未公开");
    }

    private void setUserLevel(int rank) {
        if (rank == 0) {
            mUserLv.setImageResource(R.drawable.ic_lv0);
        } else if (rank == 1) {
            mUserLv.setImageResource(R.drawable.ic_lv1);
        } else if (rank == 200) {
            mUserLv.setImageResource(R.drawable.ic_lv2);
        } else if (rank == 1500) {
            mUserLv.setImageResource(R.drawable.ic_lv3);
        } else if (rank == 3000) {
            mUserLv.setImageResource(R.drawable.ic_lv4);
        } else if (rank == 7000) {
            mUserLv.setImageResource(R.drawable.ic_lv5);
        } else if (rank == 10000) {
            mUserLv.setImageResource(R.drawable.ic_lv6);
        }
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra(EXTRA_USER_NAME);
            mid = intent.getIntExtra(EXTRA_MID, -1);
            avatar_url = intent.getStringExtra(EXTRA_AVATAR_URL);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
}
