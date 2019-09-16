package com.example.administrator.demo01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    private RecyclerView recyclerView;//定义Recycler
    private List<BitMapClass> bitMapClassList = new ArrayList<>();//定义一个以BitMapClass为泛型的List
    private File[] curFiles;
    private File curparent;
    private TextView textView;
    private  List<String>  imagePathList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);
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

    //定义initList方法
    private void initList() {
        //接受上一个界面传来的路径
        Intent intent = getIntent();
        String s = intent.getStringExtra("path");
        imagePathList = new ArrayList<>();
//        String filePath = "/storage/emulated/0/test";//加载路径
//        File fileAll = new File(filePath);//获取路径文件夹下所有图片
//        files = fileAll.listFiles();//获取文件夹下所有图片的绝对路径
        File root = new File(s);
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
                m++;


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
        if (file.toLowerCase().endsWith(".jpg") || file.toLowerCase().endsWith(".png")||file.toLowerCase().endsWith(".jpeg")||file.toLowerCase().endsWith(".bmp")) {
            return true;
        } else {
            return false;
        }
    }

}
