package com.example.kitapuygulama;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static com.example.kitapuygulama.Information.getJsonFromUrl;

public class Kitap_Ekle extends ActionBarActivity {

    //karşıya kitap yükle
    InputStream inputStream;
    int contentLength;
    ArrayList<NameValuePair> nameValuePairs;
    String the_string_response;
    String res;

    Bitmap Resim_Tut;

    Toolbar toolbar;
    ProgressDialog pDialog;
    AlertDialog alertDialog;
    JSONObject jsonKitapEkleSonuc;
    private static final String TAG = "KitapEkleExample";
    int Activity_buton_durum = 0;
    String URL = "http://feyyazozhan.besaba.com/kitap_ekle.php";

    int Uye_id = Uye_Giris.UYEID;
    JSONObject il_ilçe_jsonobject;

    String[] Kategori={
            "Kategori Seçiniz...","Bilim & Teknik","Çizgi Roman","Çocuk Kitapları",
            "Din","Edebiyat","Ekonomi & İş Dünyası","Felsefe & Düşünce",
            "Hukuk","Osmanlıca","Referans & Başvuru","Sağlık",
            "Sanat","Sınav ve Ders Kitapları","Spor","Tarih",
            "Toplum & Siyaset","Diğer & Çeşitli"};
    ArrayAdapter<String> dataAdapterKategori;

    ArrayList<String> Iller = new ArrayList<>();
    ArrayAdapter<String> dataAdapterIller;

    ArrayList<String> Ilceler = new ArrayList<>();
    ArrayAdapter<String> dataAdapterIlceler;

    Spinner kitapekleKategoriSpinner1;
    Spinner kitapekleIlSpinner2;
    Spinner kitapekleIlceSpinner3;

    Button kitapekleBarcode;
    ImageView kitapekleImage;
    EditText kitapekleIsbn;
    ImageButton kitapekleBarcodeOku;
    EditText kitapekleKitapAdi;
    EditText kitapekleAciklama;
    EditText kitapekleYazar;
    EditText kitapekleSayfa;
    EditText kitapekleYayinci;
    EditText kitapekleYayinlanmaTarih;
    EditText kitapekleDil;
    EditText kitapekleTelefon;
    EditText kitapekleFiyat;
    Button kitapekleSatisListesineEkle;

    String Resim_Link_Tut="";

