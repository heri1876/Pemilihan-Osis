package com.shadow.pemilihanosisonline.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.shadow.pemilihanosisonline.adapter.data;;

import java.util.ArrayList;
import java.util.HashMap;

public class dbhelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    data data = new data();
    ArrayList<data> dataArray = new ArrayList<>();

    static final String DATABASE_NAME = "isi.db";

    public static final String TABLE_SQLite = "calon";

    public static final String COLUMN_NOMER = "nomer";
    public static final String COLUMN_FOTO = "foto";
    public static final String COLUMN_NAMA_KETUA = "nama_ketua";
    public static final String COLUMN_NAMA_WAKIL = "nama_wakil";
    public static final String COLUMN_VISI = "visi";
    public static final String COLUMN_MISI = "misi";

    public dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_SQLite + "(" + COLUMN_NOMER + " TEXT, "
                + COLUMN_FOTO + " TEXT NOT NULL, " + COLUMN_NAMA_KETUA + " TEXT NOT NULL, " + COLUMN_NAMA_WAKIL + " TEXT NOT NULL, "
                + COLUMN_VISI + " TEXT NOT NULL, " + COLUMN_MISI + " TEXT NOT NULL )";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SQLite);
        onCreate(db);
    }

    public void input(String nomer, String foto, String nama_ketua, String nama_wakil, String visi, String misi) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryValues = "INSERT INTO " + TABLE_SQLite + " (nomer, foto, nama_ketua, nama_wakil, visi, misi) " +
                "VALUES('" + nomer + "', '" + foto + "', '" + nama_ketua + "', '" + nama_wakil + "', '" + visi + "', '"
                + misi + "')";
        Log.e("insert sqlite ", "" + queryValues);
        database.execSQL(queryValues);
        database.close();
    }

    public HashMap<String, String> getData(int nomer) {
        HashMap<String, String> calon = new HashMap<String, String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from calon where nomer=?";
        Cursor cursor = db.rawQuery(query, new String[]{"" + nomer});
        if (cursor.moveToFirst()) {
            calon.put("nomer", cursor.getString(0));
            calon.put("foto", cursor.getString(1));
            calon.put("namaKetua", cursor.getString(2));
            calon.put("namaWakil", cursor.getString(3));
            calon.put("visi", cursor.getString(4));
            calon.put("misi", cursor.getString(5));
        }
        return calon;
    }

    public int jumlah() {
        String countQuery = "SELECT  count(*) FROM " + TABLE_SQLite;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();

        return cursor.getInt(0);
    }

    public void deleteTB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from calon");
    }

}
