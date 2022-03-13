package com.example.metronom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;

import android.widget.Toast;

import java.util.ArrayList;

public class Nacti extends AppCompatActivity {
   RecyclerView recyclerView;
    NastaveniDatabaze myDB;
    ArrayList<String> banka_id, tempo, kolik_vynechat, kolik_zahrat;
    UrciteNastaveni urciteNastaveni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nacti);
        recyclerView = findViewById(R.id.recyclerView);

        myDB = new NastaveniDatabaze(Nacti.this);
        banka_id = new ArrayList<>();
        tempo = new ArrayList<>();
        kolik_vynechat = new ArrayList<>();
        kolik_zahrat = new ArrayList<>();

        storeDataInArrays();

        urciteNastaveni = new UrciteNastaveni(Nacti.this,this, banka_id, tempo, kolik_vynechat,
                kolik_zahrat);
        recyclerView.setAdapter(urciteNastaveni);
        recyclerView.setLayoutManager(new LinearLayoutManager(Nacti.this));
    }


    void storeDataInArrays(){
        Cursor cursor = myDB.zobrazVsechnaData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Žádné nastavení", Toast.LENGTH_SHORT). show() ;
        }else{
            while (cursor.moveToNext()){
                banka_id.add(cursor.getString(0));
                tempo.add(cursor.getString(1));
                kolik_vynechat.add(cursor.getString(2));
                kolik_zahrat.add(cursor.getString(3));
            }

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {




            case R.id.napoveda_menu:
                setContentView(R.layout.napoveda);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
