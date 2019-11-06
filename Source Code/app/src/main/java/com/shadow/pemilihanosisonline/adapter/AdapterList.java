package com.shadow.pemilihanosisonline.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.shadow.pemilihanosisonline.R;
import com.shadow.pemilihanosisonline.dbhelper.dbhelper;
import com.shadow.pemilihanosisonline.SelesaiActivity;
import com.shadow.pemilihanosisonline.visiMisiActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AdapterList extends ArrayAdapter<data> {

    Context context;
    int resource;
    String nik;
    dbhelper SQLite = new dbhelper(getContext());
    private String url = "http://sqlitedatanase.000webhostapp.com/";

    public AdapterList(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View listViewItem = convertView;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(context).inflate(resource, null);
        }

        TextView nomer = listViewItem.findViewById(R.id.nomer);
        TextView namaKetua = listViewItem.findViewById(R.id.namaKetua);
        TextView namaWakil = listViewItem.findViewById(R.id.namaWakil);
        ImageView foto = listViewItem.findViewById(R.id.gambarCalon);
        Button VisiMisi = listViewItem.findViewById(R.id.VisiMisi);
        Button pilih = listViewItem.findViewById(R.id.pilih);

        nomer.setText("Calon Osis " + getItem(position).getNomer());
        namaKetua.setText("Nama Ketua : \n" + getItem(position).getNama_ketua());
        namaWakil.setText("Nama Wakil : \n" + getItem(position).getNama_wakil());
        Glide.with(getContext()).load(getItem(position).getFoto()).into(foto);

        VisiMisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), visiMisiActivity.class);
                intent.putExtra(visiMisiActivity.MISI, getItem(position).getMisi());
                intent.putExtra(visiMisiActivity.VISI, getItem(position).getVisi());
                getContext().startActivity(intent);

            }
        });

        pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);
            }
        });

        return listViewItem;
    }

    void ambilNik(Context c) {
        String[] isi = new String[3];
        File file = new File(c.getFilesDir(), "userlogin");
        try {
            Scanner scan = new Scanner(file);
            for (int i = 0; i < isi.length; i++) {
                isi[i] = scan.nextLine();
            }
            nik = isi[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void setPilihan(String pilihan) {
        ambilNik(getContext());

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url1 = url + "setpilihan.php?nik=" + nik + "&pilihan=" + pilihan;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getContext(), SelesaiActivity.class);
                        getContext().startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
        };
        requestQueue.add(stringRequest);
    }

    void showDialog(final int position) {
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(getContext());

        alertdialogbuilder.setTitle("Yakin pilih calon osis ?");

        alertdialogbuilder
                .setMessage("Tap 'Ya' untuk memilih")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPilihan(getItem(position).getNomer());
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertdialogbuilder.create();

        alertDialog.show();
    }

}