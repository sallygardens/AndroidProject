package com.dujiaosongmu.music.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.dujiaosongmu.music.db.FindMessager;

import java.util.ArrayList;
import java.util.List;

public class FindPagerAdapter extends PagerAdapter {
    private Context context;
    ArrayList<View> pageview;
    RecyclerView recyclerView;
    private List<FindMessager> fimessages;

    public FindPagerAdapter (Context context,ArrayList<View> pageview,RecyclerView recyclerView) {
        this.context=context;
        this.pageview=pageview;
        this.recyclerView=recyclerView;
    }
    @Override
    //获取当前界面参数
    public int getCount() {
        return pageview.size();
    }

    @Override
    //判断是否由对象生成界面
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        // TODO Auto-generated method stub
        return view == o;
    }

    //使从ViewGroup中移出当前View
    public void destroyItem(ViewGroup container, int position, Object Object) {
        container.removeView(pageview.get(position));
    }

    //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d("6666", "instantiateItem: "+position);
        Log.d("6666", "instantiateItem: "+pageview.size());
        Log.d("6666", "instantiateItem: "+context.toString());
        container.addView(pageview.get(position));

        return pageview.get(position);
    }
}

