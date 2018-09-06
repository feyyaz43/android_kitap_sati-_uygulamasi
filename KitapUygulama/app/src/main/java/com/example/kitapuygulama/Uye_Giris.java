package com.example.kitapuygulama;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.example.kitapuygulama.Information.getJsonFromUrl;


public class Uye_Giris extends ActionBarActivity {

    EditText girisMail;
    EditText girisSifre;
    Button girisButon;
    Button girisUnuttum;
    Button girisUyeol;

    static int UYEID=0;
    Button geciciButon;

    Toolbar toolbar;
    AlertDialog alertDialog;
    ProgressDialog pDialog;

    String URL = "http://feyyazozhan.besaba.com/uye_giris.php";
    JSONObject jsonGirisSonuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye__giris);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        girisMail = (EditText) findViewById(R.id.girisMail);
        girisSifre = (EditText) findViewById(R.id.girisSifre);
        girisButon = (Button) findViewById(R.id.girisButon);
        girisUnuttum = (Button) findViewById(R.id.girisUnuttum);
        girisUyeol = (Button) findViewById(R.id.girisUyeol);

        girisButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!girisMail.getText().toString().trim().isEmpty() && !girisSifre.getText().toString().trim().isEmpty()){
                    try {
                        URL = URL
                                + "?girisMail=" + URLEncoder.encode(String.valueOf(girisMail.getText()), "UTF-8")
                                + "&girisSifre=" + URLEncoder.encode(String.valueOf(girisSifre.getText()), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    new UrlTask_Giris().execute(URL);
                }
                else {
                    alertDialog = new AlertDialog.Builder(Uye_Giris.this).create();
                    alertDialog.setMessage("Boş Bırakmayınız!");
                    alertDialog.setButton("tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

        girisUnuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Uye_Unuttum.class));
            }
        });

        girisUyeol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Uye_Ol.class));
            }
        });
    }


    class UrlTask_Giris extends AsyncTask<String, Void, Void> {
        String strJson = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Uye_Giris.this);
            pDialog.setMessage("Giriş Yapılıyor. Lütfen Bekleyiniz...");
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
                jsonGirisSonuc = new JSONObject(strJson);
                int durum = (int)jsonGirisSonuc.get("durum");
                if(durum == 1){
                    UYEID = (int)jsonGirisSonuc.get("uye_id");
                    alertDialog = new AlertDialog.Builder(Uye_Giris.this).create();
                    alertDialog.setMessage("Giriş Yapıldı");
                    alertDialog.setButton("tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                            onBackPressed();
                        }
                    });
                    alertDialog.show();
                } else {
                    alertDialog = new AlertDialog.Builder(Uye_Giris.this).create();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_uye__giris, menu);
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
