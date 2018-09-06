package com.example.kitapuygulama;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.example.kitapuygulama.Kitap_Ekle.readTextFile;


public class Kitap_Liste extends ActionBarActivity {


    Toolbar toolbar;
    private ProgressDialog pDialog;
    private static final String TAG = "RecyclerViewExample";

    Button kitaplisteAra;

    private List<FeedItem> feedItemList = new ArrayList<FeedItem>();

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    FeedItem item;
    String link;
    Bitmap snapshot = null;
    ImageView remoteImageView;
    InputStream inputStream;


    Handler mHandler;

    private static int current_page = 1;
    private int ival = 0;
    private int loadLimit = 40;

    Bundle gelen_veri;
    String Isbn;
    String Kelime;
    int spinner_secim_il_pos;
    String il;
    int spinner_secim_ilce_pos;
    String ilce;
    int spinner_secim_kategori_pos;
    int spinner_secim_siralama_pos;

    JSONObject il_ilçe_jsonobject;

    ArrayList<String> Iller = new ArrayList<>();

    ArrayList<String> Ilceler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitap__liste);

        /*
        if(!internetBaglantisiVarMi()){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("interneti açta ge");
            alertDialog.setButton("Ehhhh", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            alertDialog.show();
        }
        else{
            startActivity(new Intent("activitynize verdiğiniz action name buraya gelecek"));
        }
        */


        kitaplisteAra = (Button) findViewById(R.id.kitaplisteAra);

        kitaplisteAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle veri = new Bundle();
                veri.putString("Isbn", Isbn);
                veri.putString("Kelime", Kelime);
                veri.putInt("spinner_secim_il_pos", spinner_secim_il_pos);
                veri.putInt("spinner_secim_ilce_pos", spinner_secim_ilce_pos);
                veri.putInt("spinner_secim_kategori_pos", spinner_secim_kategori_pos);
                veri.putInt("spinner_secim_siralama_pos", spinner_secim_siralama_pos);

                /*
                Log.d("KİTAP LİSTE GÖNDERİLEN",
                                "   Isbn: " + Isbn +
                                "   Kelime:" + Kelime +
                                "   spinner_secim_il_pos: " + spinner_secim_il_pos +
                                "   spinner_secim_ilce_pos:" + spinner_secim_ilce_pos +
                                "   spinner_secim_kategori_pos: " + spinner_secim_kategori_pos +
                                "   spinner_secim_siralama_pos:" + spinner_secim_siralama_pos );*/

                startActivity(new Intent(getApplicationContext(), Arama_Yap.class).putExtras(veri));
            }
        });
