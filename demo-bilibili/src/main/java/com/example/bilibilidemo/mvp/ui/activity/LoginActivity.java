package com.example.bilibilidemo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.bilibilidemo.mvp.model.Constant;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.bilibilidemo.di.component.DaggerLoginComponent;
import com.example.bilibilidemo.mvp.contract.LoginContract;
import com.example.bilibilidemo.mvp.presenter.LoginPresenter;

import com.example.bilibilidemo.R;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 15:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_icon_left)
    ImageView mLeftLogo;
    @BindView(R.id.iv_icon_right)
    ImageView mRightLogo;
    @BindView(R.id.delete_username)
    ImageView mDeleteUserName;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initViews();
        initToolbar();
    }

    @OnClick({R.id.btn_login, R.id.delete_username})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //登录
                boolean isNetConnected = DeviceUtils.hasInternet(this);
                if (!isNetConnected) {
                    ArmsUtils.makeText(this, "当前网络不可用,请检查网络设置");
                    return;
                }
                login();
                break;
            case R.id.delete_username:
                // 清空用户名以及密码
                et_username.setText("");
                et_password.setText("");
                mDeleteUserName.setVisibility(View.GONE);
                et_username.setFocusable(true);
                et_username.setFocusableInTouchMode(true);
                et_username.requestFocus();
                break;
        }
    }

    private void login() {
        String name = et_username.getText().toString();
        String password = et_password.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ArmsUtils.makeText(this, "用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ArmsUtils.makeText(this, "密码不能为空");
            return;
        }
        DataHelper.setIntergerSF(this, Constant.KEY, 1);//1为true,默认-1为false
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_cancle);
        mToolbar.setTitle("登录");
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initViews() {
        et_username.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && et_username.getText().length() > 0) {
                mDeleteUserName.setVisibility(View.VISIBLE);
            } else {
                mDeleteUserName.setVisibility(View.GONE);
            }
            mLeftLogo.setImageResource(R.drawable.ic_22);
            mRightLogo.setImageResource(R.drawable.ic_33);
        });
        et_password.setOnFocusChangeListener((v, hasFocus) -> {
            mLeftLogo.setImageResource(R.drawable.ic_22_hide);
            mRightLogo.setImageResource(R.drawable.ic_33_hide);
        });
        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 如果用户名清空了 清空密码 清空记住密码选项
                et_password.setText("");
                if (s.length() > 0) {
                    // 如果用户名有内容时候 显示删除按钮
                    mDeleteUserName.setVisibility(View.VISIBLE);
                } else {
                    // 如果用户名有内容时候 显示删除按钮
                    mDeleteUserName.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
