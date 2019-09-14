package com.dujiaosongmu.music.MyUtil;

import android.net.Uri;

import java.io.File;
import java.net.URI;

public class MyfileUtil extends File {
    public MyfileUtil(String pathname) {
        super(pathname);
    }

    public MyfileUtil(String parent, String child) {
        super(parent, child);
    }

    public MyfileUtil(File parent, String child) {
        super(parent, child);
    }

    public MyfileUtil(URI uri) {
        super(uri);
    }

    public static File setUri(Uri uri){
        return new File(uri.toString());
    }
}
