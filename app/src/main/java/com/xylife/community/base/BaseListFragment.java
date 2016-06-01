package com.xylife.community.base;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

import com.android.framewok.base.BaseFragment;
import com.android.framewok.base.ListBaseAdapter;
import com.android.framewok.bean.Entity;
import com.android.framewok.util.TLog;
import com.cundong.recyclerview.CommonHeader;
import com.cundong.recyclerview.CustRecyclerView;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LoadingFooter;
import com.cundong.recyclerview.RecyclerOnScrollListener;
import com.cundong.recyclerview.RecyclerViewStateUtils;
import com.xylife.community.R;
import com.xylife.community.bean.Response;
import com.xylife.community.exception.ApiException;
import com.xylife.community.ui.error.ErrorLayout;
import com.xylife.community.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public abstract class BaseListFragment<T extends Entity,D extends List<T>> extends BaseFragment {
    /**每一页展示多少条数据*/
    protected int mCurrentPage = 0;
    protected int totalPage = 0;

    @BindView(R.id.recycler_view)
    protected CustRecyclerView mRecyclerView;
    @BindView(R.id.error_layout)
    ErrorLayout mErrorLayout;

    @BindView(R.id.top_btn)
    protected Button toTopBtn;

    protected ListBaseAdapter<T> mListAdapter;
    protected HeaderAndFooterRecyclerViewAdapter mRecyclerViewAdapter;

    protected boolean isRequestInProcess = false;
    protected boolean mIsStart = false;

    protected CommonHeader headerView;

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, getPageSize(), LoadingFooter.State.Loading, null);
            requestData();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pull_refresh_recyclerview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        onRefresh();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initView(View view) {
        if (mListAdapter != null) {
            mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
        } else {
            mListAdapter = getListAdapter();

            if (requestDataIfViewCreated()) {
                mErrorLayout.setErrorType(ErrorLayout.NETWORK_LOADING);
                TLog.log("requestDataIfViewCreated  requestData");
                requestData();
            } else {
                mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
            }

        }

        AnimationAdapter adapter = new ScaleInAnimationAdapter(mListAdapter);
        adapter.setFirstOnly(false);
        adapter.setDuration(500);
        adapter.setInterpolator(new OvershootInterpolator(.5f));

        mRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(getActivity(), adapter);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        initLayoutManager();

        mRecyclerView.setLoadingListener(new CustRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if (isRequestInProcess) {
                    return;
                }
                // 设置顶部正在刷新
                setSwipeRefreshLoadingState();
                mCurrentPage = 0;
                requestData();
            }
        });

        //mRecyclerView.setRefreshing(true);

        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCurrentPage = 0;
                mErrorLayout.setErrorType(ErrorLayout.NETWORK_LOADING);
                requestData();
            }
        });

        toTopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.scrollToPosition(0);
                toTopBtn.setVisibility(View.GONE);
            }
        });
    }

    protected boolean requestDataIfViewCreated() {
        return true;
    }

    protected RecyclerOnScrollListener mOnScrollListener = new RecyclerOnScrollListener() {

        @Override
        public void onScrolled(int dx, int dy) {
            super.onScrolled(dx, dy);

            if(null != headerView) {
                if (dy == 0 || dy < headerView.getHeight()) {
                    toTopBtn.setVisibility(View.GONE);
                }
            }


        }

        @Override
        public void onScrollUp() {
            // 滑动时隐藏float button
            if (toTopBtn.getVisibility() == View.VISIBLE) {
                toTopBtn.setVisibility(View.GONE);
                animate(toTopBtn, R.anim.floating_action_button_hide);
            }
        }

        @Override
        public void onScrollDown() {
            if (toTopBtn.getVisibility() != View.VISIBLE) {
                toTopBtn.setVisibility(View.VISIBLE);
                animate(toTopBtn, R.anim.floating_action_button_show);
            }
        }

        @Override
        public void onBottom() {
            TLog.log("onBottom mCurrentPage = " + mCurrentPage);
            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mRecyclerView);
            if(state == LoadingFooter.State.Loading) {
                return;
            }

            if (mCurrentPage < totalPage) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, getPageSize(), LoadingFooter.State.Loading, null);
                requestData();
            } else {
                //the end
                /*if (totalPage > 1){
                    RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, getPageSize(), LoadingFooter.State.TheEnd, null);
                }else {
                    RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, mListAdapter.getItemCount(), LoadingFooter.State.TheEnd, null);

                }*/
                RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, getPageSize(), LoadingFooter.State.TheEnd, null);
            }
        }


    };

    private void animate(View view, int anim) {
        if (anim != 0) {
            Animation a = AnimationUtils.loadAnimation(view.getContext(), anim);
            view.startAnimation(a);
        }
    }

    /** 设置顶部正在加载的状态 */
    protected void setSwipeRefreshLoadingState() {
        TLog.log("setSwipeRefreshLoadingState ");
    }

    /** 设置顶部加载完毕的状态 */
    protected void setSwipeRefreshLoadedState() {
        TLog.log("setSwipeRefreshLoadedState ");
        mRecyclerView.refreshComplete();
    }

    // 完成刷新
    protected void executeOnLoadFinish() {
        setSwipeRefreshLoadedState();
        isRequestInProcess = false;
        mIsStart = false;
    }

    protected abstract ListBaseAdapter<T> getListAdapter();

    Observable<Response<D>> mObservable;
    Subscription mSubscription;
    protected void requestData() {
        mCurrentPage++;
        if(mSubscription !=null){
            TLog.log("requestData mSubscription.isUnsubscribed() = " + mSubscription.isUnsubscribed());
        }
        mObservable = sendRequestData();
        if(null != mObservable){
            toSubscribe(mObservable);
        }
        isRequestInProcess = true;

        if(mCurrentPage == 1){
            onRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        TLog.log("onDestroyView");
        if(mSubscription !=null){
            TLog.log("onDestroyView mSubscription.isUnsubscribed() = " + mSubscription.isUnsubscribed());
        }
    }

    private void toSubscribe(Observable<Response<D>> observable) {
        mSubscription = observable.subscribeOn(Schedulers.io())
                .map(new Func1<Response<D>,D>() {

                    @Override
                    public D call(Response<D> response) {
                        if(response == null){
                            throw new ApiException(100);
                        }
                        totalPage = response.total;
                        return response.result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);

    }

    protected Observable<Response<D>> sendRequestData() {
        return null;
    }


    protected List<T> parseList(Response<T> response) {
        return null;
    }

    protected abstract void initLayoutManager();

    protected void onRefresh() {}

    protected int getPageSize() {
        return Constant.PAGE_SIZE;
    }
    protected int getTotalPage(byte[] data) {
        return 100;
    }

    protected void executeOnLoadDataSuccess(List<T> data) {
        TLog.log("executeOnLoadDataSuccess " + data.size());
        if (data == null) {
            data = new ArrayList<>();
        }

        mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);

        if (mCurrentPage == 1) {
            mListAdapter.setDataList(data);
        } else {
            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
            mListAdapter.addAll(data);
        }
    }

    protected void executeOnLoadDataError(String error) {
        executeOnLoadFinish();
        if (mCurrentPage == 1) {
            mErrorLayout.setErrorType(ErrorLayout.NETWORK_ERROR);
        } else {

            //在无网络时，滚动到底部时，mCurrentPage先自加了，然而在失败时却
            //没有减回来，如果刻意在无网络的情况下上拉，可以出现漏页问题
            //find by TopJohn
            mCurrentPage--;

            mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
            RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, getPageSize(), LoadingFooter.State.NetWorkError, mFooterClick);
            mListAdapter.notifyDataSetChanged();
        }
    }

    protected void onRefreshNetworkSuccess() {}

    protected Observer<D> mObserver = new Observer<D>() {
        @Override
        public void onCompleted() {
            executeOnLoadFinish();
        }

        @Override
        public void onError(Throwable e) {
            TLog.error("onError " + e.toString());
            executeOnLoadDataError(null);
        }

        @Override
        public void onNext(D d) {
            TLog.log("onNext " );
            List<T> list = d;
            TLog.log("entity " + list.size());
            executeOnLoadDataSuccess(list);

            TLog.log("onSuccess totalPage " + totalPage);
        }
    };

}
