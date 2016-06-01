package com.xylife.community.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.xylife.community.R;
import com.xylife.community.api.APIWrapper;
import com.xylife.community.bean.Advertisement;
import com.xylife.community.bean.JavaResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ViewPager实现的轮播图广告自定义视图，如京东首页的广告轮播图效果； 既支持自动轮播页面也支持手势滑动切换页面
 */

public class HomeSlideShowView extends FrameLayout {

    // 轮播图图片数量
    private final static int IMAGE_COUNT = 4;
    // 自动轮播的时间间隔
    private final static int TIME_INTERVAL = 5;
    // 自动轮播启用开关
    private final static boolean isAutoPlay = true;

    // 自定义轮播图的资源
    /*private String[] imageUrls;*/
    // 放轮播图片的ImageView 的list
    private List<ImageView> imageViewsList;
    // 放圆点的View的list
    private List<View> dotViewsList;

    private ViewPager viewPager;
    // 当前轮播页
    private int currentItem = 0;
    // 定时任务
    private ScheduledExecutorService scheduledExecutorService;

    private Context context;
    private LinearLayout dotLayout;
    private List<Advertisement> adverList;
    private MyPagerAdapter mAdapter;

    // Handler
    private static final int UPDATE_VIEWPAGER_ITEM = 1001;
    private static final int FRESH_BANNER_DATA = 1002;
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_VIEWPAGER_ITEM:
                    viewPager.setCurrentItem(currentItem);
                    break;
                case FRESH_BANNER_DATA:
                    List<Advertisement> list = (List<Advertisement>) msg.obj;
                    adverList.addAll(list);
                    initUI(context);
                    if (isAutoPlay) {
                        startPlay();
                    }

                    isRefreshing = false;
                    break;

                default:
                    break;
            }

        }

        ;

    };

    private String TAG = "HomeSlideShowView";

    public HomeSlideShowView(Context context) {
        this(context, null);
    }

    public HomeSlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeSlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;

        initView();
    }

    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this,
                true);
        dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
        adverList = new ArrayList<>();

        imageViewsList = new ArrayList<>();
        dotViewsList = new ArrayList<>();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);

        mAdapter = new MyPagerAdapter(adverList);
        viewPager.setAdapter(mAdapter);

        viewPager.setOnPageChangeListener(new MyPageChangeListener());

    }

    private boolean isRefreshing = false;

    public void refreshView() {
        if (!isRefreshing) {
            Log.d(TAG, "refresh bannerView");
            isRefreshing = true;
            if (adverList.size() != 0) {
                stopPlay();
                adverList.clear();
                imageViewsList.clear();
                dotViewsList.clear();
                mAdapter.notifyDataSetChanged();
            }
            initData();

        }

    }

    /**
     * 开始轮播图切换
     */
    public void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), (long) 2, 4,
                TimeUnit.SECONDS);
    }

    /**
     * 停止轮播图切换
     */
    private void stopPlay() {
        scheduledExecutorService.shutdown();
    }

    /**
     * 初始化相关Data
     */
    private void initData() {

        processData();
    }

    /**
     * 初始化Views等UI
     */
    private void initUI(Context context) {
        if (adverList == null || adverList.size() == 0)
            return;

        dotLayout.removeAllViews();

        // 热点个数与图片特殊相等
        for (int i = 0; i < adverList.size(); i++) {
            ImageView view = new ImageView(context);
            if (i == 0)// 给一个默认图
                view.setBackgroundResource(R.drawable.ic_product_default);
            view.setScaleType(ScaleType.FIT_XY);
            imageViewsList.add(view);

            ImageView dotView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = 4;
            params.rightMargin = 4;
            dotLayout.addView(dotView, params);
            dotViewsList.add(dotView);
        }


        mAdapter.notifyDataSetChanged();

    }

    /**
     * 填充ViewPager的页面适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        List<Advertisement> list;

        public MyPagerAdapter(List<Advertisement> list) {
            this.list = list;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            // ((ViewPag.er)container).removeView((View)object);
            if (position < list.size()) {
                ((ViewPager) container).removeView(imageViewsList.get(position));
            }

        }

        @Override
        public Object instantiateItem(View container, final int position) {
            final ImageView imageView = imageViewsList.get(position);

            if (position < list.size()) {
                final Advertisement advertisement = list.get(position);
                Glide.with(context)
                        .load(advertisement.attPathUrl)
                        .centerCrop()
                        .placeholder(R.drawable.ic_product_default)
                        .crossFade()
                        .into(imageView);

                ((ViewPager) container).addView(imageView);
            }

            return imageView;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }

    }

    /**
     * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (viewPager.getCurrentItem() == viewPager.getAdapter()
                            .getCount() - 1 && !isAutoPlay) {
                        viewPager.setCurrentItem(0);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int pos) {

            currentItem = pos;
            for (int i = 0; i < dotViewsList.size(); i++) {
                if (i == pos) {
                    View v = ((View) dotViewsList.get(pos));
                    if (dotViewsList.size() == 1) {
                        v.setVisibility(View.GONE);
                    } else {
                        v.setVisibility(View.VISIBLE);
                        v.setBackgroundResource(R.mipmap.dot_focus);
                    }

                } else {
                    ((View) dotViewsList.get(i))
                            .setBackgroundResource(R.mipmap.dot_blur);
                }
            }
        }

    }

    /**
     * 执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imageViewsList.size();
                mHandler.sendEmptyMessage(UPDATE_VIEWPAGER_ITEM);
            }
        }

    }

    /**
     * 销毁ImageView资源，回收内存
     */
    private void destoryBitmaps() {

        for (int i = 0; i < IMAGE_COUNT; i++) {
            ImageView imageView = imageViewsList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                // 解除drawable对view的引用
                drawable.setCallback(null);
            }
        }
    }


    private void processData() {
        Log.d(TAG, "processData  ");
        APIWrapper.getInstance().getBannerImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JavaResponse<List<Advertisement>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(JavaResponse<List<Advertisement>> response) {
                        List<Advertisement> list = response.data;
                        mHandler.obtainMessage(FRESH_BANNER_DATA, list).sendToTarget();
                    }
                });

    }


}