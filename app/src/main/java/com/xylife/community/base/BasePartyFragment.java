package com.xylife.community.base;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.framewok.base.ListBaseAdapter;
import com.android.framewok.util.TLog;
import com.xylife.community.R;
import com.xylife.community.adapter.PartyListAdapter;
import com.xylife.community.bean.Exercise;
import com.xylife.community.bean.Response;

import java.util.List;

import rx.Observable;

/**
 * Created by SK on 2016-05-25.
 */
public class BasePartyFragment extends BaseListFragment<Exercise,List<Exercise>> {

    public static BasePartyFragment newInstance(String title) {
        BasePartyFragment fragment = new BasePartyFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_party;
    }

    @Override
    protected ListBaseAdapter getListAdapter() {
        return new PartyListAdapter();
    }

    @Override
    protected void initLayoutManager() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected Observable<Response<List<Exercise>>> sendRequestData() {
        TLog.log("sendRequestData mCurrentPage = " +  mCurrentPage);
        //return APIWrapper.getInstance().queryLookUp("人才",mCurrentPage);
        return null;
    }


}
