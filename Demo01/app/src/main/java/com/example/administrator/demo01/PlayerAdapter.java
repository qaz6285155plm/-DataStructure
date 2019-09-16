package com.example.administrator.demo01;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

//自定义类继承RecyclerView.Adapter,将泛型指定为PlayerAdapter.ViewHolder
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    private List<BitMapClass> playerList;//创建list集合，泛型为之前定义的实体类BitMapClass
    private Context mContext;//创建Context，用于向适配器中传入context
    private List<String> imagePathList;

    //添加构造方法
    public PlayerAdapter(List<BitMapClass> playerList, List<String> imagePathList, Context mContext) {
        this.playerList = playerList;
        this.mContext = mContext;
        this.imagePathList = imagePathList;

    }

    //在onCreateViewHolder（）中完成布局的绑定，同时创建ViewHolder对象，返回ViewHolder对象
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //在内部类中完成对控件的绑定
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;


        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.filename);
            imageView = itemView.findViewById(R.id.item_imgv);
        }
    }

    //在onBindViewHolder（）中完成对数据的填充
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        //设置初始界面的缩略图

        if (isDictionary(imagePathList.get(position))) {
            holder.textView.setText(getFileName(playerList.get(position).getPath().toString()));
            holder.imageView.setImageBitmap(playerList.get(position).getBitmap());
            holder.imageView.setOnClickListener(new ImageView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //新建Intent对象
                    Intent intent = new Intent();

                    intent.setClass(mContext, Main3Activity.class);
                    //若没有FLAG_ACTIVITY_NEW_TASK属性会报错
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //将path属性传入intent
                    intent.putExtra("path", playerList.get(position).getPath());
                    //启动Main2Activity
                    mContext.startActivity(intent);
                }
            });
        } else {
            holder.textView.setText(getphotoName(playerList.get(position).getPath().toString()));
            //设置图片点击事件
            Bitmap temp = playerList.get(position).getBitmap();
            holder.imageView.setImageBitmap(temp);
            holder.imageView.setOnClickListener(new ImageView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //新建Intent对象
                    Intent intent = new Intent();

                    intent.setClass(mContext, Main2Activity.class);
                    //若没有FLAG_ACTIVITY_NEW_TASK属性会报错
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //将path属性传入intent
                    intent.putExtra("path", playerList.get(position).getPath());
                    //启动Main2Activity
                    mContext.startActivity(intent);
                }
            });

        }
    }

    //获取文件名（过滤路径）
    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public String getphotoName(String pathandname) {

        int start = pathandname.lastIndexOf("/");
        int end = pathandname.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return pathandname.substring(start + 1, end);
        } else {
            return null;
        }

    }

    public String getFileName(String pathandname) {

        int start = pathandname.lastIndexOf("/");
        int end = pathandname.length();

            return pathandname.substring(start + 1, end);
        }



    public boolean isDictionary(String pathandname) {
        File file=new File(pathandname);
        return file.isDirectory();
    }
}
