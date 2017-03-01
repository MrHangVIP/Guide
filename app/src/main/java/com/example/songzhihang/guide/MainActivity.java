package com.example.songzhihang.guide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<View> mPicViews = new ArrayList<>();
    private Timer timer;
    private List<String> imageUrls = new ArrayList<>();
    private int index;
    private ViewPager viewPager;
    private float width;
    private float height;

    private Handler timerHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int count = msg.what;
            int currentItem = viewPager.getCurrentItem();
            try {
                viewPager.setCurrentItem((currentItem + 1) % count, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initData();
    }

    private void findViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void initData() {
        getScreenProperty();
        imageUrls.add("http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=4258410114,1864035878&os=696894082,251134109&simid=3340348687,156642102&pn=87&rn=1&di=89504082910&ln=1993&fr=&fmq=1488331406767_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=1e&objurl=http%3A%2F%2Fpic33.nipic.com%2F20130907%2F12906030_161342990000_2.png&rpstart=0&rpnum=0&adpicid=0");
        imageUrls.add("http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=2934335366,916431300&os=3754129648,1651023579&simid=4111575876,482546114&pn=86&rn=1&di=102560218090&ln=1993&fr=&fmq=1488331406767_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=1e&objurl=http%3A%2F%2Ftupian.enterdesk.com%2F2012%2F0606%2Fgha%2F10%2F11285966_1334673509285.jpg&rpstart=0&rpnum=0&adpicid=0");
        imageUrls.add("http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=3408403074,1974597991&os=3233399909,3303004825&simid=3483875776,285681351&pn=88&rn=1&di=136286053640&ln=1993&fr=&fmq=1488331406767_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=1e&objurl=http%3A%2F%2Fpic39.nipic.com%2F20140308%2F496038_210222044000_2.jpg&rpstart=0&rpnum=0&adpicid=0");
        setLiveAdapter();
    }


    private static void loadingImgWithCommon(final Context mContext,
                                             String url, final int placeholder,final ImageView img,  int width,
                                             int height) {
        if (null == img) {
            return;
        }
//        Glide.clear(img);
        Glide.with(mContext)
                .load(R.drawable.img1)
                .asBitmap()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Log.e(TAG, "onResourceReady: " );
                        img.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        Log.e(TAG, "onLoadFailed: ");
                        super.onLoadFailed(e, errorDrawable);
                        img.setImageDrawable(errorDrawable);
                    }
                });
//        DrawableTypeRequest<String> mDrawableTypeRequest = Glide.with(mContext)
//                .load(url);
//        mDrawableTypeRequest.dontAnimate();
//        if (width != 0 && height != 0) {
//            mDrawableTypeRequest.override(width, height);
//        }
//        mDrawableTypeRequest
//                .listener(new RequestListener<String, GlideDrawable>() {
//
//                    @Override
//                    public boolean onException(Exception arg0, String arg1,
//                                               Target<GlideDrawable> arg2, boolean arg3) {
//                        if (arg0 != null) {
//                            Log.e(TAG, arg0.getMessage());
//                        }
//                        try {
//                            img.setImageResource(placeholder);
//                        } catch (OutOfMemoryError e) {
//                        }
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable arg0,
//                                                   String arg1, Target<GlideDrawable> arg2,
//                                                   boolean arg3, boolean arg4) {
//                        return false;
//                    }
//                });
//        mDrawableTypeRequest.placeholder(placeholder)
//                .error(placeholder)
//                .diskCacheStrategy(DiskCacheStrategy.NONE).into(img);
    }

    private void setLiveAdapter() {
        mPicViews.clear();
        for (int i = 0, l = imageUrls.size(); i < l; i++) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_layout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            TextView cursorPoint = (TextView) view.findViewById(R.id.cursor);
            loadingImgWithCommon(MainActivity.this, imageUrls.get(i), R.mipmap.ic_launcher,imageView, (int)width,
                    (int) (width * .486));
            mPicViews.add(view);
            cursorPoint.setTag(imageUrls.get(i));
            imageView.setTag(imageUrls.get(i));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
        viewPager.setAdapter(new IndexPagerAdapter());
        viewPager.setCurrentItem(index);
        scrollPoint(index);
        change(imageUrls.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                index = arg0;
                scrollPoint(arg0);
                change(imageUrls.size());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }


    private void change(final int listSize) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                timerHandler.sendEmptyMessage(listSize);

            }
        }, 3000, 5000);
    }

    private void scrollPoint(int new_position) {
        // 设置首页的底部游标
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mPicViews.size(); i++) {
            builder.append("<font color='" + (new_position == i ? "#2f8dd4" : "#ffffff") + "'>● </font>");
        }
        if (mPicViews.size() > 1) {
            ((TextView) mPicViews.get(new_position).findViewById(R.id.cursor)).setText(Html.fromHtml(builder.toString()));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    // 图片的Adapter
    private class IndexPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPicViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (position < mPicViews.size()) {
                ((ViewPager) container).removeView(mPicViews.get(position));
            } else {
                ((ViewPager) container).removeView((View) object);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mPicViews.get(position);
            if (null != view.getParent()) {
                Log.e("debug", "v.getParent():" + view.getParent().getClass().getSimpleName());
            } else {
                ((ViewPager) container).addView(view, 0);
            }

            return view;
        }

    }

    private void getScreenProperty() {
        DisplayMetrics dm = new DisplayMetrics();
        MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
    }

}
