package com.example.kitapuygulama;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Uye_Profil extends ActionBarActivity {

    Toolbar toolbar;
    private ProgressDialog pDialog;
    Bundle veri2;
    int kitap_uyeid;

    TextView uyeprofilAdSoyad;
    TextView uyeprofilUyelikTarihi;
    TextView uyeprofilIlanSayisi;

    String uyeprofilAd_tut;
    String uyeprofilSoyad_tut;
    String uyeprofilUyelikTarihi_tut;
    String uyeprofilIlanSayisi_tut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye__profil);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uyeprofilAdSoyad = (TextView) findViewById(R.id.uyeprofilAdSoyad);
        uyeprofilUyelikTarihi = (TextView) findViewById(R.id.uyeprofilUyelikTarihi);
        uyeprofilIlanSayisi = (TextView) findViewById(R.id.uyeprofilIlanSayisi);

        veri2 = getIntent().getExtras();
        kitap_uyeid = veri2.getInt("Kitap_uyeid");

        String uye_profil_url = "http://feyyazozhan.besaba.com/uye_profil_cek.php?uye_id="+ kitap_uyeid;
        new AsyncHttpTask().execute(uye_profil_url);
    }


    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(Uye_Profil.this);
            pDialog.setMessage("Yükleniyor. Lütfen Bekleyiniz...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {

            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;
            try {
                /* forming th java.net.URL object */
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                /* for Get request */
                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();
                /* 200 represents HTTP OK */
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }

                    result = parseResult(response.toString());

                }else{
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                //Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            pDialog.dismiss();
            if (result == 1) {
                uyeprofilAdSoyad.setText(uyeprofilAd_tut + " " + uyeprofilSoyad_tut);
                uyeprofilUyelikTarihi.setText(uyeprofilUyelikTarihi_tut);
                uyeprofilIlanSayisi.setText(uyeprofilIlanSayisi_tut);

            } else {
                Log.e("uye profil", "Failed to fetch data!");
            }
        }
    }

    private int parseResult(String uye_profil) {
        try {
            JSONObject sonuc = new JSONObject(uye_profil);
            JSONArray rows = sonuc.getJSONArray("rows");
            JSONArray c = rows.getJSONObject(0).getJSONArray("c");

            uyeprofilAd_tut = (String) c.getJSONObject(1).get("v");
            uyeprofilSoyad_tut = (String) c.getJSONObject(2).get("v");
            uyeprofilUyelikTarihi_tut = (String) c.getJSONObject(5).get("v");
            uyeprofilIlanSayisi_tut = ""+(int) c.getJSONObject(6).get("v");

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("uye profil",e.getLocalizedMessage());
            return 0;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_uye__profil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
