package com.xylife.community.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xylife.community.R;
import com.xylife.community.adapter.PostPhotoAdapter;
import com.xylife.community.base.BaseActivity;
import com.xylife.community.bean.ImageItem;
import com.xylife.community.utils.Bimp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by SK on 2016-05-30.
 */
public class PostActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    @BindView(R.id.photo_grid_view)
    GridView gridView;

    private ArrayList<String> selectedPhotos = new ArrayList<>();

    private PostPhotoAdapter photoAdapter;

    private boolean isSelected = false;

    public final static int REQUEST_CODE = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post;
    }

    @Override
    public void initView() {
        mTitleText.setText("找豆伴");


        photoAdapter = new PostPhotoAdapter(this);
        photoAdapter.update();

        gridView.setAdapter(photoAdapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void initData() {

    }

    public void previewPhoto(Intent intent) {
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                selectedPhotos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);

                for (String photoPath: selectedPhotos) {
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 8;
                    Bitmap bitmap = BitmapFactory.decodeFile(photoPath, opts);

                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bitmap);
                    takePhoto.setImagePath(photoPath);
                    Bimp.tempSelectBitmap.add(takePhoto);

                    photoAdapter.notifyDataSetChanged();
                    // 进入此处说明有照片，且默认为选中状态
                    isSelected = true;
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PostPhotoAdapter.ViewHolder holder = (PostPhotoAdapter.ViewHolder) view.getTag();

        if (position == Bimp.tempSelectBitmap.size()) {
            if (position == 1) return;
            PhotoPickerIntent intent = new PhotoPickerIntent(this);
            intent.setPhotoCount(1);
            intent.setShowCamera(true);
            intent.setShowGif(true);
            startActivityForResult(intent, REQUEST_CODE);
        } else {

            if (isSelected) {
                holder.isSelected.setVisibility(View.GONE);
                isSelected = false;

                //switchMasterTextView(isSelected);
                //switchPrivateTextView(isSelected);
            } else {
                holder.isSelected.setVisibility(View.VISIBLE);
                isSelected = true;
            }

            /*Intent intent = new Intent(this, PhotoPagerActivity.class);
            intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, position);
            intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, photos);
            startActivity(intent);*/
        }
    }
}
