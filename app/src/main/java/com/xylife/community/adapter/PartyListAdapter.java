package com.xylife.community.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.framewok.base.ListBaseAdapter;
import com.xylife.community.R;
import com.xylife.community.adapter.viewholder.NewsViewHolder;
import com.xylife.community.adapter.viewholder.PartyViewHolder;
import com.xylife.community.bean.News;
import com.xylife.community.bean.PartyEntity;

import java.util.Collection;

/**
 * Created by SK on 2016-05-18.
 */
public class PartyListAdapter extends ListBaseAdapter<News> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_party, parent, false);
        return new PartyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

}
