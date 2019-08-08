package com.example.bilibilidemo.mvp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bilibilidemo.app.utils.EndlessRecyclerOnScrollListener;
import com.example.bilibilidemo.app.utils.HeaderViewRecyclerAdapter;
import com.example.bilibilidemo.mvp.model.Constant;
import com.example.bilibilidemo.mvp.model.entity.SearchArchiveInfo;
import com.example.bilibilidemo.mvp.ui.activity.TotalStationSearchActivity;
import com.example.bilibilidemo.mvp.ui.adapter.ArchiveResultsAdapter;
import com.example.bilibilidemo.mvp.ui.widget.CircleProgressView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.bilibilidemo.di.component.DaggerArchiveResultsComponent;
import com.example.bilibilidemo.mvp.contract.ArchiveResultsContract;
import com.example.bilibilidemo.mvp.presenter.ArchiveResultsPresenter;

import com.example.bilibilidemo.R;

import org.jetbrains.annotations.Contract;

import java.time.chrono.MinguoDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static android.view.View.VISIBLE;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 15:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ArchiveResultsFragment extends BaseFragment<ArchiveResultsPresenter> implements ArchiveResultsContract.View {

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    ImageView mEmptyView;
    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;


    @Inject
    ArchiveResultsAdapter mAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    List<SearchArchiveInfo.DataBean.ItemsBean.ArchiveBean> archiveBean;

    private String content;
    private int pageNum = 1;
    private int pageSize = 10;
    private View loadMoreView;
    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;

    public static ArchiveResultsFragment newInstance(String content) {
        ArchiveResultsFragment fragment = new ArchiveResultsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXTRA_CONTENT, content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerArchiveResultsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_archive_results, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        iniIntent();
        initRecyclerView();
        loadData(content);
    }

    private void loadData(String content) {
        mPresenter.loadData(content, pageNum, pageSize);
    }

    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        createHeadView();
        createLoadMoreView();
        mRecyclerView.setAdapter(mHeaderViewRecyclerAdapter);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int i) {
                pageNum++;
                loadData(content);
                loadMoreView.setVisibility(VISIBLE);
            }
        });
        // TODO: 2019/8/3  点击item跳转播放视频 等待处理
//        mAdapter.setOnItemClickListener((position, holder) -> {
//            SearchArchiveInfo.DataBean.ItemsBean.ArchiveBean archiveBean = archives.get(position);
//            VideoDetailsActivity.launch(getActivity(), Integer.valueOf(archiveBean.getParam()),
//                    archiveBean.getCover());
//        });
    }

    private void createLoadMoreView() {
        loadMoreView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_load_more, mRecyclerView, false);
        mHeaderViewRecyclerAdapter.addFooterView(loadMoreView);
        loadMoreView.setVisibility(View.GONE);
    }

    private void iniIntent() {
        content = getArguments().getString(Constant.EXTRA_CONTENT);
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        int visibility = loadMoreView.getVisibility();
        if(visibility == View.GONE){
            mCircleProgressView.spin();
        }
    }

    @Override
    public void hideLoading() {
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

    }

    @Override
    public void finishTask(List<SearchArchiveInfo.DataBean.ItemsBean.ArchiveBean> archiveBean) {
        this.archiveBean.addAll(archiveBean);
        if (archiveBean != null) {
            mAdapter.notifyDataSetChanged();
            mHeaderViewRecyclerAdapter.notifyDataSetChanged();
            if (archiveBean.size() == 0) {
                showEmptyView();
            } else {
                hideEmptyView();
            }
        }
        loadMoreView.setVisibility(View.GONE);
        if (pageNum * pageSize - pageSize - 1 > 0) {
            mHeaderViewRecyclerAdapter.notifyItemRangeChanged(pageNum * pageSize - pageSize - 1, pageSize);
        } else {
            mHeaderViewRecyclerAdapter.notifyDataSetChanged();
        }
    }

    private void createHeadView() {
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_search_archive_head_view, mRecyclerView, false);
        mHeaderViewRecyclerAdapter.addHeaderView(headView);
    }

    public void showEmptyView() {
        mEmptyView.setVisibility(VISIBLE);
    }

    @Override
    public void removeFootView() {
        loadMoreView.setVisibility(View.GONE);
        mHeaderViewRecyclerAdapter.removeFootView();
    }

    public void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
    }
}
