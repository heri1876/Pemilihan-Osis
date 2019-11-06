package com.shadow.pemilihanosisonline;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    EditText NikSiswa, Password;
    Button Login;
    private static String url = "https://sqlitedatanase.000webhostapp.com/";
    private static String _NAMAFILE = "userlogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        NikSiswa = findViewById(R.id.NikSiswa);
        Password = findViewById(R.id.Password);
        Login = findViewById(R.id.Login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NikSiswa.getText().length() != 0) {
                    if ((Password.getText().length() != 0)) {
                        Toast.makeText(LoginActivity.this, "Sedang mengecek akun ke server", Toast.LENGTH_SHORT).show();
                        login();
                    } else {
                        Toast.makeText(LoginActivity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "NIK tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void login() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url1 = url + "login.php?nik=" + NikSiswa.getText().toString() + "&password=" + Password.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", "" + response);
                        try {
                            //Mengambil output JSON
                            JSONObject jsonObject = new JSONObject(response);

                            //Merubah JSON menjadi Array
                            JSONArray userArray = jsonObject.getJSONArray("result");

                            //Mengambil data dari Array ke variable
                            JSONObject userObject = userArray.getJSONObject(0);
                            String nama = userObject.getString("nama");
                            String nik = userObject.getString("nik");
                            String pilihan = userObject.getString("pilihan");

                            //Membuat variable untuk isi file
                            String isiFile = nama + "\n" + nik + "\n" + pilihan;

                            //Membuat File
                            File file = new File(getFilesDir(), _NAMAFILE);
                            file.createNewFile();
                            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                            fileOutputStream.write(isiFile.getBytes());

                            ceklogin(pilihan);


                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Pastikan ada koneksi internet", Toast.LENGTH_SHORT).show();
            }
        }) {
        };
        requestQueue.add(stringRequest);
    }

    void ceklogin(String pilih) {

        if (Integer.parseInt(pilih) == 0) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.moveleft, R.anim.movehelper);
            finish();
        } else {
            Intent intent = new Intent(LoginActivity.this, SelesaiActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.moveleft, R.anim.movehelper);
            finish();
        }
    }
}
