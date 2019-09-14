package com.dujiaosongmu.music.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dujiaosongmu.music.R;
import com.dujiaosongmu.music.fragment.Camera2BasicFragment;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container2
                    , Camera2BasicFragment.newInstance()).commit();

        }
    }


}
