package com.xylife.community.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.framewok.base.ListBaseAdapter;
import com.cundong.recyclerview.CommonHeader;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.xylife.community.R;
import com.xylife.community.adapter.ExerciseListAdapter;
import com.xylife.community.api.APIWrapper;
import com.xylife.community.base.BaseListFragment;
import com.xylife.community.bean.Exercise;
import com.xylife.community.bean.Response;
import com.xylife.community.view.HomeSlideShowView;

import java.util.List;

import rx.Observable;


public class HomeFragment extends BaseListFragment<Exercise,List<Exercise>> {

    private HomeSlideShowView slideShowView;
    @Override
    public void initView(View view) {
        super.initView(view);

        headerView = new CommonHeader(getActivity(), R.layout.layout_home_header);
        RecyclerViewUtils.setHeaderView(mRecyclerView, headerView);

        slideShowView = (HomeSlideShowView) headerView.findViewById(R.id.banner_view);
    }

    @Override
    protected ListBaseAdapter<Exercise> getListAdapter() {
        return new ExerciseListAdapter(0);
    }


    @Override
    protected void onRefresh() {
        if (null != slideShowView){
            slideShowView.refreshView();
        }

    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    protected Observable<Response<List<Exercise>>> sendRequestData() {
        return APIWrapper.getInstance().queryLookUp("人才",mCurrentPage);
    }

    @Override
    protected void initLayoutManager() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


}