    Bitmap kitapekleImage_tut;
    String kitapekleIsbn_tut;
    String kitapekleKitapAdi_tut;
    String kitapekleAciklama_tut;
    String kitapekleYazar_tut;
    String kitapekleSayfa_tut;
    String kitapekleYayinci_tut;
    String kitapekleYayinlanmaTarih_tut;
    String kitapekleDil_tut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitap__ekle);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        kitapekleBarcode = (Button) findViewById(R.id.kitapekleBarcode);
        kitapekleImage = (ImageView) findViewById(R.id.kitapekleImage);
        kitapekleKategoriSpinner1 = (Spinner) findViewById(R.id.kitapekleSpinner1);
        kitapekleIsbn = (EditText) findViewById(R.id.kitapekleIsbn);
        kitapekleBarcodeOku = (ImageButton) findViewById(R.id.kitapekleBarcodeOku);
        kitapekleKitapAdi = (EditText) findViewById(R.id.kitapekleKitapAdi);
        kitapekleAciklama = (EditText) findViewById(R.id.kitapekleAciklama);
        kitapekleYazar = (EditText) findViewById(R.id.kitapekleYazar);
        kitapekleSayfa = (EditText) findViewById(R.id.kitapekleSayfa);
        kitapekleYayinci = (EditText) findViewById(R.id.kitapekleYayinci);
        kitapekleYayinlanmaTarih = (EditText) findViewById(R.id.kitapekleYayinlanmaTarih);
        kitapekleDil = (EditText) findViewById(R.id.kitapekleDil);
        kitapekleTelefon = (EditText) findViewById(R.id.kitapekleTelefon);
        kitapekleIlSpinner2 = (Spinner) findViewById(R.id.kitapekleSpinner2);
        kitapekleIlceSpinner3 = (Spinner) findViewById(R.id.kitapekleSpinner3);
        kitapekleFiyat = (EditText) findViewById(R.id.kitapekleFiyat);
        kitapekleSatisListesineEkle = (Button) findViewById(R.id.kitapekleSatisListesineEkle);


        //kitapekleBarcode
        kitapekleBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_buton_durum = 1;
                IntentIntegrator integrator = new IntentIntegrator(Kitap_Ekle.this);
                integrator.initiateScan();
            }
        });

        //kitapekleImage
        kitapekleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_buton_durum = 0;
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        //kitapekleBarcodeOku
        kitapekleBarcodeOku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_buton_durum = 2;
                IntentIntegrator integrator = new IntentIntegrator(Kitap_Ekle.this);
                integrator.initiateScan();
            }
        });

        //kitapekleKategoriSpinner1
        dataAdapterKategori = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Kategori);
        dataAdapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kitapekleKategoriSpinner1.setAdapter(dataAdapterKategori);

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

        //kitapekleIlSpinner2
        dataAdapterIller = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Iller);
        dataAdapterIller.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kitapekleIlSpinner2.setAdapter(dataAdapterIller);

        //kitapekleIlceSpinner3
        dataAdapterIlceler = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Ilceler);
        dataAdapterIlceler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kitapekleIlceSpinner3.setAdapter(dataAdapterIlceler);

        //il tıklandığında ilçe ayarla
        kitapekleIlSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Ilceler.clear();
                try {
                    JSONArray ilceler_jsonobject = il_ilçe_jsonobject.getJSONObject("" + position).getJSONArray("ilceler");
                    for (int sayac2 = 0; sayac2 < ilceler_jsonobject.length(); sayac2++){
                        Ilceler.add((String) ilceler_jsonobject.getJSONObject(sayac2).get("ilce"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dataAdapterIlceler = new ArrayAdapter<String>(Kitap_Ekle.this, android.R.layout.simple_spinner_item,Ilceler);
                dataAdapterIlceler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                kitapekleIlceSpinner3.setAdapter(dataAdapterIlceler);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Kitap ekleme butonu
        kitapekleSatisListesineEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Resim_Link_Tut = "image_" + ((int) new Date().getTime()) + "_" + new Random().nextInt((int) new Date().getTime()) + ".jpg";

                //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.id.kitapekleImage);
                Bitmap bitmap = Resim_Tut;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
                byte [] byte_arr = stream.toByteArray();
                String image_str = Base64.encodeBytes(byte_arr);
                nameValuePairs = new  ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("image", image_str));

                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try{
                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("http://feyyazozhan.besaba.com/uploads/upload_image.php?link=" + URLEncoder.encode(Resim_Link_Tut,"UTF-8"));
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            the_string_response = convertResponseToString(response);
                            /*
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    Toast.makeText(Kitap_Ekle.this, "Response " + the_string_response, Toast.LENGTH_LONG).show();
                                }
                            });
                            */

                        }catch(final Exception e){
                            /*
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    Toast.makeText(Kitap_Ekle.this, "ERROR " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                            */
                            Log.d("kitap ekle hata resim","Error in http connection "+e.toString());
                        }
                    }
                });
                t.start();



                try {
                    URL = URL
                            + "?keKategori=" + kitapekleKategoriSpinner1.getSelectedItemPosition()
                            + "&keIsbn=" + URLEncoder.encode(kitapekleIsbn.getText().toString(),"UTF-8")
                            + "&keKitapAdi=" + URLEncoder.encode(kitapekleKitapAdi.getText().toString(),"UTF-8")
                            + "&keAciklama=" + URLEncoder.encode(kitapekleAciklama.getText().toString(),"UTF-8")
                            + "&keYazar=" + URLEncoder.encode(kitapekleYazar.getText().toString(),"UTF-8")
                            + "&keSayfa=" + URLEncoder.encode(kitapekleSayfa.getText().toString(),"UTF-8")
                            + "&keYayinci=" + URLEncoder.encode(kitapekleYayinci.getText().toString(),"UTF-8")
                            + "&keYayinlanmaTarih=" + URLEncoder.encode(kitapekleYayinlanmaTarih.getText().toString(),"UTF-8")
                            + "&keDil=" + URLEncoder.encode(kitapekleDil.getText().toString(),"UTF-8")
                            + "&keTelefon=" + URLEncoder.encode(kitapekleTelefon.getText().toString(),"UTF-8")
                            + "&keIl=" + URLEncoder.encode(kitapekleIlSpinner2.getSelectedItem().toString(),"UTF-8")
                            + "&keIlce=" + URLEncoder.encode(kitapekleIlceSpinner3.getSelectedItem().toString(),"UTF-8")
                            + "&keFiyat=" + URLEncoder.encode(kitapekleFiyat.getText().toString(),"UTF-8")
                            + "&keUyeId=" + URLEncoder.encode(String.valueOf(Uye_id),"UTF-8")
                            + "&keResim=" + URLEncoder.encode("http://feyyazozhan.besaba.com/uploads/" + Resim_Link_Tut,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //Log.d("Kita ekle eeeee","gelen url : "+URL);
                new UrlTask_KitapEkle().execute(URL);
            }
        });

    }


    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(Kitap_Ekle.this);
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

                    JSONObject jsonObject = new JSONObject(response.toString());
                    if((int)jsonObject.get("totalItems") != 0) {

                        String Kitap_id = (String) jsonObject.getJSONArray("items").getJSONObject(0).get("id");
                        String kitap_url2 = "https://www.googleapis.com/books/v1/volumes/" + Kitap_id +
                                "?key=AIzaSyDb9bL83r35Og-sJgzkqGch9pT8pdFKUuc";
                        HttpURLConnection urlConnection2 = null;
                        URL url2 = new URL(kitap_url2);
                        urlConnection2 = (HttpURLConnection) url2.openConnection();
                        urlConnection2.setRequestMethod("GET");
                        int statusCode2 = urlConnection2.getResponseCode();
                        if(statusCode2 == 200){
                            BufferedReader r2 = new BufferedReader(new InputStreamReader(urlConnection2.getInputStream()));
                            StringBuilder response2 = new StringBuilder();
                            String line2;
                            while ((line2 = r2.readLine()) != null) {
                                response2.append(line2);
                            }
                            result = parseResult(response2.toString());
                        }
                        else{
                            result = 0;
                        }
                    }
                    else{
                        result = 0;
                    }

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
                kitapekleImage.setImageBitmap(kitapekleImage_tut);
                kitapekleIsbn.setText(kitapekleIsbn_tut);
                kitapekleKitapAdi.setText(kitapekleKitapAdi_tut);
                kitapekleAciklama.setText(kitapekleAciklama_tut);
                kitapekleYazar.setText(kitapekleYazar_tut);
                kitapekleSayfa.setText(kitapekleSayfa_tut);
                kitapekleYayinci.setText(kitapekleYayinci_tut);
                kitapekleYayinlanmaTarih.setText(kitapekleYayinlanmaTarih_tut);
                kitapekleDil.setText(kitapekleDil_tut);
            } else {
                Log.e(TAG, "Failed to fetch data!");
            }
        }
    }

    private int parseResult(String kitap_bilgileri) {
        try {
            JSONObject sonuc = new JSONObject(kitap_bilgileri);
            JSONObject volumeInfo = sonuc.getJSONObject("volumeInfo");

            kitapekleImage_tut = BitmapFactory.decodeStream((InputStream) new URL((String) volumeInfo.getJSONObject("imageLinks").get("thumbnail")).getContent());
            Resim_Tut = kitapekleImage_tut;
            kitapekleIsbn_tut = (String) volumeInfo.getJSONArray("industryIdentifiers").getJSONObject(1).get("identifier");
            kitapekleKitapAdi_tut = (String) volumeInfo.get("title");
            kitapekleAciklama_tut = (String) volumeInfo.get("subtitle");
            kitapekleYazar_tut = (String) volumeInfo.getJSONArray("authors").get(0);
            kitapekleSayfa_tut = "" + (int) volumeInfo.get("pageCount");
            kitapekleYayinci_tut = (String) volumeInfo.get("publisher");
            kitapekleYayinlanmaTarih_tut = (String) volumeInfo.get("publishedDate");
            kitapekleDil_tut = (String) volumeInfo.get("language");

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG,e.getLocalizedMessage());
            return 0;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(TAG,e.getLocalizedMessage());
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,e.getLocalizedMessage());
            return 0;
        }/**/
    }

    class UrlTask_KitapEkle extends AsyncTask<String, Void, Void> {
        String strJson = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Kitap_Ekle.this);
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
                jsonKitapEkleSonuc = new JSONObject(strJson);
                int durum = (int)jsonKitapEkleSonuc.get("durum");
                if(durum == 1){
                    alertDialog = new AlertDialog.Builder(Kitap_Ekle.this).create();
                    alertDialog.setMessage("Kayıt İşlemi Gerçekleşti");
                    alertDialog.setButton("tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                            onBackPressed();
                        }
                    });
                    alertDialog.show();
                } else {
                    alertDialog = new AlertDialog.Builder(Kitap_Ekle.this).create();
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

    //json okuma fonksiyonu
    public static String readTextFile(Context ctx, int resId){
        InputStream inputStream = ctx.getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader bufferedreader = new BufferedReader(inputreader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try
        {
            while (( line = bufferedreader.readLine()) != null)
            {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
        }
        catch (IOException e)
        {
            return null;
        }
        return stringBuilder.toString();
    }


    public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException{

        res = "";
        StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        contentLength = (int) response.getEntity().getContentLength(); //getting content length…..
        /*
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(Kitap_Ekle.this, "contentLength : " + contentLength, Toast.LENGTH_LONG).show();
            }
        });

*/
        if (contentLength < 0){
        }
        else{
            byte[] data = new byte[512];
            int len = 0;
            try
            {
                while (-1 != (len = inputStream.read(data)) )
                {
                    buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbuffer…..
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                inputStream.close(); // closing the stream…..
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            res = buffer.toString();	 // converting stringbuffer to string…..

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(Kitap_Ekle.this, "Result : " + res, Toast.LENGTH_LONG).show();
                }
            });
            //System.out.println("Response => " +  EntityUtils.toString(response.getEntity()));
        }
        return res;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(Activity_buton_durum == 0){
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            Bitmap bitmap = Bitmap.createScaledBitmap(bp, 128, 185, true);
            Resim_Tut = bitmap;
            kitapekleImage.setImageBitmap(bitmap);
        }

        if(Activity_buton_durum == 1){
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                String Kitap_barcode = result.getContents();
                String kitap_url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + Kitap_barcode;
                new AsyncHttpTask().execute(kitap_url);
            }
        }

        if(Activity_buton_durum == 2) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                kitapekleIsbn.setText(result.getContents());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kitap__ekle, menu);
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
