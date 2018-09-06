package com.example.kitapuygulama;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Kitap_Sil extends ActionBarActivity {

    Toolbar toolbar;
    private ProgressDialog pDialog;

    private RecyclerView mRecyclerView;
    private Kitap_Sil_Adapter adapter;
    LinearLayoutManager linearLayoutManager;

    private List<FeedItem> feedItemList = new ArrayList<FeedItem>();
    FeedItem item;

    int uye_id=Uye_Giris.UYEID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitap__sil);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_kitap_sil);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        adapter = new Kitap_Sil_Adapter(Kitap_Sil.this, feedItemList);

        String url = "http://feyyazozhan.besaba.com/kitap_sil_cek.php?uye_id="+ uye_id;
        new AsyncHttpTask_Kitap_Sil_Cek().execute(url);
    }


    public class AsyncHttpTask_Kitap_Sil_Cek extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //setProgressBarIndeterminateVisibility(true);
            /**/
            pDialog = new ProgressDialog(Kitap_Sil.this);
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
                Log.d("Kitap_Sil exception", e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {

            pDialog.dismiss();
            //setProgressBarIndeterminateVisibility(false);
            /* Download complete. Lets update UI */

            if (result == 1) {
                mRecyclerView.setAdapter(adapter);
            } else {
                Log.e("Kitap_Sil exception", "Failed to fetch data!");
            }
        }
    }

    private int parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray rows = response.optJSONArray("rows");

            /*Initialize array if null*/
            if (null == feedItemList) {
                feedItemList = new ArrayList<FeedItem>();
            }

            for (int i = 0; i < rows.length(); i++) {
                JSONObject row = rows.optJSONObject(i);
                JSONArray c = row.getJSONArray("c");

                item = new FeedItem();

                int aa=(int) c.getJSONObject(0).get("v");

                item.setKitap_id((int) c.getJSONObject(0).get("v"));
                item.setKitap_uyeid((int) c.getJSONObject(4).get("v"));
                item.setKitap_adi((String) c.getJSONObject(1).get("v"));
                item.setKitap_adres((String) c.getJSONObject(5).get("v") + "," + (String) c.getJSONObject(6).get("v"));
                item.setKitap_fiyat((int) c.getJSONObject(3).get("v") + "TL");
                item.setKitap_resim_link((String) c.getJSONObject(2).get("v"));
                item.setKitap_resim(null);

                feedItemList.add(item);

            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kitap__sil, menu);
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
