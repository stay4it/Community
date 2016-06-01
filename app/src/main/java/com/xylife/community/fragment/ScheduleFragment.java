package com.xylife.community.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.framewok.base.ListBaseAdapter;
import com.android.framewok.util.TLog;
import com.xylife.community.adapter.ExerciseListAdapter;
import com.xylife.community.api.APIWrapper;
import com.xylife.community.base.BaseListFragment;
import com.xylife.community.bean.Exercise;
import com.xylife.community.bean.Response;

import java.util.List;

import rx.Observable;

/**
 * Created by SK on 2016-05-30.
 *
 */
public class ScheduleFragment extends BaseListFragment<Exercise, List<Exercise>> {


    @Override
    public void initView(View view) {
        super.initView(view);

    }

    @Override
    protected ListBaseAdapter<Exercise> getListAdapter() {
        return new ExerciseListAdapter(1);
    }

    @Override
    protected void initLayoutManager() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    protected Observable<Response<List<Exercise>>> sendRequestData() {
        TLog.log("sendRequestData mCurrentPage = " +  mCurrentPage);
        return APIWrapper.getInstance().queryLookUp("人才",mCurrentPage);
    }

}
