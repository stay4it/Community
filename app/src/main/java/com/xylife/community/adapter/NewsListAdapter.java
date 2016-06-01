package com.xylife.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.framewok.base.ListBaseAdapter;
import com.xylife.community.R;
import com.xylife.community.adapter.viewholder.NewsViewHolder;
import com.xylife.community.bean.News;
import com.xylife.community.ui.PartyDetailActivity;

public class NewsListAdapter extends ListBaseAdapter<News> {

    private Context mContext;

    public NewsListAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final NewsViewHolder viewHolder = (NewsViewHolder) holder;
        final News news = this.mDataList.get(position);

        viewHolder.titleText.setText(news.famous_saying);
        viewHolder.speakerText.setText(news.famous_name);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PartyDetailActivity.class);
                mContext.startActivity(intent);
            }
        });



    }
}
