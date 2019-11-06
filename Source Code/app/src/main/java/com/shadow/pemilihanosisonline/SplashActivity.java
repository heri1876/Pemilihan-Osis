package com.shadow.pemilihanosisonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shadow.pemilihanosisonline.dbhelper.dbhelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

    RelativeLayout splash;
    Animation smalltobig, moveleft, movehelper;
    private String url = "https://sqlitedatanase.000webhostapp.com/";

    dbhelper SQLite = new dbhelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        moveleft = AnimationUtils.loadAnimation(this, R.anim.moveleft);
        movehelper = AnimationUtils.loadAnimation(this, R.anim.movehelper);

        splash = findViewById(R.id.splash);
        splash.startAnimation(smalltobig);


        SQLite = new dbhelper(getApplicationContext());

        Toast.makeText(SplashActivity.this, "Mengecek Koneksi, pastikan terhubung ke Internet", Toast.LENGTH_SHORT).show();

        ambildata();
    }

    private void ambildata() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url1 = url + "tampilcalon.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            SQLite.deleteTB();
                            JSONObject obj = new JSONObject(response);
                            JSONArray calonArray = obj.getJSONArray("result");

                            for (int i = 0; i < calonArray.length(); i++) {

                                JSONObject calonObject = calonArray.getJSONObject(i);

                                SQLite.input(calonObject.getString("nomer"), calonObject.getString("foto"),
                                        calonObject.getString("nama_ketua"), calonObject.getString("nama_wakil"),
                                        calonObject.getString("visi"), calonObject.getString("misi"));
                            }

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.moveleft, R.anim.movehelper);
                                    finish();
                                }
                            }, 100);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SplashActivity.this, "Tidak ada koneksi Internet, buka aplikasi kembali " +
                        "saat sudah terhubung dengan Internet", Toast.LENGTH_SHORT).show();
                finish();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.moveleft, R.anim.movehelper);
                        finish();
                    }
                }, 2000);
            }
        }) {
        };
        requestQueue.add(stringRequest);
    }
}
