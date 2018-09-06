package com.example.kitapuygulama;

/**
 * Created by Feyyaz on 25.03.2015.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment implements VivzAdapter.ClickListener {

    public static final String PREF_FILE_NAME="testpref";
    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";

    AlertDialog alertDialog;

    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;
    RecyclerView recyclerView;
    VivzAdapter adapter;
    boolean mUserLeanedDrawer;
    boolean mFromSavedInstanceState;

    View containerView;

    Button navigationButonGirisYap;
    Button navigationButonCikisYap;
    Button navigationButonUyeOl;
    Button navigationButonKitapEkle;
    Button navigationButonKitapSil;
    Button navigationHakkinda;
    Button navigationUygulamayiKapat;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLeanedDrawer=Boolean.valueOf(readToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState != null){
            mFromSavedInstanceState=true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        /*
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new VivzAdapter(getActivity(), getData());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
*/


        navigationButonGirisYap = (Button) layout.findViewById(R.id.navigationButonGirisYap);
        navigationButonCikisYap = (Button) layout.findViewById(R.id.navigationButonCikisYap);
        navigationButonUyeOl = (Button) layout.findViewById(R.id.navigationButonUyeOl);
        navigationButonKitapEkle = (Button) layout.findViewById(R.id.navigationButonKitapEkle);
        navigationButonKitapSil = (Button) layout.findViewById(R.id.navigationButonKitapSil);
        navigationHakkinda = (Button) layout.findViewById(R.id.navigationHakkinda);
        navigationUygulamayiKapat = (Button) layout.findViewById(R.id.navigationUygulamayiKapat);

        navigationButonGirisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Uye_Giris.UYEID == 0) {
                    startActivity(new Intent(getActivity(), Uye_Giris.class));
                } else {
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("Giriş Yapılmış");
                    alertDialog.setButton("tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

        navigationButonCikisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Uye_Giris.UYEID != 0) {
                    Uye_Giris.UYEID = 0;
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("Çıkış Yapıldı");
                    alertDialog.setButton("tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                        }
                    });
                    alertDialog.show();
                } else {
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("Zaten Giriş Yapılmamış!");
                    alertDialog.setButton("tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

        navigationButonUyeOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Uye_Ol.class));
            }
        });
        navigationButonKitapEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Uye_Giris.UYEID != 0) {
                    startActivity(new Intent(getActivity(), Kitap_Ekle.class));
                } else {
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("Lütfen Giriş Yapınız!");
                    alertDialog.setButton("tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            }
        });
        navigationButonKitapSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Uye_Giris.UYEID != 0) {
                    startActivity(new Intent(getActivity(), Kitap_Sil.class));
                } else {
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("Lütfen Giriş Yapınız!");
                    alertDialog.setButton("tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            }
        });
        navigationHakkinda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setMessage("Her Türlü Bilgi İçin : feyyazfy@gmail.com");
                alertDialog.setButton("tamam", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
                //Toast.makeText(getActivity(),"Hakkında",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getActivity(), Kitap_Liste.class));
            }
        });
        navigationUygulamayiKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.exit(0);

                //Toast.makeText(getActivity(),"Kapat",Toast.LENGTH_SHORT).show();
            }
        });

        return layout;
    }

    public static List<Information> getData(){
        List<Information> data = new ArrayList<>();
        //int[] icons = {R.drawable.abc_ic_menu_cut_mtrl_alpha,R.drawable.abc_ic_menu_cut_mtrl_alpha,R.drawable.abc_ic_menu_cut_mtrl_alpha,R.drawable.abc_ic_menu_cut_mtrl_alpha};
        String[] titles={
                "Kategori Seçiniz...","Bilim & Teknik","Çizgi Roman","Çocuk Kitapları",
                "Din","Edebiyat","Ekonomi & İş Dünyası","Felsefe & Düşünce",
                "Hukuk","Osmanlıca","Referans & Başvuru","Sağlık",
                "Sanat","Sınav ve Ders Kitapları","Spor","Tarih",
                "Toplum & Siyaset","Diğer & Çeşitli"};
        for(int i=0; i<titles.length; i++){
            Information current = new Information();
            //current.iconId = icons[i];
            current.title = titles[i];
            data.add(current);
        }
        return data;
    }


    public void setUp(int fragmentId , DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLeanedDrawer){
                    mUserLeanedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLeanedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView,slideOffset);
                toolbar.setAlpha(1 - slideOffset/2);

                //Log.d("VIVZ", "offset" + slideOffset);
            }
        };

        if(!mUserLeanedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readToPreferences(Context context, String preferenceName, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName  ,defaultValue);
    }

    @Override
    public void itemClicked(View view, int position) {
    }
}
