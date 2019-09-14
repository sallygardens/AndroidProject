package com.dujiaosongmu.music.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dujiaosongmu.music.R;
import com.dujiaosongmu.music.db.FindMessager;

import java.util.List;

public class FindAdapter extends RecyclerView.Adapter<FindAdapter.ViewHolder>{
    private List<FindMessager> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    public FindAdapter(Context context,List<FindMessager> datas){
        this.mDatas=datas;
        this.mContext=context;
        inflater=LayoutInflater. from(mContext);
    }
    public int getItemCount() {
        return mDatas.size();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_view,parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        FindMessager da=mDatas.get(position);

        if(position%2==0){

            holder.tv.setBackgroundColor(Color.BLUE);

            holder.v.setBackgroundColor(Color.GRAY);

        }

        holder.tv.setText(da.getName());

        holder.msg.setText(da.getZt());

    }



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        TextView msg;
        View v;
        public ViewHolder(View view) {
            super(view);
            tv =  view.findViewById(R.id.txt_xs);
            msg =  view.findViewById(R.id.txt_msg);
            v = view;
        }

    }
}
