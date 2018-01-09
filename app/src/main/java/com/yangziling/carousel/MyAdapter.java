package com.yangziling.carousel;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * <b>Create Date:</b> 2018/1/3<br>
 * <b>Author:</b> Stone <br>
 * <b>Description:</b> <br>
 */
public class MyAdapter extends PagerAdapter {

    private List<ImageView> images;
    private Context mContext;

    public MyAdapter(Context context,List<ImageView> images) {
        this.mContext =context;
        this.images = images;
    }
    //返回Viewpager中的view个数
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    //判断instantiateItem中的函数返回的key与一个页面示图是不是代表同一个
    //通常直接相等就OK啦
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    //移除一个固定位置的页面
    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {

        view.removeView((View) object);
    }

    //将固定位置的View添加到Viewgroup中，并创建显示出来
    @Override
    public Object instantiateItem(ViewGroup view, final int position) {

        ImageView imageView = images.get(position % images.size());
        ViewGroup parent = (ViewGroup) imageView.getParent();
        //这里是动态添加示图，一个子类只能有一个父类
        //判断下如果parent存在一定要记得移除
        if (parent != null) {
            parent.removeView(imageView);
        }

        view.addView(imageView);
        //给图片添加点击事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点击了第"+(position % images.size()+1)+"图片",Toast.LENGTH_SHORT).show();
            }
        });
        return images.get(position % images.size());
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }




}
