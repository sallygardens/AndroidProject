package com.dujiaosongmu.music.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dujiaosongmu.music.MyUtil.MyUtils;
import com.dujiaosongmu.music.R;
import com.dujiaosongmu.music.ui.HorizontalScrollViewEx;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewTestActivity extends AppCompatActivity {
    private static final String TAG="viewtest";
    private HorizontalScrollViewEx mListContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);
        Log.d(TAG,"onCreate");
        initView();
    }
    private void initView(){
        LayoutInflater inflater=getLayoutInflater();
        mListContainer=(HorizontalScrollViewEx)findViewById(R.id.container);
        final int screenWidth= MyUtils.getScreenMetrics(this).widthPixels;
        final int screenHeight=MyUtils.getScreenMetrics(this).heightPixels;
        for(int i=0;i<3;i++){
            ViewGroup layout=(ViewGroup)inflater.inflate(R.layout.content_layout,mListContainer,false);
            layout.getLayoutParams().width=screenWidth;
            TextView textView=(TextView) layout.findViewById(R.id.title);
            textView.setText("page"+(i+1));
            layout.setBackgroundColor(Color.rgb(255/(i+1),255/(i+1),0));
            createList(layout);
            mListContainer.addView(layout);
        }

    }
    public void createList(ViewGroup layout){
        ListView listView=layout.findViewById(R.id.list);
        ArrayList<String> datas=new ArrayList<String>();
        for(int i=0;i<50;i++){
            datas.add("name"+i);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(ViewTestActivity.this,R.layout.content_list_item,R.id.name,datas);
        listView.setAdapter(adapter);
    }

}
