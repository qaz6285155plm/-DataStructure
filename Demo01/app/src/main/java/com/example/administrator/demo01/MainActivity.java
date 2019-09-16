package com.example.administrator.demo01;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private  List<String>  imagePathList;
    private RecyclerView recyclerView;//定义Recycler
    private List<BitMapClass> bitMapClassList = new ArrayList<>();//定义一个以BitMapClass为泛型的List
    private File[] curFiles;
    private File curparent;
    private TextView textView;

    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 0x123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

        //申请权限
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }


        //initList（）方法用于给playerList填充数据
        initList();

        recyclerView = findViewById(R.id.main_rv);
        textView = findViewById(R.id.path);
        textView.setText(curparent.getPath().toString());
        //创建LinearLayoutManager，用于决定RecyclerView的布局方式为网格布局
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        //创建适配器
        PlayerAdapter adapter = new PlayerAdapter(bitMapClassList,imagePathList,this);
        recyclerView.setAdapter(adapter);
    }

    //判断是否具有权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
        }
    }

    //定义initList方法
    private void initList() {

       imagePathList = new ArrayList<>();
//        String filePath = "/storage/emulated/0/test";//加载路径
//        File fileAll = new File(filePath);//获取路径文件夹下所有图片
//        files = fileAll.listFiles();//获取文件夹下所有图片的绝对路径
        File root = new File(Environment.getExternalStorageDirectory().getPath());
        curparent = root;
        curFiles = curparent.listFiles();
        int m = 0;
        int n=0;
        for (int i = 0; i < curFiles.length; i++) {
            //循环加载所有图片

            File file = curFiles[i];
            if (isJpg(file.getPath().toString())) {
                imagePathList.add(file.getPath());//获得绝对路径

                /**
                 * 压缩图片
                 */
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                Bitmap bitmap = BitmapFactory.decodeFile(imagePathList.get(m),opts);//生成位图
                int inSam = Change.calculateInSampleSize(opts,300,400);
                opts.inSampleSize = inSam;
                opts.inJustDecodeBounds = false;
                Log.e("bitmapsize", String.valueOf(inSam));
                Bitmap bmp1 = BitmapFactory.decodeFile(imagePathList.get(m), opts);

                //新建BitMapClass类，定义其中的位图与路径属性
                BitMapClass bit = new BitMapClass(bmp1, imagePathList.get(m));
                bitMapClassList.add(bit);//将转换出的位图加入List中
            }
            if (file.isDirectory()) {
                imagePathList.add(file.getPath());
                Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.a);
                BitMapClass bit =new BitMapClass(bitmap,imagePathList.get(m));
                bitMapClassList.add(bit);
                m++;
            }
        }
    }

    public boolean isJpg(String file) {
        if (file.toLowerCase().endsWith(".jpg") || file.toLowerCase().endsWith(".png")) {
            return true;
        } else {
            return false;
        }
    }


}

