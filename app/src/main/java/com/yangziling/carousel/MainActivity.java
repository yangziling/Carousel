package com.yangziling.carousel;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <b>Create Date:</b> 2018/1/3<br>
 * <b>Author:</b> Stone <br>
 * <b>Description:</b> <br>
 */
public class MainActivity extends AppCompatActivity {
    private ViewPager mMyViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    private TextView title;
    private MyAdapter adapter;
    //当前显示图片的位置
    private int localPosition = 0;

    //图片的id
    private int[] imageIds =
            new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};
    //图片的标题
    private String[] titles =
            new String[]{"蓝天白云", "青山绿水", "枯藤老树", "人间仙境", "岛屿大树"};
    private TimerTask mTimerTask;
    //创建一个定时器
    private final Timer timer = new Timer();
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyViewPaper = (ViewPager) findViewById(R.id.vp);

        //显示的图片
        images = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            mImageView = new ImageView(this);
            mImageView.setBackgroundResource(imageIds[i]);
            images.add(mImageView);
        }
        //指示点
        dots = new ArrayList<>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));

        title = (TextView) findViewById(R.id.title);
        title.setText(titles[0]);

        adapter = new MyAdapter(MainActivity.this, images);
        mMyViewPaper.setAdapter(adapter);
        mMyViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                position = position % images.size();
                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.dot_focused);
                dots.get(localPosition).setBackgroundResource(R.drawable.dot_normal);

                localPosition = position;
                currentItem = position;
            }

            /**
             * 页面滑动时回调
             */
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            /**
             * 当ViewPager状态改变时，回调
             */
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 轮播任务
     */
    @Override
    protected void onStart() {
        super.onStart();
        mTimerTask = new TimerTask() {

            @Override
            public void run() {
                currentItem = (currentItem + 1) % imageIds.length;
                mHandler.sendEmptyMessage(0);
            }
        };
        timer.schedule(mTimerTask, 2000, 2000);
    }

    /**
     * 接收子线程传递的数据
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //轮播到最后一张图片时，直接跳转至第一页，并且取消滑动效果
            if (currentItem % images.size() == 0) {
                mMyViewPaper.setCurrentItem(currentItem, false);
            }
            //非最后一张展示图片的滑动效果
            mMyViewPaper.setCurrentItem(currentItem, true);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }
}
