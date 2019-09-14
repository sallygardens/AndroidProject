package com.dujiaosongmu.music.Loading;

import android.graphics.Bitmap;

import java.io.File;

public class LoadFile {

    File mfile;

    public LoadFile(File file, int pg, boolean isUpload, Bitmap bitmap) {
        mfile = file;
        mpg = pg;
        this.isUpload = isUpload;
        mbitmap =bitmap;
    }

    int mpg;//图片下方进度条
    boolean isUpload=false;//是否成功上传
    Bitmap mbitmap;

    public LoadFile() {
    }

    public LoadFile(File file, int pg) {
        mfile = file;
        mpg = pg;
    }

    public LoadFile(File file, int pg, Bitmap bitmap) {
        mfile = file;
        mpg = pg;
        mbitmap = bitmap;
    }

    public File getfile() {
        return mfile;
    }

    public void setfile(File file) {
        mfile = file;
    }

    public int getpg() {
        return mpg;
    }

    public void setpg(int pg) {
        this.mpg = pg;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }

    public Bitmap getbitmap() {
        return mbitmap;
    }

    public void setMbitmap(Bitmap bitmap) {
        this.mbitmap = bitmap;
    }

    @Override
    public String toString() {
        return "LoadFile{" +
                "mfile=" + mfile +
                ", mpg=" + mpg +
                ", isUpload=" + isUpload +
                ", mbitmap=" + mbitmap +
                '}';
    }
}
