package com.xylife.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.framewok.base.ListBaseAdapter;
import com.xylife.community.R;
import com.xylife.community.adapter.viewholder.ExerciseViewHolder;
import com.xylife.community.bean.Exercise;
import com.xylife.community.ui.PartyDetailActivity;

public class ExerciseListAdapter extends ListBaseAdapter<Exercise> {

    private Context mContext;
    private int lastPosition = -1;
    private int type = -1;            // 0 首页；1 日程

    public ExerciseListAdapter(int type) {
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ExerciseViewHolder viewHolder = (ExerciseViewHolder) holder;
        final Exercise exercise = this.mDataList.get(position);

        viewHolder.companyNameText.setText("富电科技");
        viewHolder.certificationText.setText("官方");

        if (type == 1) {
            viewHolder.enrolBtn.setVisibility(View.GONE);
        }

        viewHolder.titleText.setText("哪种鞋最适合运动，一起来聊聊呗");
        viewHolder.timeText.setText("05/30 12:00 - 06/30 12:00");
        viewHolder.locationText.setText("768创意产业园");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PartyDetailActivity.class);
                mContext.startActivity(intent);
            }
        });

    }

}
