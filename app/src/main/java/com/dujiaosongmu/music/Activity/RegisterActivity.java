package com.dujiaosongmu.music.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dujiaosongmu.music.MyUtil.HttpUtil;
import com.dujiaosongmu.music.R;
import com.dujiaosongmu.music.db.User;

import java.net.HttpURLConnection;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText un;//username
    private EditText pw;//password
    private EditText a;//age
    private EditText ar;//area
    private EditText fm;//fav_music
    private EditText fi;//fav_instrument
    private Button reg;//register

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
    private void initView(){
        un=findViewById(R.id.username);
        pw=findViewById(R.id.password);
        a=findViewById(R.id.age);
        ar=findViewById(R.id.area);
        fm=findViewById(R.id.fav_music);
        fi=findViewById(R.id.fav_instrument);
        reg=findViewById(R.id.register);
        reg.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:{
                Toast.makeText(RegisterActivity.this,"正在注册中…",Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User user=new User();
                        user.setName(un.getText().toString());
                        user.setPassword(pw.getText().toString());
                        user.setAge(a.getText().toString());
                        user.setArea(ar.getText().toString());
                        user.setFav_music(fm.getText().toString());
                        user.setFav_instrument(fi.getText().toString());
                        final String response= HttpUtil.registerinByGet(user);

                    }
                }).start();

            }

        }
    }
}
