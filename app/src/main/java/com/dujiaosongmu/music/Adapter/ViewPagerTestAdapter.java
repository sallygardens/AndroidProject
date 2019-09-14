package com.dujiaosongmu.music.Adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

public class ViewPagerTestAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragments;
    List<String> titles;

    public ViewPagerTestAdapter(FragmentManager fm,List<Fragment> fragments,List<String> titles) {
        super(fm);

        this.fragments=fragments;
        Log.d("88888", "ViewPagerTestAdapter: "+this.fragments.toString());
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

}
