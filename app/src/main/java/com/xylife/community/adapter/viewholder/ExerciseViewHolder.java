package com.xylife.community.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xylife.community.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.logo_image) public ImageView logoImage;
    @BindView(R.id.certification_text) public TextView certificationText;
    @BindView(R.id.company_name_text) public TextView companyNameText;
    @BindView(R.id.title_text) public TextView titleText;
    @BindView(R.id.time_text) public TextView timeText;
    @BindView(R.id.location_text) public TextView locationText;
    @BindView(R.id.enrol_btn) public Button enrolBtn;


    public ExerciseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