/**/
        try{
            gelen_veri = getIntent().getExtras();
            Isbn = gelen_veri.getString("Isbn");
            Kelime = gelen_veri.getString("Kelime");
            spinner_secim_il_pos = gelen_veri.getInt("spinner_secim_il_pos");
            spinner_secim_ilce_pos = gelen_veri.getInt("spinner_secim_ilce_pos");
            spinner_secim_kategori_pos = gelen_veri.getInt("spinner_secim_kategori_pos");
            spinner_secim_siralama_pos = gelen_veri.getInt("spinner_secim_siralama_pos");
        }catch (Exception e){
            Isbn = "";
            Kelime = "";
            spinner_secim_il_pos = 0;
            spinner_secim_ilce_pos = 0;
            spinner_secim_kategori_pos = 0;
            spinner_secim_siralama_pos = 0;
        }

        /*
        Log.d("KİTAP LİSTE GELEN",
                        "   Isbn: " + Isbn +
                        "   Kelime:" + Kelime +
                        "   spinner_secim_il_pos: " + spinner_secim_il_pos +
                        "   spinner_secim_ilce_pos:" + spinner_secim_ilce_pos +
                        "   spinner_secim_kategori_pos: " + spinner_secim_kategori_pos +
                        "   spinner_secim_siralama_pos:" + spinner_secim_siralama_pos );
                        */

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        /* Initialize recyclerview */
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        /*Downloading data from below url*/

        adapter = new MyRecyclerAdapter(Kitap_Liste.this, feedItemList);

        //Json okuma il-ilçe
        String il_ilce = readTextFile(this, R.raw.ililce);
        try {
            il_ilçe_jsonobject = new JSONObject(il_ilce);
            for (int sayac = 0; sayac < il_ilçe_jsonobject.length(); sayac++){
                Iller.add((String) il_ilçe_jsonobject.getJSONObject(""+sayac).get("sehir"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //il'e değer ver
        if(spinner_secim_il_pos == 0){
            il = "";
        } else {
            il = Iller.get(spinner_secim_il_pos);
        }
        //ilce'ye değer ver
        if(spinner_secim_ilce_pos == 0){
            ilce = "";
        } else {
            Ilceler.clear();
            try {
                JSONArray ilceler_jsonobject = il_ilçe_jsonobject.getJSONObject("" + spinner_secim_il_pos).getJSONArray("ilceler");
                Ilceler.add("ilceler");
                for (int sayac2 = 0; sayac2 < ilceler_jsonobject.length(); sayac2++) {
                    Ilceler.add((String) ilceler_jsonobject.getJSONObject(sayac2).get("ilce"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ilce = Ilceler.get(spinner_secim_ilce_pos);
        }

        String url = "http://feyyazozhan.besaba.com/kitap_liste_cek.php";
        try {
            url += "?ilk=";
            url += ival;
            url += "&load_limit=";
            url += loadLimit;
            url += "&isbn=";
            url += URLEncoder.encode(Isbn,"UTF-8");
            url += "&kelime=";
            url += URLEncoder.encode(Kelime,"UTF-8");
            url += "&il=";
            url += URLEncoder.encode(il,"UTF-8");
            url += "&ilce=";
            url += URLEncoder.encode(ilce,"UTF-8");
            url += "&spinner_secim_kategori_pos=";
            url += spinner_secim_kategori_pos;
            url += "&spinner_secim_siralama_pos=";
            url += spinner_secim_siralama_pos;/**/
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        new AsyncHttpTask().execute(url);

        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {

            @Override
            public void onLoadMore(int current_page) {
                //loadLimit += 10;

                //String url = "http://feyyazozhan.besaba.com/kitap_liste_cek.php?ilk=" + ival + "&load_limit=" + loadLimit;
                String url = "http://feyyazozhan.besaba.com/kitap_liste_cek.php";
                try {
                    url += "?ilk=";
                    url += ival;
                    url += "&load_limit=";
                    url += loadLimit;
                    url += "&isbn=";
                    url += URLEncoder.encode(Isbn,"UTF-8");
                    url += "&kelime=";
                    url += URLEncoder.encode(Kelime,"UTF-8");
                    url += "&il=";
                    url += URLEncoder.encode(il,"UTF-8");
                    url += "&ilce=";
                    url += URLEncoder.encode(ilce,"UTF-8");
                    url += "&spinner_secim_kategori_pos=";
                    url += spinner_secim_kategori_pos;
                    url += "&spinner_secim_siralama_pos=";
                    url += spinner_secim_siralama_pos;/**/
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                new AsyncHttpTask().execute(url);


            }
        });
    }


    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //setProgressBarIndeterminateVisibility(true);
            /**/
            pDialog = new ProgressDialog(Kitap_Liste.this);
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
            //setProgressBarIndeterminateVisibility(false);
            /* Download complete. Lets update UI */

            if (result == 1) {
            /**/
                if(ival == 0) {

                    mRecyclerView.setAdapter(adapter);
                }
                else if(ival > 0){

                    adapter.notifyDataSetChanged();

                }
                ival+=loadLimit;

            } else {
                Log.e(TAG, "Failed to fetch data!");
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
        getMenuInflater().inflate(R.menu.menu_kitap__liste, menu);
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



    public Bitmap downloadBitmap(String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            urlConnection.disconnect();
            Log.w("ImageDownloader", "Error downloading image from " + url);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }


    boolean internetBaglantisiVarMi() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
