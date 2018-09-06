package com.example.kitapuygulama;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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


public class Kitap_Bilgileri extends ActionBarActivity {

    AlertDialog alertDialog;
    private ProgressDialog pDialog;
    Toolbar toolbar;

    int kitap_id;
    int kitap_uyeid;
    Bundle veri;

    TextView kitapbilgileriKitapAdi;
    ImageView kitapbilgileriImage;

    Button kitapbilgileriAra;
    Button kitapbilgileriİlanSahibi;
    Button kitapbilgileriAciklama;

    TextView kitapbilgileriFiyatBilgi;
    TextView kitapbilgileriIlanTarihiBilgi;
    TextView kitapbilgileriKonumBilgi;
    TextView kitapbilgileriKategoriBilgi;
    TextView kitapbilgileriYazarBilgi;
    TextView kitapbilgileriSayfaBilgi;
    TextView kitapbilgileriDilBilgi;
    TextView kitapbilgileriYayinciBilgi;
    TextView kitapbilgileriYayinlanmaTarihiBilgi;
    TextView kitapbilgileriIsbnBilgi;

    String kitapbilgileriKitapAdi_tut;
    Bitmap kitapbilgileriImage_tut;
    String kitapbilgileriFiyat_tut;
    String kitapbilgileriIlanTarihi_tut;
    String kitapbilgileriKonum_tut;
    String kitapbilgileriYazar_tut;
    String kitapbilgileriSayfa_tut;
    String kitapbilgileriDil_tut;
    String kitapbilgileriYayinci_tut;
    String kitapbilgileriYayinlanmaTarihi_tut;
    String kitapbilgileriIsbn_tut;
    int kitapbilgileriKategori_tut;

    String kitapbilgileriAciklama_tut;
    String kitapbilgileriUyeTelefon_tut;
    String kitapbilgileriUyeAd_tut;
    String kitapbilgileriUyeSoyad_tut;

    String[] Kategori={
            "Tüm Kategoriler","Bilim & Teknik","Çizgi Roman","Çocuk Kitapları",
            "Din","Edebiyat","Ekonomi & İş Dünyası","Felsefe & Düşünce",
            "Hukuk","Osmanlıca","Referans & Başvuru","Sağlık",
            "Sanat","Sınav ve Ders Kitapları","Spor","Tarih",
            "Toplum & Siyaset","Diğer & Çeşitli"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitap__bilgileri);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        kitapbilgileriKitapAdi = (TextView) findViewById(R.id.kitapbilgileriKitapAdi);
        kitapbilgileriImage = (ImageView) findViewById(R.id.kitapbilgileriImage);
        kitapbilgileriAra = (Button) findViewById(R.id.kitapbilgileriAra);
        kitapbilgileriİlanSahibi = (Button) findViewById(R.id.kitapbilgileriİlanSahibi);
        kitapbilgileriAciklama = (Button) findViewById(R.id.kitapbilgileriAciklama);

        kitapbilgileriFiyatBilgi = (TextView) findViewById(R.id.kitapbilgileriFiyatBilgi);
        kitapbilgileriIlanTarihiBilgi = (TextView) findViewById(R.id.kitapbilgileriIlanTarihiBilgi);
        kitapbilgileriKonumBilgi = (TextView) findViewById(R.id.kitapbilgileriKonumBilgi);
        kitapbilgileriKategoriBilgi = (TextView) findViewById(R.id.kitapbilgileriKategoriBilgi);
        kitapbilgileriYazarBilgi = (TextView) findViewById(R.id.kitapbilgileriYazarBilgi);
        kitapbilgileriSayfaBilgi = (TextView) findViewById(R.id.kitapbilgileriSayfaBilgi);
        kitapbilgileriDilBilgi = (TextView) findViewById(R.id.kitapbilgileriDilBilgi);
        kitapbilgileriYayinciBilgi = (TextView) findViewById(R.id.kitapbilgileriYayinciBilgi);
        kitapbilgileriYayinlanmaTarihiBilgi = (TextView) findViewById(R.id.kitapbilgileriYayinlanmaTarihiBilgi);
        kitapbilgileriIsbnBilgi = (TextView) findViewById(R.id.kitapbilgileriIsbnBilgi);

        veri = getIntent().getExtras();
        kitap_id = veri.getInt("Kitap_id");
        kitap_uyeid = veri.getInt("Kitap_uyeid");

        String kitap_bilgileri_url = "http://feyyazozhan.besaba.com/kitap_bilgileri_cek.php?kitap_id="+ kitap_id +"&kitap_uyeid="+ kitap_uyeid;
        new AsyncHttpTask().execute(kitap_bilgileri_url);

        kitapbilgileriAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:0" + kitapbilgileriUyeTelefon_tut));
                startActivity(i);
                //Toast.makeText(Kitap_Bilgileri.this,"tel : " + kitapbilgileriUyeTelefon_tut, Toast.LENGTH_SHORT).show();
            }
        });

        kitapbilgileriAciklama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(Kitap_Bilgileri.this).create();
                alertDialog.setMessage(kitapbilgileriAciklama_tut);
                alertDialog.setButton("tamam", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });

        kitapbilgileriİlanSahibi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle veri2 = new Bundle();
                veri2.putInt("Kitap_uyeid",kitap_uyeid);
                startActivity(new Intent(getApplicationContext(),Uye_Profil.class).putExtras(veri2));
            }
        });

    }


    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(Kitap_Bilgileri.this);
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
                kitapbilgileriKitapAdi.setText(kitapbilgileriKitapAdi_tut);
                kitapbilgileriImage.setImageBitmap(kitapbilgileriImage_tut);
                kitapbilgileriFiyatBilgi.setText(kitapbilgileriFiyat_tut);
                kitapbilgileriIlanTarihiBilgi.setText(kitapbilgileriIlanTarihi_tut);
                kitapbilgileriKonumBilgi.setText(kitapbilgileriKonum_tut);
                kitapbilgileriKategoriBilgi.setText(Kategori[kitapbilgileriKategori_tut]);
                kitapbilgileriYazarBilgi.setText(kitapbilgileriYazar_tut);
                kitapbilgileriSayfaBilgi.setText(kitapbilgileriSayfa_tut);
                kitapbilgileriDilBilgi.setText(kitapbilgileriDil_tut);
                kitapbilgileriYayinciBilgi.setText(kitapbilgileriYayinci_tut);
                kitapbilgileriYayinlanmaTarihiBilgi.setText(kitapbilgileriYayinlanmaTarihi_tut);
                kitapbilgileriIsbnBilgi.setText(kitapbilgileriIsbn_tut);

                kitapbilgileriİlanSahibi.setText(kitapbilgileriUyeAd_tut + " " + kitapbilgileriUyeSoyad_tut);

            } else {
                Log.e("kitap bilgileri", "Failed to fetch data!");
            }
        }
    }

    private int parseResult(String kitap_bilgileri) {
        try {
            JSONObject sonuc = new JSONObject(kitap_bilgileri);
            JSONArray rows = sonuc.getJSONArray("rows");
            JSONArray c = rows.getJSONObject(0).getJSONArray("c");

            kitapbilgileriIsbn_tut = (String) c.getJSONObject(2).get("v");
            kitapbilgileriKitapAdi_tut = (String) c.getJSONObject(3).get("v");
            kitapbilgileriAciklama_tut = (String) c.getJSONObject(4).get("v");
            kitapbilgileriYazar_tut = (String) c.getJSONObject(5).get("v");
            kitapbilgileriSayfa_tut = "" + (int) c.getJSONObject(6).get("v");
            kitapbilgileriYayinci_tut = (String) c.getJSONObject(7).get("v");
            kitapbilgileriYayinlanmaTarihi_tut = (String) c.getJSONObject(8).get("v");
            kitapbilgileriKategori_tut = (int) c.getJSONObject(9).get("v");
            kitapbilgileriDil_tut = (String) c.getJSONObject(10).get("v");
            kitapbilgileriImage_tut = BitmapFactory.decodeStream((InputStream) new URL((String) c.getJSONObject(11).get("v")).getContent());
            kitapbilgileriFiyat_tut = ""+ (int) c.getJSONObject(12).get("v");
            kitapbilgileriUyeTelefon_tut = (String) c.getJSONObject(13).get("v");
            kitapbilgileriKonum_tut = (String) c.getJSONObject(14).get("v") + "," + (String) c.getJSONObject(15).get("v");
            kitapbilgileriIlanTarihi_tut = (String) c.getJSONObject(16).get("v");
            kitapbilgileriUyeAd_tut = (String) c.getJSONObject(17).get("v");
            kitapbilgileriUyeSoyad_tut = (String) c.getJSONObject(18).get("v");

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("kitap bilgileri",e.getLocalizedMessage());
            return 0;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("kitap bilgileri",e.getLocalizedMessage());
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("kitap bilgileri",e.getLocalizedMessage());
            return 0;
        }/**/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kitap__bilgileri, menu);
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
