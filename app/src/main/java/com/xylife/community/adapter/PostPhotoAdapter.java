package com.xylife.community.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xylife.community.R;
import com.xylife.community.utils.Bimp;

/**
 * Created by SK on 2016/1/21.
 */
public class PostPhotoAdapter extends BaseAdapter {

    private Context context;

    private final static int PHOTOS_SIZE = 3;  // 后期会改为8

    public PostPhotoAdapter(Context context) {
        this.context = context;
    }

    public void update() {
        loading();
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void loading() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (Bimp.max == Bimp.tempSelectBitmap.size()) {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                        break;
                    } else {
                        Bimp.max += 1;
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }
            }
        }).start();
    }

    @Override
    public int getCount() {
        if(Bimp.tempSelectBitmap.size() == 8){
            return 8;
        }
        return Bimp.tempSelectBitmap.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.picker_item_photo, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.post_image);
            holder.isSelected = (ImageView) convertView.findViewById(R.id.isSelected);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == Bimp.tempSelectBitmap.size()) {
            if (position == PHOTOS_SIZE) {
                holder.image.setVisibility(View.INVISIBLE);
            } else {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.btn_add_big));
            }
            holder.isSelected.setVisibility(View.INVISIBLE);
        } else {
            Glide.with(context)
                    .load(Bimp.tempSelectBitmap.get(position).getImagePath())
                    .centerCrop()
                    .thumbnail(0.1f)
                    .placeholder(R.mipmap.ic_photo_default)
                    .error(R.mipmap.ic_photo_default)
                    .into(holder.image);
            holder.isSelected.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
        public ImageView isSelected;
    }

}
