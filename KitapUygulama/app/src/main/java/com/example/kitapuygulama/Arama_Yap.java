package com.example.kitapuygulama;

import android.content.Intent;
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
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.kitapuygulama.Kitap_Ekle.readTextFile;


public class Arama_Yap extends ActionBarActivity {

    Toolbar toolbar;
    int Activity_buton_durum = 0;

    EditText aramayapIsbn;
    ImageButton aramayapBarcodeOku;
    EditText aramayapKelime;
    Spinner aramayapSpinnerIl;
    Spinner aramayapSpinnerIlce;
    Spinner aramayapSpinnerKategori;
    Spinner aramayapSpinnerSiralama;
    Button aramayapButon;

    Bundle gelen_veri;
    String Isbn;
    String Kelime;
    int spinner_secim_il_pos;
    int spinner_secim_ilce_pos;
    int spinner_secim_kategori_pos;
    int spinner_secim_siralama_pos;

    JSONObject il_ilçe_jsonobject;

    ArrayList<String> Iller = new ArrayList<>();
    ArrayAdapter<String> dataAdapterIller;

    ArrayList<String> Ilceler = new ArrayList<>();
    ArrayAdapter<String> dataAdapterIlceler;

    String[] Kategori={
            "Tüm Kategoriler","Bilim & Teknik","Çizgi Roman","Çocuk Kitapları",
            "Din","Edebiyat","Ekonomi & İş Dünyası","Felsefe & Düşünce",
            "Hukuk","Osmanlıca","Referans & Başvuru","Sağlık",
            "Sanat","Sınav ve Ders Kitapları","Spor","Tarih",
            "Toplum & Siyaset","Diğer & Çeşitli"};
    ArrayAdapter<String> dataAdapterKategori;

    String[] FiyataGore={"Sıralama Yok","Fiyata Göre Azalan","Fiyata Göre Artan"};
    ArrayAdapter<String> dataAdapterFiyataGore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arama__yap);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        aramayapIsbn = (EditText) findViewById(R.id.aramayapIsbn);
        aramayapBarcodeOku = (ImageButton) findViewById(R.id.aramayapBarcodeOku);
        aramayapKelime = (EditText) findViewById(R.id.aramayapKelime);
        aramayapSpinnerIl = (Spinner) findViewById(R.id.aramayapSpinnerIl);
        aramayapSpinnerIlce = (Spinner) findViewById(R.id.aramayapSpinnerIlce);
        aramayapSpinnerKategori = (Spinner) findViewById(R.id.aramayapSpinnerKategori);
        aramayapSpinnerSiralama = (Spinner) findViewById(R.id.aramayapSpinnerSiralama);
        aramayapButon = (Button) findViewById(R.id.aramayapButon);

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

        dataAdapterIller = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Iller);
        dataAdapterIller.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aramayapSpinnerIl.setAdapter(dataAdapterIller);

        aramayapSpinnerIl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Ilceler.clear();
                try {
                    JSONArray ilceler_jsonobject = il_ilçe_jsonobject.getJSONObject("" + position).getJSONArray("ilceler");
                    Ilceler.add("ilceler");
                    for (int sayac2 = 0; sayac2 < ilceler_jsonobject.length(); sayac2++) {
                        Ilceler.add((String) ilceler_jsonobject.getJSONObject(sayac2).get("ilce"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dataAdapterIlceler = new ArrayAdapter<String>(Arama_Yap.this, android.R.layout.simple_spinner_item, Ilceler);
                dataAdapterIlceler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                aramayapSpinnerIlce.setAdapter(dataAdapterIlceler);
                aramayapSpinnerIlce.setSelection(spinner_secim_ilce_pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dataAdapterIlceler = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Ilceler);
        dataAdapterIlceler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aramayapSpinnerIlce.setAdapter(dataAdapterIlceler);

        dataAdapterKategori = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Kategori);
        dataAdapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aramayapSpinnerKategori.setAdapter(dataAdapterKategori);

        dataAdapterFiyataGore = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, FiyataGore);
        dataAdapterFiyataGore.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aramayapSpinnerSiralama.setAdapter(dataAdapterFiyataGore);

        try {
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
        Log.d("ARAMA YAP GELEN",
                "   Isbn: " + Isbn +
                        "   Kelime:" + Kelime +
                        "   spinner_secim_il_pos: " + spinner_secim_il_pos +
                        "   spinner_secim_ilce_pos:" + spinner_secim_ilce_pos +
                        "   spinner_secim_kategori_pos: " + spinner_secim_kategori_pos +
                        "   spinner_secim_siralama_pos:" + spinner_secim_siralama_pos);*/

        aramayapIsbn.setText(Isbn);
        aramayapKelime.setText(Kelime);
        aramayapSpinnerIl.setSelection(spinner_secim_il_pos);
        aramayapSpinnerIlce.setSelection(spinner_secim_ilce_pos);
        aramayapSpinnerKategori.setSelection(spinner_secim_kategori_pos);
        aramayapSpinnerSiralama.setSelection(spinner_secim_siralama_pos);

        aramayapBarcodeOku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_buton_durum = 0;
                IntentIntegrator integrator = new IntentIntegrator(Arama_Yap.this);
                integrator.initiateScan();
            }
        });

        aramayapButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle veri = new Bundle();
                veri.putString("Isbn", "");
                veri.putString("Kelime", aramayapKelime.getText().toString());
                veri.putInt("spinner_secim_il_pos", aramayapSpinnerIl.getSelectedItemPosition());
                veri.putInt("spinner_secim_ilce_pos", aramayapSpinnerIlce.getSelectedItemPosition());
                veri.putInt("spinner_secim_kategori_pos", aramayapSpinnerKategori.getSelectedItemPosition());
                veri.putInt("spinner_secim_siralama_pos", aramayapSpinnerSiralama.getSelectedItemPosition());

                /*
                Log.d("ARAMA YAP GÖNDERİLEN",
                        "   Isbn: " + Isbn +
                                "   Kelime:" + Kelime +
                                "   spinner_secim_il_pos: " + spinner_secim_il_pos +
                                "   spinner_secim_ilce_pos:" + spinner_secim_ilce_pos +
                                "   spinner_secim_kategori_pos: " + spinner_secim_kategori_pos +
                                "   spinner_secim_siralama_pos:" + spinner_secim_siralama_pos);*/
                startActivity(new Intent(getApplicationContext(), Kitap_Liste.class).putExtras(veri));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(Activity_buton_durum == 0) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                aramayapIsbn.setText(result.getContents());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_arama__yap, menu);
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
