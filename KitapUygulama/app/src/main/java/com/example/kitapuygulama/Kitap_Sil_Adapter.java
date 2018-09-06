package com.example.kitapuygulama;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import static com.example.kitapuygulama.Information.getJsonFromUrl;

/**
 * Created by Feyyaz on 10.05.2015.
 */
public class Kitap_Sil_Adapter extends RecyclerView.Adapter<Kitap_Sil_Adapter.Kitap_Sil_Holder> {

    ProgressDialog pDialog;
    AlertDialog alertDialog;
    JSONObject jsonUyeSonuc;

    private LayoutInflater inflater;
    List<FeedItem> data = Collections.emptyList();
    Context context;

    ImageLoader imageLoader;

    int previousPosition;

    public void delete(final int position) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Silinsin Mi?");
        alertDialog.setMessage("Silmek İçin İşlemi Onayla");
        alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new UrlTask_Kitap_Sil().execute("http://feyyazozhan.besaba.com/kitap_sil_sil.php?kitap_id=" + data.get(position).getKitap_id());
                data.remove(position);
                notifyItemRemoved(position);
            }
        });

        alertDialog.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public Kitap_Sil_Adapter(Context context, List<FeedItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        imageLoader = new ImageLoader(context.getApplicationContext());
    }

    @Override
    public Kitap_Sil_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row, parent, false);
        Kitap_Sil_Holder holder = new Kitap_Sil_Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Kitap_Sil_Holder holder, int position) {
        FeedItem current = data.get(position);
        holder.kitap_adi.setText(current.getKitap_adi());

        holder.kitap_adres.setText(current.getKitap_adres());
        holder.kitap_fiyat.setText(current.getKitap_fiyat());

/*
        if(position>previousPosition){
            AnimationUtils.animate(holder,true);
        } else {
            AnimationUtils.animate(holder,false);
        }
        previousPosition = position;*/


        imageLoader.DisplayImage(current.getKitap_resim_link(), holder.kitap_resim);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) { return position; }


    class Kitap_Sil_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView kitap_adi;

        TextView kitap_adres;
        TextView kitap_fiyat;
        ImageView kitap_resim;


        public Kitap_Sil_Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            kitap_adi = (TextView) itemView.findViewById(R.id.kitap_adi);

            kitap_adres = (TextView) itemView.findViewById(R.id.kitap_adres);
            kitap_fiyat = (TextView) itemView.findViewById(R.id.kitap_fiyat);
            kitap_resim = (ImageView) itemView.findViewById(R.id.kitap_resim);


        }

        @Override
        public void onClick(View v) {
            delete(getPosition());
        }
    }


    class UrlTask_Kitap_Sil extends AsyncTask<String, Void, Void> {
        String strJson = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Siliniyor.Lütfen Bekleyiniz...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... url) {
            strJson = getJsonFromUrl(url[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            try {
                jsonUyeSonuc = new JSONObject(strJson);
                int durum = (int)jsonUyeSonuc.get("durum");
                if(durum == 1){
                    alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setMessage("Silme İşlemi Gerçekleşti");
                    alertDialog.setButton("tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                        }
                    });
                    alertDialog.show();
                } else {
                    alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setMessage("Tekrar Deneyiniz");
                    alertDialog.setButton("tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
