package com.android.framewok.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.framewok.AppBaseContext;
import com.android.framewok.interf.BaseFragmentInterface;


public class BaseFragment extends Fragment implements View.OnClickListener,BaseFragmentInterface {
    public final String TAG= this.getClass().getName();
    protected LayoutInflater mInflater;

    public AppBaseContext getApplication() {
        return (AppBaseContext) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mInflater = inflater;
        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 打开一个Activity 默认 不关闭当前activity
     * @param clz
     */
    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

    public void gotoActivity(Class<?> clz,boolean isCloseCurrentActivity) {
        gotoActivity(clz, isCloseCurrentActivity, null);
    }

    public  void gotoActivity(Class<?> clz,boolean isCloseCurrentActivity,Bundle ex) {
        Intent intent=new Intent(getActivity(), clz);
        if(ex!=null)
            intent.putExtras(ex);
        startActivity(intent);
        if (isCloseCurrentActivity) {
            getActivity().finish();
        }
    }
}
