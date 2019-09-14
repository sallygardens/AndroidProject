package com.dujiaosongmu.music;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.dujiaosongmu.music.Activity.CameraActivity;
import com.dujiaosongmu.music.Activity.ComplexViewActivity;
import com.dujiaosongmu.music.Activity.RegisterActivity;
import com.dujiaosongmu.music.Activity.UploadingPicture;
import com.dujiaosongmu.music.Activity.ViewPagerTest;
import com.dujiaosongmu.music.Activity.ViewTestActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView responseText;
    Button sendRequest;
    Button nextPage;
    Button tr;//toregister
    Button tc;//tocamera
    Button up;//UploadingPicture
    Button cv;//complexview
    Button vp;//viewpager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendRequest= findViewById(R.id.send_request);
        responseText = findViewById(R.id.response_text);
        nextPage = findViewById(R.id.next_page);
        tr=findViewById(R.id.toregister);
        tc=findViewById(R.id.tocamera);
        up=findViewById(R.id.uploadingpicture);
        cv=findViewById(R.id.complexview);
        vp=findViewById(R.id.viewpager);

        sendRequest.setOnClickListener(this);
        nextPage.setOnClickListener(this);
        tr.setOnClickListener(this);
        tc.setOnClickListener(this);
        up.setOnClickListener(this);
        cv.setOnClickListener(this);
        vp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.send_request){
            sendRequestWithOkHttp();
        }
        else if(v.getId()==R.id.next_page){
            Intent intent=new Intent(MainActivity.this, ViewTestActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.toregister){
            Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.tocamera){
            Intent intent=new Intent(MainActivity.this, CameraActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.uploadingpicture){
            Intent intent=new Intent(MainActivity.this, UploadingPicture.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.complexview){
            Intent intent=new Intent(MainActivity.this, ComplexViewActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.viewpager){
            Intent intent=new Intent(MainActivity.this, ViewPagerTest.class);
            startActivity(intent);
        }
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://10.0.2.2:8080/user/3").build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    String responseData1 ="[ "+responseData+" ]";

                    parseJSONWithJSONObject(responseData1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void parseJSONWithJSONObject(String jsonData){
        try{
            JSONArray jsonArray=new JSONArray(jsonData);


            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id=jsonObject.getString("id");
                String name=jsonObject.getString("name");
                Log.d("MainActivity","id is"+id);
                Log.d("MainActivity","name is"+name);
                showResponse(id+" "+name);
          }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
}