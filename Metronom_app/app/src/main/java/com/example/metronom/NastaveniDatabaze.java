package com.example.metronom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

class NastaveniDatabaze extends SQLiteOpenHelper {

    private Context context;
    private static final String JMENO_DATABASE = "BankaMetronomu.db";
    private static final int VERSE_DATABASE = 1;

    private static final String TABLE_NAME = "moje_banka";
    private static final String SLOUPEC_ID = "_id";
    private static final String SLOUPEC_TEMPO = "tempo";
    private static final String SLOUPEC_VYNECHAT = "kolik_vynechat";
    private static final String SLOUPEC_ZAHRAT = "kolik_zahrat";

    NastaveniDatabaze(@Nullable Context context) {
        super(context, JMENO_DATABASE, null, VERSE_DATABASE);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + SLOUPEC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SLOUPEC_TEMPO + " TEXT, " +
                SLOUPEC_VYNECHAT + " TEXT, " +
                SLOUPEC_ZAHRAT + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    void pridaniNastaveni(String tempo, String vynechat, int zahrat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SLOUPEC_TEMPO, tempo);
        cv.put(SLOUPEC_VYNECHAT, vynechat);
        cv.put(SLOUPEC_ZAHRAT, zahrat);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Nastavení se neuložilo!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Nastavení uloženo!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor zobrazVsechnaData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void smazaniJednohoNastaveni(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Smazání selhalo!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Data jsou smazány!", Toast.LENGTH_SHORT).show();
        }
    }
}
