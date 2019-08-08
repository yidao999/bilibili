package com.example.bilibilidemo.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bilibilidemo.mvp.model.Constant;
import com.example.bilibilidemo.mvp.ui.widget.CircleImageView;
import com.example.bilibilidemo.mvp.ui.widget.livelike.LoveLikeLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.bilibilidemo.di.component.DaggerLivePlayerComponent;
import com.example.bilibilidemo.mvp.contract.LivePlayerContract;
import com.example.bilibilidemo.mvp.presenter.LivePlayerPresenter;

import com.example.bilibilidemo.R;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.autosize.utils.LogUtils;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/29/2019 16:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LivePlayerActivity extends BaseActivity<LivePlayerPresenter> implements LivePlayerContract.View {

    @BindView(R.id.video_view)
    SurfaceView videoView;
    @BindView(R.id.bili_anim)
    ImageView mAnimView;
    @BindView(R.id.right_play)
    ImageView mRightPlayBtn;
    @BindView(R.id.bottom_layout)
    RelativeLayout mBottomLayout;
    @BindView(R.id.bottom_play)
    ImageView mBottomPlayBtn;
    @BindView(R.id.bottom_fullscreen)
    ImageView mBottomFullscreen;
    @BindView(R.id.video_start_info)
    TextView mLoadTv;
    @BindView(R.id.toolbars)
    Toolbar mToolbar;
    @BindView(R.id.user_pic)
    CircleImageView mUserPic;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.live_num)
    TextView mLiveNum;
    @BindView(R.id.love_layout)
    LoveLikeLayout mLoveLikeLayout;
    @BindView(R.id.bottom_love)
    ImageView mlove;

    private int flag = 0;
    private int cid;
    private String title;
    private int online;
    private String face;
    private String name;
    private int mid;
    private String url;
    private SurfaceHolder holder;
    private boolean isPlay = false;
    private IjkMediaPlayer ijkMediaPlayer;
    private AnimationDrawable mAnimViewBackground;
    private static final String EXTRA_USER_NAME = "extra_user_name", EXTRA_MID = "extra_mid", EXTRA_AVATAR_URL = "extra_avatar_url";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLivePlayerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_live_player; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initIntent();
        initToolBar();
        initVideo();
        initUserInfo();
        startAnim();
    }

    private void startAnim() {
        mAnimViewBackground = (AnimationDrawable) mAnimView.getBackground();
        mAnimViewBackground.start();
    }

    private void stopAnim() {
        mAnimViewBackground.stop();
        mAnimView.setVisibility(View.GONE);
        mLoadTv.setVisibility(View.GONE);
    }

    /**
     * 设置用户信息
     */
    private void initUserInfo() {
        Glide.with(LivePlayerActivity.this)
                .load(face)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.ico_user_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mUserPic);
        mUserName.setText(name);
        mLiveNum.setText(String.valueOf(online));
    }

    private void initVideo() {
        holder = videoView.getHolder();
        ijkMediaPlayer = new IjkMediaPlayer();
        playVideo(url);
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            cid = intent.getIntExtra(Constant.EXTRA_CID, 0);
            title = intent.getStringExtra(Constant.EXTRA_TITLE);
            online = intent.getIntExtra(Constant.EXTRA_ONLINE, 0);
            face = intent.getStringExtra(Constant.EXTRA_FACE);
            name = intent.getStringExtra(Constant.EXTRA_NAME);
            mid = intent.getIntExtra(Constant.EXTRA_MID, 0);
            url = intent.getStringExtra(Constant.PLAY_URL);
        }
    }

    private void initToolBar() {
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void playVideo(String uri) {

        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    stopAnim();
                    isPlay = true;
                    videoView.setVisibility(View.VISIBLE);
                    mRightPlayBtn.setImageResource(R.drawable.ic_tv_stop);
                    mBottomPlayBtn.setImageResource(R.drawable.ic_portrait_stop);
                }, throwable -> LogUtils.d("直播地址url获取失败" + throwable.getMessage()));

        try {
            if(TextUtils.isEmpty(uri)){
                ArmsUtils.snackbarText("暂无资源，播放失败");
                return;
            }
            ijkMediaPlayer.setDataSource(this, Uri.parse(uri));
            ijkMediaPlayer.setDisplay(holder);
            holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    ijkMediaPlayer.setDisplay(holder);
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                }
            });
            ijkMediaPlayer.prepareAsync();
            ijkMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        ijkMediaPlayer.setKeepInBackground(false);
    }

    @OnClick({R.id.right_play, R.id.bottom_play, R.id.bottom_fullscreen,
            R.id.video_view, R.id.user_pic, R.id.bottom_love})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_play:
                ControlVideo();
                break;
            case R.id.bottom_play:
                ControlVideo();
                break;
            case R.id.bottom_fullscreen:
                // TODO: 2019/7/29 全屏切换暂 *无需处理*

                break;
            case R.id.video_view:
                if (flag == 0) {
                    startBottomShowAnim();
                    flag = 1;
                } else {
                    startBottomHideAnim();
                    flag = 0;
                }
                break;
            case R.id.user_pic:
                Intent intent = new Intent(this,UserInfoDetailsActivity.class);
                intent.putExtra(EXTRA_USER_NAME,name);
                intent.putExtra(EXTRA_MID,mid);
                intent.putExtra(EXTRA_AVATAR_URL,face);
                startActivity(intent);
                ControlVideo();
                mRightPlayBtn.setVisibility(View.VISIBLE);
                break;
            case R.id.bottom_love:
                mLoveLikeLayout.addLove();
                break;
        }
    }

    private void ControlVideo() {
        if (isPlay) {
            ijkMediaPlayer.pause();
            isPlay = false;
            mRightPlayBtn.setImageResource(R.drawable.ic_tv_play);
            mBottomPlayBtn.setImageResource(R.drawable.ic_portrait_play);
        } else {
            ijkMediaPlayer.start();
            isPlay = true;
            mRightPlayBtn.setImageResource(R.drawable.ic_tv_stop);
            mBottomPlayBtn.setImageResource(R.drawable.ic_portrait_stop);
        }
    }

    private void startBottomShowAnim() {
        mBottomLayout.setVisibility(View.VISIBLE);
        mRightPlayBtn.setVisibility(View.VISIBLE);
    }

    private void startBottomHideAnim() {
        mBottomLayout.setVisibility(View.GONE);
        mRightPlayBtn.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkMediaPlayer.release();
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

    public static void launch(Activity activity, int cid, String title, int online, String face, String name, int mid) {
        Intent mIntent = new Intent(activity, LivePlayerActivity.class);
        mIntent.putExtra(Constant.EXTRA_CID, cid);
        mIntent.putExtra(Constant.EXTRA_TITLE, title);
        mIntent.putExtra(Constant.EXTRA_ONLINE, online);
        mIntent.putExtra(Constant.EXTRA_FACE, face);
        mIntent.putExtra(Constant.EXTRA_NAME, name);
        mIntent.putExtra(Constant.EXTRA_MID, mid);
        activity.startActivity(mIntent);
    }
}
