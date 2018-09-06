package com.example.kitapuygulama;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;

/**
 * Created by Feyyaz on 27.03.2015.
 */

public class FeedItem {
    private int kitap_id;
    private int kitap_uyeid;
    private String kitap_adi;
    private String kitap_adres;
    private String kitap_fiyat;
    private Bitmap kitap_resim;
    private String kitap_resim_link;

    public int getKitap_id() {
        return kitap_id;
    }

    public void setKitap_id(int kitap_id) {
        this.kitap_id = kitap_id;
    }

    public int getKitap_uyeid() {
        return kitap_uyeid;
    }

    public void setKitap_uyeid(int kitap_uyeid) {
        this.kitap_uyeid = kitap_uyeid;
    }

    public String getKitap_adi() {
        return kitap_adi;
    }

    public void setKitap_adi(String kitap_adi) {
        this.kitap_adi = kitap_adi;
    }

    public String getKitap_adres() {
        return kitap_adres;
    }

    public void setKitap_adres(String kitap_adres) {
        this.kitap_adres = kitap_adres;
    }

    public String getKitap_fiyat() {
        return kitap_fiyat;
    }

    public void setKitap_fiyat(String kitap_fiyat) {
        this.kitap_fiyat = kitap_fiyat;
    }

    public Bitmap getKitap_resim() {
        return kitap_resim;
    }

    public void setKitap_resim(Bitmap kitap_resim) {
        this.kitap_resim = kitap_resim;
    }

    public String getKitap_resim_link() {
        return kitap_resim_link;
    }

    public void setKitap_resim_link(String kitap_resim_link) {
        this.kitap_resim_link = kitap_resim_link;
    }
}