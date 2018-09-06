package com.example.kitapuygulama;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.kitapuygulama.Information.getJsonFromUrl;


public class Uye_Ol extends ActionBarActivity {

    Toolbar toolbar;

    EditText uyeolMail;
    EditText uyeolAd;
    EditText uyeolSoyad;
    EditText uyeolSifre;
    Button uyeolButon;

    ProgressDialog pDialog;
    AlertDialog alertDialog;

    String URL = "http://feyyazozhan.besaba.com/uye_ol.php";
    JSONObject jsonUyeSonuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye__ol);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uyeolMail = (EditText) findViewById(R.id.uyeolMail);
        uyeolAd = (EditText) findViewById(R.id.uyeolAd);
        uyeolSoyad = (EditText) findViewById(R.id.uyeolSoyad);
        uyeolSifre = (EditText) findViewById(R.id.uyeolSifre);
        uyeolButon = (Button) findViewById(R.id.uyeolButon);

        uyeolButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!uyeolMail.getText().toString().trim().isEmpty()
                        && !uyeolAd.getText().toString().trim().isEmpty()
                        && !uyeolSoyad.getText().toString().trim().isEmpty()
                        && !uyeolSifre.getText().toString().trim().isEmpty()
                        ) {

                    try {
                        URL = URL
                                + "?uyeolMail=" + URLEncoder.encode(String.valueOf(uyeolMail.getText()),"UTF-8")
                                + "&uyeolAd=" + URLEncoder.encode(String.valueOf(uyeolAd.getText()),"UTF-8")
                                + "&uyeolSoyad=" + URLEncoder.encode(String.valueOf(uyeolSoyad.getText()),"UTF-8")
                                + "&uyeolSifre=" + URLEncoder.encode(String.valueOf(uyeolSifre.getText()),"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    new UrlTask().execute(URL);

                }
                else {
                    alertDialog = new AlertDialog.Builder(Uye_Ol.this).create();
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
    }

    class UrlTask extends AsyncTask<String, Void, Void> {
        String strJson = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Uye_Ol.this);
            pDialog.setMessage("Kayıt Yapılıyor.Lütfen Bekleyiniz...");
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
                    alertDialog = new AlertDialog.Builder(Uye_Ol.this).create();
                    alertDialog.setMessage("Kayıt İşlemi Gerçekleşti");
                    alertDialog.setButton("tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                            onBackPressed();
                        }
                    });
                    alertDialog.show();
                } else {
                    alertDialog = new AlertDialog.Builder(Uye_Ol.this).create();
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
        getMenuInflater().inflate(R.menu.menu_uye__ol, menu);
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
