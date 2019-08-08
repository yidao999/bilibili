package com.example.bilibilidemo.mvp.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.bilibilidemo.app.utils.ShareUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.bilibilidemo.di.component.DaggerAppIntroduceComponent;
import com.example.bilibilidemo.mvp.contract.AppIntroduceContract;
import com.example.bilibilidemo.mvp.presenter.AppIntroducePresenter;

import com.example.bilibilidemo.R;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/04/2019 13:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AppIntroduceActivity extends BaseActivity<AppIntroducePresenter> implements AppIntroduceContract.View {

    @BindView(R.id.toolbars)
    Toolbar mToolbar;
    @BindView(R.id.tv_version)
    TextView mVersion;
    @BindView(R.id.tv_network_diagnosis)
    TextView mTvNetworkDiagnosis;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAppIntroduceComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_app_introduce; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar();
        initVersion();
    }

    private void initVersion() {
        mVersion.setText("v" + getVersion());
    }

    private String getVersion() {
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return getString(R.string.about_version);
        }
    }

    private void initToolBar() {
        mToolbar.setTitle("关于");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.tv_share_app, R.id.tv_feedback})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_share_app:
                //分享app
                ShareUtil.shareLink(getString(R.string.github_url),
                        getString(R.string.share_title), AppIntroduceActivity.this);
                break;
            case R.id.tv_feedback:
                //意见反馈
                new AlertDialog.Builder(AppIntroduceActivity.this)
                        .setTitle(R.string.feedback_titlle)
                        .setMessage(R.string.feedback_message)
                        .setPositiveButton("确定", (dialog, which) -> dialog.dismiss())
                        .show();
                break;
        }
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
