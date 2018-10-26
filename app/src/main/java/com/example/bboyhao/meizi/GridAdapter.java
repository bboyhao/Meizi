package com.example.bboyhao.meizi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private Context myContext;
    private List<Meizi> meizis;

    public static interface OnRecyclerViewClickListener{
        void onItemClick(View view);
        void onItemLongClick(View view);
    }

    private OnRecyclerViewClickListener myOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewClickListener listener){
        myOnItemClickListener = listener;
    }

    public GridAdapter(Context c, List<Meizi> list){
        myContext = c;
        meizis = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(myContext).inflate(R.layout.grid_meizi_item, viewGroup, false);
            MyViewHolder holder = new MyViewHolder(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Picasso.get().load(meizis.get(i).getUrl()).into(((MyViewHolder) myViewHolder).iv);
    }

    @Override
    public int getItemCount() {
        return meizis.size();
    }

    @Override
    public void onClick(View v){
        if(myOnItemClickListener != null)
            myOnItemClickListener.onItemClick(v);
    }

    @Override
    public boolean onLongClick(View v){
        if(myOnItemClickListener != null)
            myOnItemClickListener.onItemLongClick(v);
        return false;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageButton iv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = (ImageButton) itemView.findViewById(R.id.meizi_photo);
        }

    }


}
