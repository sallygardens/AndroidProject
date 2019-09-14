package com.dujiaosongmu.music.db;

import android.widget.TextView;

public class FindMessager {
    private String Name;  //文件名称
    private String Zt;    //状态

    public FindMessager() {
    }

    public FindMessager(String name, String zt) {
        this.Name = name;
        this.Zt = zt;
    }

    public String getName() {
        return Name;
    }

    public String getZt() {
        return Zt;
    }


    class ViewHolder {
        TextView textView1;
        TextView textview2;
    }
}
