package com.example.kitapuygulama;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpStatus;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * Created by Feyyaz on 23.03.2015.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<FeedItem> data = Collections.emptyList();
    Context context;
    //ClickListener clickListener;
    int previousPosition;
    ImageLoader imageLoader;

    public void delete(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }



    public MyRecyclerAdapter(Context context, List<FeedItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        imageLoader = new ImageLoader(context.getApplicationContext());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FeedItem current = data.get(position);
        holder.kitap_adi.setText(current.getKitap_adi());
        holder.kitap_adres.setText(current.getKitap_adres());
        holder.kitap_fiyat.setText(current.getKitap_fiyat());

 /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("burasııııı","burda");
            }
        });
*/

        /*
        if(position>previousPosition){
            AnimationUtils.animate(holder,true);
        } else {
            AnimationUtils.animate(holder,false);
        }
        previousPosition = position;
*/
        imageLoader.DisplayImage(current.getKitap_resim_link(),holder.kitap_resim);

    }

/*
    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }
*/
    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) { return position; }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView kitap_adi;
        TextView kitap_adres;
        TextView kitap_fiyat;
        ImageView kitap_resim;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            kitap_adi = (TextView) itemView.findViewById(R.id.kitap_adi);
            kitap_adres = (TextView) itemView.findViewById(R.id.kitap_adres);
            kitap_fiyat = (TextView) itemView.findViewById(R.id.kitap_fiyat);
            kitap_resim = (ImageView) itemView.findViewById(R.id.kitap_resim);

        }

        @Override
        public void onClick(View v) {

            Bundle veri = new Bundle();
            veri.putInt("Kitap_id",data.get(getPosition()).getKitap_id());
            veri.putInt("Kitap_uyeid",data.get(getPosition()).getKitap_uyeid());

            context.startActivity(new Intent(context,Kitap_Bilgileri.class).putExtras(veri));

            /*
            if(clickListener!=null){
                clickListener.itemClicked(v,getPosition());
            }
            */
        }
    }

    /*
    public interface ClickListener{
        public void itemClicked(View view, int position);
    }
    */
}
