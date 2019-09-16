package com.example.administrator.demo01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //建立Intent接收路径信息
        Intent intent = getIntent();
        String s = intent.getStringExtra("path");
        //找到ImageView
        CustomImageView imageView = findViewById(R.id.IMG);
        //由路径加载图片并且解码为bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(s);
        //显示图片
        imageView.setImageBitmap(bitmap);
        imageView.setOnClickListener(view ->{
            finish();
        } );

    }
}
