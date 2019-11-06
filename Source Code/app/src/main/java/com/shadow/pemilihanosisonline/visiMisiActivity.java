package com.shadow.pemilihanosisonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class visiMisiActivity extends AppCompatActivity {

    public static final String VISI = "visi";
    public static final String MISI = "misi";

    ImageView back;
    TextView visi, misi;
    String isiVisi, isiMisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_misi);

        back = findViewById(R.id.back);
        visi = findViewById(R.id.visi);
        misi = findViewById(R.id.misi);

        isiVisi = getIntent().getStringExtra(VISI);
        isiMisi = getIntent().getStringExtra(MISI);

        visi.setText(isiVisi);
        misi.setText(isiMisi);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(visiMisiActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.moveright, R.anim.movehelper);
                finish();
            }
        });

    }
}
