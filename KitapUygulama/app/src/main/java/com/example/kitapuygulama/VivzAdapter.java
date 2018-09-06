package com.example.kitapuygulama;

/**
 * Created by Feyyaz on 25.03.2015.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Feyyaz on 23.03.2015.
 */
public class VivzAdapter extends RecyclerView.Adapter<VivzAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();
    Context context;
    ClickListener clickListener;

    public void delete(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }

    public VivzAdapter(Context context, List<Information> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current = data.get(position);
        holder.buton.setText(current.title);
        //holder.title.setText(current.title);
        //holder.icon.setImageResource(current.iconId);
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //TextView title;
        //ImageView icon;
        Button buton;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            buton = (Button) itemView.findViewById(R.id.listButon);
            //title = (TextView) itemView.findViewById(R.id.listText);
            //icon = (ImageView) itemView.findViewById(R.id.listIcon);

        }

        @Override
        public void onClick(View v) {
            if(clickListener!=null){
                clickListener.itemClicked(v,getPosition());
            }
        }
    }

    public interface ClickListener{
        public void itemClicked(View view, int position);
    }

}
