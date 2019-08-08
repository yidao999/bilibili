package com.example.bilibilidemo.mvp.ui.activity;

import android.app.Activity;
import android.arch.lifecycle.LifecycleObserver;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bilibilidemo.app.utils.KeyBoardUtil;
import com.example.bilibilidemo.app.utils.StatusBarUtil;
import com.example.bilibilidemo.mvp.model.Constant;
import com.example.bilibilidemo.mvp.model.entity.SearchArchiveInfo;
import com.example.bilibilidemo.mvp.ui.adapter.SearchTabAdapter;
import com.example.bilibilidemo.mvp.ui.fragment.ArchiveResultsFragment;
import com.example.bilibilidemo.mvp.ui.widget.NoScrollViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.lifecycle.Lifecycleable;
import com.jess.arms.utils.ArmsUtils;

import com.example.bilibilidemo.di.component.DaggerTotalStationSearchComponent;
import com.example.bilibilidemo.mvp.contract.TotalStationSearchContract;
import com.example.bilibilidemo.mvp.presenter.TotalStationSearchPresenter;

import com.example.bilibilidemo.R;
import com.jess.arms.utils.RxLifecycleUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 12:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class TotalStationSearchActivity extends BaseActivity<TotalStationSearchPresenter> implements TotalStationSearchContract.View {

    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.iv_search_loading)
    ImageView mLoadingView;
    @BindView(R.id.search_img)
    ImageView mSearchBtn;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.search_text_clear)
    ImageView mSearchTextClear;
    @BindView(R.id.search_layout)
    LinearLayout mSearchLayout;

    private String content;
    private AnimationDrawable mAnimationDrawable;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private List<SearchArchiveInfo.DataBean.NavBean> navs = new ArrayList<>();


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTotalStationSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_total_station_search; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolbar();
        initIntent();
        initLoad();
        netLoad();
        search();
        setUpEditText();
    }

    private void setUpEditText() {
        RxTextView.textChanges(mSearchEdit)
                .map(CharSequence::toString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s)) {
                        mSearchTextClear.setVisibility(View.VISIBLE);
                    } else {
                        mSearchTextClear.setVisibility(View.GONE);
                    }
                });
        RxView.clicks(mSearchTextClear)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> mSearchEdit.setText(""));

        RxTextView.editorActions(mSearchEdit)
                .filter(integer -> !TextUtils.isEmpty(mSearchEdit.getText().toString().trim()))
                .filter(integer -> integer == EditorInfo.IME_ACTION_SEARCH)
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        return Observable.just(mSearchEdit.getText().toString().trim());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    KeyBoardUtil.closeKeybord(mSearchEdit, TotalStationSearchActivity.this);
                    showSearchAnim();
                    clearData();
                    content = s;
                    getSearchData();
                });
    }

    private void netLoad() {
        getSearchData();
    }

    private void getSearchData() {
        int page = 1;
        int pageSize = 10;
        mPresenter.loadData(content, page, pageSize);
    }

    private void initLoad() {
        mLoadingView.setImageResource(R.drawable.anim_search_loading);
        mAnimationDrawable = (AnimationDrawable) mLoadingView.getDrawable();
        mSearchEdit.clearFocus();
        mSearchEdit.setText(content);
    }

    private void showSearchAnim() {
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            content = intent.getStringExtra(Constant.EXTRA_CONTENT);
        }
    }

    private void initToolbar() {
        //设置6.0以上StatusBar字体颜色
        StatusBarUtil.from(this).setLightStatusBar(true).process();
    }

    @Override
    public void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mSearchLayout.setVisibility(View.GONE);
        mAnimationDrawable.start();
    }

    @Override
    public void hideLoading() {
        mLoadingView.setVisibility(View.GONE);
        mSearchLayout.setVisibility(View.VISIBLE);
        mAnimationDrawable.stop();
    }

    @Override
    public void emptyLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mSearchLayout.setVisibility(View.GONE);
        mLoadingView.setImageResource(R.drawable.search_failed);
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
    public void finishTask(List<SearchArchiveInfo.DataBean.NavBean> navss) {
        this.navs.addAll(navss);
        hideLoading();
        titles.add("综合");
        titles.add(navs.get(0).getName() + "(" + checkNumResults(navs.get(0).getTotal()) + ")");
//        titles.add(navs.get(1).getName() + "(" + checkNumResults(navs.get(1).getTotal()) + ")");
//        titles.add(navs.get(2).getName() + "(" + checkNumResults(navs.get(2).getTotal()) + ")");

        // TODO: 2019/8/3   暂不处理
        ArchiveResultsFragment archiveResultsFragment = ArchiveResultsFragment.newInstance(content);
//        BangumiResultsFragment bangumiResultsFragment = BangumiResultsFragment.newInstance(content);
//        UpperResultsFragment upperResultsFragment = UpperResultsFragment.newInstance(content);
//        MovieResultsFragment movieResultsFragment = MovieResultsFragment.newInstance(content);

        fragments.add(archiveResultsFragment);
//        fragments.add(bangumiResultsFragment);
//        fragments.add(upperResultsFragment);
//        fragments.add(movieResultsFragment);

        SearchTabAdapter mAdapter = new SearchTabAdapter(getSupportFragmentManager(), titles, fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(titles.size());
        mSlidingTabLayout.setViewPager(mViewPager);
//        measureTabLayoutTextWidth(0);
        mSlidingTabLayout.setCurrentTab(0);
        mAdapter.notifyDataSetChanged();
        mSlidingTabLayout.notifyDataSetChanged();
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                measureTabLayoutTextWidth(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
    }

    public String checkNumResults(int numResult) {
        return numResult > 100 ? "99+" : String.valueOf(numResult);
    }

    public static void launch(Activity activity, String str) {
        Intent mIntent = new Intent(activity, TotalStationSearchActivity.class);
        mIntent.putExtra(Constant.EXTRA_CONTENT, str);
        activity.startActivity(mIntent);
    }

    @OnClick(R.id.search_back)
    void onBack() {
        onBackPressed();
    }

    private void search() {
        RxView.clicks(mSearchBtn)
                .throttleFirst(2, TimeUnit.SECONDS)
                .map(aVoid -> mSearchEdit.getText().toString().trim())
                .filter(s -> !TextUtils.isEmpty(s))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    KeyBoardUtil.closeKeybord(mSearchEdit, TotalStationSearchActivity.this);
                    showSearchAnim();
                    clearData();
                    content = s;
                    getSearchData();
                });
    }

    private void clearData() {
        navs.clear();
        titles.clear();
        fragments.clear();
    }
}
