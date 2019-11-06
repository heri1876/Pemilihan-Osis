package com.shadow.pemilihanosisonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.shadow.pemilihanosisonline.adapter.AdapterList;
import com.shadow.pemilihanosisonline.adapter.data;
import com.shadow.pemilihanosisonline.dbhelper.dbhelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button keluar;
    TextView namaUser;

    ListView listView;
    AdapterList adapter;
    ArrayList<data> dataArray = new ArrayList<>();
    dbhelper sql = new dbhelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = findViewById(R.id.listCalon);
        adapter = new AdapterList(this, R.layout.calon_row);
        listView.setAdapter(adapter);
        tampilData();

        keluar = findViewById(R.id.Logout);
        namaUser = findViewById(R.id.namaUser);

        dynamicSize();
        bacafile();

        keluar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Logout:
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.moveright, R.anim.movehelper);
                finish();
                break;
        }
    }

    private void tampilData() {
        dataArray.clear();
        adapter.clear();

        int a = sql.jumlah();

        for (int i = 1; i <= a; i++) {
            HashMap<String, String> calon = sql.getData(i);
            data Data = new data();
            Data.setNomer(calon.get("nomer"));
            Data.setFoto(calon.get("foto"));
            Data.setNama_ketua(calon.get("namaKetua"));
            Data.setNama_wakil(calon.get("namaWakil"));
            Data.setVisi(calon.get("visi"));
            Data.setMisi(calon.get("misi"));
            dataArray.add(Data);
        }

        adapter.addAll(dataArray);
        adapter.notifyDataSetChanged();
    }

    void dynamicSize(){
        int totalHeight = 0;

        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    void bacafile(){
        File file = new File(getFilesDir(), "userlogin");
        try {
            Scanner scan = new Scanner(file);
            namaUser.setText("Selamat datang, "+scan.nextLine());
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

}
