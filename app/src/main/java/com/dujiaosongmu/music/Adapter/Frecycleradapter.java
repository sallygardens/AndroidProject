package com.dujiaosongmu.music.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dujiaosongmu.music.R;
import com.dujiaosongmu.music.db.FindMessager;

import java.util.List;

public class Frecycleradapter extends RecyclerView.Adapter<Frecycleradapter.ViewHolder> {
    private List<FindMessager> mFmessge;

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView1;
        TextView textView2;
        public ViewHolder(View view){
            super(view);
            textView1=view.findViewById(R.id.txt_xs);
            textView2=view.findViewById(R.id.txt_msg);
        }
    }
    public Frecycleradapter(List<FindMessager> fmessages){
      //  Log.d("777", "getItemCount: "+ mFmessge.size());
        mFmessge=fmessages;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    @Override
   public void onBindViewHolder(ViewHolder holder,int position){
       FindMessager fmessage=mFmessge.get(position);
        holder.textView1.setText(fmessage.getName());
        holder.textView2.setText(fmessage.getZt());
   }
   @Override
    public int getItemCount(){

        return mFmessge.size();
   }
}
