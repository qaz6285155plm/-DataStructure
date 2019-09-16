package com.example.administrator.demo01;

import android.graphics.Bitmap;


public class BitMapClass {
    private Bitmap bitmap;//定义bitmap
    private String path;//定义路径


    //定义构造方法
    public BitMapClass(Bitmap bitmap, String path) {
        this.bitmap = bitmap;
        this.path = path;
    }

    //get方法
    public String getPath() {
        return path;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    //set方法
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
