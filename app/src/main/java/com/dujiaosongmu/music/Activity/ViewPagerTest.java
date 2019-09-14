package com.dujiaosongmu.music.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dujiaosongmu.music.Adapter.FindAdapter;
import com.dujiaosongmu.music.Adapter.FindPagerAdapter;
import com.dujiaosongmu.music.Adapter.ViewPagerTestAdapter;
import com.dujiaosongmu.music.R;
import com.dujiaosongmu.music.fragment.ChannelFragment;
import com.dujiaosongmu.music.fragment.FindFragment;
import com.dujiaosongmu.music.fragment.ProfessionFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerTest extends AppCompatActivity implements View.OnClickListener, ProfessionFragment.OnFragmentInteractionListener,ChannelFragment.OnFragmentInteractionListener, FindFragment.OnFragmentInteractionListener {

    private TabLayout tabLayout;

    private ViewPager viewPager;


    /**
     *创建一个用于存储fragment的列表
     */
    List<Fragment> fragments = new ArrayList<>();

    /**
     *创建一个titlestring数组用来存储关联在tablayout上的fragment的名称
     */
    List<String> titles = new ArrayList<>();

    /**
     *以下是我们要添加的三个fragment给viewpager
     */
    Fragment findFragment;
    Fragment professionFragment;
    Fragment channelFragment;

    private ArrayList<View> pageview;
    private TextView findLayout;
    private TextView professionLayout;
    private TextView channelLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EditText editText = findViewById(R.id.search_bar);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(RecommendActivity.this, SearchActivity.class);
                // startActivity(intent);
            }
        });

        tabLayout = findViewById(R.id.tablayouttest);
        viewPager = findViewById(R.id.viewpagertest);

        //find fragment which is my definition
//        findFragment = getSupportFragmentManager().findFragmentById(R.id.find_fragment);
//        professionFragment = getSupportFragmentManager().findFragmentById(R.id.profession_fragment);
//        channelFragment = getSupportFragmentManager().findFragmentById(R.id.channel_fragment);
        findFragment=new FindFragment();
        professionFragment=new ProfessionFragment();
        channelFragment=new ChannelFragment();
        //put fragments into my fragmentlist
        fragments.add(findFragment);
        fragments.add(professionFragment);
        fragments.add(channelFragment);


        //add title
        titles.add("发现");
        titles.add("专业");
        titles.add("频道");

        //establish viewpager data adapter
        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewPagerTestAdapter viewPagerTestAdapter = new ViewPagerTestAdapter(fragmentManager, fragments,titles);
        viewPager.setAdapter(viewPagerTestAdapter);

        //connect viewpager to tablayout
        tabLayout.setupWithViewPager(viewPager);

        //cache one view to prevent pause when sliding views
        viewPager.setOffscreenPageLimit(1);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
