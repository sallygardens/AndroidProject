package com.dujiaosongmu.music.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dujiaosongmu.music.Loading.LoadFile;
import com.dujiaosongmu.music.R;

import java.util.List;

public class LoadPicAdapter extends RecyclerView.Adapter<LoadPicAdapter.MyViewHolder> {

    Context context;
    List<LoadFile> fileList=null;
    View view;
    int picNum=8;//列表图片的最大值


    public LoadPicAdapter(Context context, List<LoadFile> fileList) {
        this.context = context;
        this.fileList = fileList;
    }

    public LoadPicAdapter(Context context, List<LoadFile> fileList, int picNum) {
        this.context = context;
        this.fileList = fileList;
        this.picNum = picNum;
    }


   static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPic;
        ImageView ivDel;
        ProgressBar bg_progressbar;
        View view;
        public MyViewHolder(@NonNull View itemView) {
           super(itemView);
            ivPic=itemView.findViewById(R.id.ivPic);
            ivDel=itemView.findViewById(R.id.ivdel);
            bg_progressbar=itemView.findViewById(R.id.bg_progressbar);
            view=itemView;
       }
   }

   @Override
   public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        view= LayoutInflater.from(context).inflate(R.layout.rvpic_item,parent,false);
        return new MyViewHolder(view);
    }

    public interface OnItemClickListener{
        void click(View view,int position);
        void del(View view);
    }

    OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //第一个为空文件，且在文件个数小于最大限制的情况，当文件个数等于大于限制值时第一个不是添加按钮
        if(position==0&&fileList.get(position).getbitmap()==null){
            holder.ivPic.setImageResource(R.drawable.ic_add_pic);//加号图片
            holder.ivPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.click(view,position);
                }
            });
            holder.ivDel.setVisibility(View.INVISIBLE);
            holder.bg_progressbar.setVisibility(View.GONE);
        }else{
            holder.ivPic.setImageBitmap(fileList.get(position).getbitmap());
            //使用压缩后的图片
            holder.ivDel.setVisibility(ViewGroup.VISIBLE);
            holder.bg_progressbar.setVisibility(View.VISIBLE);
            holder.bg_progressbar.setProgress(fileList.get(position).getpg());
        }
        holder.ivDel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(fileList.get(position).isUpload()){
                Toast.makeText(context,"图片已上传",Toast.LENGTH_SHORT).show();
                }else{
                    fileList.remove(position);
                    if(fileList.size()==picNum-1&&fileList.get(0).getbitmap()!=null){
                        fileList.add(0,new LoadFile());
                    }//如果数量达到最大值时前面加号去掉，然后减去后再添加
                    notifyDataSetChanged();
                    if(listener!=null){
                        listener.del(view);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return fileList.size();
    }
}
