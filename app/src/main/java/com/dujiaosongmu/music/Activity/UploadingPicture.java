package com.dujiaosongmu.music.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dujiaosongmu.music.Adapter.LoadPicAdapter;
import com.dujiaosongmu.music.Loading.LoadFile;
import com.dujiaosongmu.music.MyUtil.FileTransferUtil;
import com.dujiaosongmu.music.MyUtil.HttpUtil;
import com.dujiaosongmu.music.MyUtil.MyfileUtil;
import com.dujiaosongmu.music.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.RequestBody.Companion.*;
import okio.BufferedSink;

public class UploadingPicture extends AppCompatActivity implements View.OnClickListener{

    List<LoadFile> fileList=new ArrayList<>();
    LoadPicAdapter adapter=null;
    RecyclerView rvPic;
    TextView tvNum;
    Button btnEnter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploading_picture);
        rvPic=findViewById(R.id.picrv);
        tvNum=findViewById(R.id.picnum);
        btnEnter=findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(this);
        initAdapter();
    }

    final Map<String, String> params = new HashMap<>();//参数map
    final Map<String,String> files = new HashMap<>();//图片文件映射map
    private Map<String,File> filetest=new HashMap<>();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEnter:{
                enterEnable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int index=0;
                        for(LoadFile picfile:fileList){
                            if(picfile.isUpload()==false&&picfile.getfile()!=null){
                                postToService(picfile,index++);
                            }

                            String s=HttpUtil.submitPostData(params,"UTF-8");
                            String st= HttpUtil.submitPostFile2(filetest,"UTF-8");
                           //  String sf= HttpUtil.submitPostFile(files,"UTF-8");
                            if(s=="-1"){
                                Log.d("upload", "run:paramsfailure ");}
//                            else if(sf=="-1"){
//                                Log.d("upload", "run:filesfailure ");
//                            }
                        }
                    }
                }).start();
                enterEnable(true);
            }
        }
    }

    private void initAdapter(){
        fileList.add(new LoadFile());
        adapter=new LoadPicAdapter(this,fileList,8);
        rvPic.setAdapter(adapter);

        rvPic.setLayoutManager(new GridLayoutManager(this,3));
        adapter.setListener(new LoadPicAdapter.OnItemClickListener() {
            @Override
            public void click(View view, int position) {
                if(fileList.size()>8){
                   showShortToast("一次最多上传8张");
                }else{
                    selectPic();
                }
            }

            @Override
            public void del(View view) {
                tvNum.setText((fileList.size()-1)+"/8");
            }
        });
    }



    private void postToService(LoadFile file,int index){
        params.put("picname"+index,file.getfile().getName());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // 压缩图片
        file.getbitmap().compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byte_arr = stream.toByteArray();
        // Base64图片转码为String
        String encodedString = Base64.encodeToString(byte_arr,0);//Base64.getEncoder().encode(byte_arr).toString();
        //files.put("picfile"+index,encodedString);
        filetest.put("picfile"+index,file.getfile());
    }
    String mPhotoPath;
    Uri uriImage;
    File mPhotoFile=null;

    private void selectPic(){
        //动态请求权限
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA},1);

        }
        final CharSequence[] items={"相册","拍照"};
        AlertDialog.Builder dlg=new AlertDialog.Builder(this);
        dlg.setTitle("添加图片");
        dlg.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    Intent intent=new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,0);//获得该活动返回的数据，请求码标识活动作用
                }else{
                   //拍照操作
                }
            }
        }).create();
        dlg.show();
    }

    public String getSDPath(){
        File sdDir=null;
        boolean sdCardExsit= Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(sdCardExsit){
            sdDir=Environment.getExternalStorageDirectory();
        }
        return sdDir.toString();
    }
    private String getPhotoFileName(){
        Date date=new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat=new SimpleDateFormat("'img'_yyyyMMdd_HH:mm:ss");
        return  dateFormat.format(date)+".jpg";
    }
    private void showShortToast(String toast){
        Toast.makeText(this,toast,Toast.LENGTH_SHORT);
    }
    //获取返回的图片?


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){//这与上面dialog发送请求并返回图片数据进行处理
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=2;//图片的宽高为原来的二分之一，图片大小为原来的四分之一

            Bitmap bitmap=BitmapFactory.decodeFile(mPhotoPath,options);
            if(bitmap!=null){
                if(uriImage!=null){
                    saveUriToFile(uriImage,1);
                }
                if(!bitmap.isRecycled()){
                    bitmap.recycle();//回收图片所占的内存
                    System.gc();//提醒系统及时回收
                }
            }
        }
        if(requestCode==0){
            if(data!=null){
                Uri uri=data.getData();
                Log.d("wang", "onActivityResult: "+uri);
                saveUriToFile(uri,0);

            }
        }
    }
    //将uri图片类型转化为file，bitmap型在页面显示bitmap图片防止内存溢出
    private void saveUriToFile(Uri uriImage,int type){
        Bitmap photoBmp=null;
        if(uriImage!=null){
            try{
                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inSampleSize=2;
                photoBmp=BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uriImage),null,options);

                String test= FileTransferUtil.getPath(this,uriImage);
                uriImage=Uri.parse(test);

                File testfile=FileTransferUtil.getFileByUri(this,uriImage);

                if(type==0){
                  //  File file=new File(testfile);
                    fileList.add(new LoadFile(testfile,0,false,photoBmp));
                  //  file= MyfileUtil.setUri(uriImage);

                }else{
//                    if(mPhotoFile!=null){
//                        file=mPhotoFile;
//                    }
                }


               // fileList.add(new LoadFile(file,0,false,photoBmp));

                tvNum.setText((fileList.size()-1)+"/8");
                if(fileList.size()>8){
                    fileList.remove(0);
                }

                adapter.notifyDataSetChanged();
            }catch (IOException e){
                e.printStackTrace();
                Log.d("TAG","压缩图片异常");
            }
        }
    }

    //用来提示用户文件上传的情况。同时也是避免同时反复操作
    public void enterEnable(boolean isEnabled) {
        if (isEnabled) {
            btnEnter.setText("重新上传");
            btnEnter.setEnabled(true);
//            btnEnter.setBackgroundResource(R.drawable.selecter_button);
//            btnEnter.setTextColor(getResources().getColor(R.color.inButtonText));

        } else {
            btnEnter.setText("正在上传···");
            btnEnter.setEnabled(false);
//            btnEnter.setBackgroundResource(R.drawable.buttonshap);
//            btnEnter.setTextColor(getResources().getColor(R.color.outButtonText));
        }
    }


}
