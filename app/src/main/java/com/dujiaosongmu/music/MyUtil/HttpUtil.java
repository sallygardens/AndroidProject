package com.dujiaosongmu.music.MyUtil;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.dujiaosongmu.music.db.User;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    private static Integer id=13;
    private static final String TAG="Httppost";
    public static String registerinByGet(User user) {
        try {

            String path = "http://10.0.2.2:8080/user?id=13"+URLEncoder.encode(id.toString(),"UTF-8")+ "&name=" + URLEncoder.encode(user.getName(),"UTF-8")
                    + "&password=" + URLEncoder.encode(user.getPassword(),"UTF-8")
                  + "&age=" + URLEncoder.encode(user.getAge(), "UTF-8")
                  + "&area=" + URLEncoder.encode(user.getArea(), "UTF-8")
                  + "&fav_music=" + URLEncoder.encode(user.getFav_music(), "UTF-8")
                  + "&fav_instrument=" + URLEncoder.encode(user.getFav_instrument(), "UTF-8");
            Log.d(TAG, "registerinByPost:" + path);
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //connection.setDoOutput(true);//该方法会把get请求变为post请求所以引发错误
            if(connection.getResponseCode()==200){
                Log.d(TAG, "registerinByPost:successful"+connection.getResponseCode());
            }else{
                Log.d(TAG, "registerinByPost:failure"+connection.getResponseCode() );
            }

            id++;//mysql自增id
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String submitPostData(Map<String, String> params, String encode) {
        String UrlPath ="http://10.0.2.2:8080/uploadParam";
        byte[] dataparam = getRequestDataParams(params, encode).toString().getBytes();//获得参数请求体请求体

        Log.d(TAG, "submitPostData: params"+params.toString());

        String MULTIPART_FROM_DATA = "multipart/form-data";
        String PREFIX = "--", LINEND = "\r\n";
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        try {


            //String urlPath = "http://192.168.1.9:80/JJKSms/RecSms.php";
            URL url = new URL(UrlPath);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            httpURLConnection.setRequestProperty("Charsert", "UTF-8");
            //            httpURLConnection.setReadTimeout(10 * 1000); // 缓存的最长时间
//            httpURLConnection.setConnectTimeout(3000);     //设置连接超时时间

//            httpURLConnection.setRequestMethod("POST");     //设置以Post方式提交数据

//            httpURLConnection.setRequestProperty("connection", "keep-alive");

//            httpURLConnection.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(dataparam.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            if(params!=null)
            {outputStream.write(dataparam);}
//            if(files!=null)
//            {outputStream.write(datafile);}
//            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
//            outputStream.write(end_data);
            outputStream.flush();
            InputStreamReader inputStreamReader=new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer=new StringBuffer();
            String line=null;
            while ((line=bufferedReader.readLine())!=null){
                stringBuffer.append(line);
            }
            String result=stringBuffer.toString();
            outputStream.close();
            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            Log.d(TAG, "submitPostData:response "+ response);
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();

                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "-1";
        }
        return "-1";
    }



    public static String submitPostFile(Map<String, String> files, String encode) {
        String UrlPath ="http://10.0.2.2:8080/uploadFiles";
        byte[] dataparam = getRequestDataParams(files, encode).toString().getBytes();//获得参数请求体请求体

        Log.d(TAG, "submitPostData: file："+files.toString());

        String MULTIPART_FROM_DATA = "multipart/form-data";
        String PREFIX = "--", LINEND = "\r\n";
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        try {


            //String urlPath = "http://192.168.1.9:80/JJKSms/RecSms.php";
            URL url = new URL(UrlPath);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            httpURLConnection.setRequestProperty("Charsert", "UTF-8");
            //            httpURLConnection.setReadTimeout(10 * 1000); // 缓存的最长时间
//            httpURLConnection.setConnectTimeout(3000);     //设置连接超时时间

//            httpURLConnection.setRequestMethod("POST");     //设置以Post方式提交数据

//            httpURLConnection.setRequestProperty("connection", "keep-alive");

//            httpURLConnection.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(dataparam.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            if(files!=null)
            {outputStream.write(dataparam);}
//            if(files!=null)
//            {outputStream.write(datafile);}
//            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
//            outputStream.write(end_data);
            outputStream.flush();
            InputStreamReader inputStreamReader=new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer=new StringBuffer();
            String line=null;
            while ((line=bufferedReader.readLine())!=null){
                stringBuffer.append(line);
            }
            String result=stringBuffer.toString();
            outputStream.close();
            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码

            Log.d(TAG, "submitPostFile: "+response);
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();

                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "-1";
        }
        return "-1";
    }



    /*
     * Function  :   封装请求体信息
     * Param     :   params请求体内容，encode编码格式
     * return    :   form表单
     */
    public static StringBuffer getRequestDataParams(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();//存储封装好的请求体信息


        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        try {

            for(Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                //                stringBuffer.append(PREFIX)
//                        .append(BOUNDARY)
//                        .append(LINEND)
//                        .append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND)
//                        .append("Content-Type: text/plain; charset=" + encode + LINEND)
//                        .append("Content-Transfer-Encoding: 8bit" + LINEND)
//                        .append(LINEND)
//                        .append(entry.getValue())
//                        .append(LINEND);
            }
            stringBuffer.delete(stringBuffer.length()-1,stringBuffer.length());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }


    /*
     * Function  :   处理服务器的响应结果（将输入流转化成字符串）
     * Param     :   inputStream服务器的响应输入流
     */
    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

    //用http请求的方式发送图片
    public static String submitPostFile2(Map<String, File> files, String encode) {
        String UrlPath = "http://10.0.2.2:8080/uploadFiles";
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";//边界标识
        int TIME_OUT = 10 * 1000; //超时时间
        HttpURLConnection con = null;
        DataOutputStream ds = null;

        try {
            URL url = new URL(UrlPath);
            con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(TIME_OUT);
            con.setConnectTimeout(TIME_OUT);
            /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
// 设置http连接属性
            con.setRequestMethod("POST");//请求方式
            con.setRequestProperty("Connection", "Keep-Alive");//在一次TCP连接中可以持续发送多份数据而不会断开连接
            con.setRequestProperty("Charset", "UTF-8");//设置编码
            con.setRequestProperty("Content-Type",//multipart/form-data能上传文件的编码格式
                    "multipart/form-data;boundary=" + boundary);

            ds = new DataOutputStream(con.getOutputStream());
            ds.writeBytes(end);
            ds.writeBytes(end);
            // 取得文件的FileInputStream


            for(Map.Entry<String,File> entry:files.entrySet()) {
                Log.d(TAG, "submitPostFile2: "+entry.getValue());
                StringBuilder stringBuilder=new StringBuilder();
                stringBuilder.append(twoHyphens + boundary + end);
               stringBuilder.append("Content-Disposition: form-data; "
                       + "name=\"inputName\";filename=\"" + entry.getKey() + "\"" + end);
               stringBuilder.append("Content-Type: application/octet-stream");
               stringBuilder.append(end);
               stringBuilder.append(end);
               Log.d(TAG, "submitPostFile2: "+stringBuilder.toString());
               ds.writeBytes(stringBuilder.toString());
               //fStream = new FileInputStream(entry.getValue());
                InputStream in = new FileInputStream(entry.getValue());
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];

                int length = -1;
                /* 从文件读取数据至缓冲区 */
                while ((length = in.read(buffer)) != -1) {
                    /* 将资料写入DataOutputStream中 */
                    ds.write(buffer, 0, length);
                }
                in.close();
                //ds.flush();
                //ds.close();
                Log.d(TAG, "submitPostFile2: "+entry.getKey());
                ds.writeBytes(end);

            }
            /* 设置每次写入1024bytes */
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);//结束
            ds.close();

            int response =con.getResponseCode();
            Log.d(TAG, "submitPostFile2: "+response);

            /* 取得Response内容 */
            InputStream is = null;
            is = con.getInputStream();
            con.disconnect();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //showDialog(activity,false,uploadFile,"上传失败" + e);
        }
        return null;
    }

}
