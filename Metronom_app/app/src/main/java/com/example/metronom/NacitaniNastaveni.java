package com.example.metronom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NacitaniNastaveni extends AppCompatActivity {
    private Context context;
    EditText editText;
    TextView tempo, kolik_vynechat2, kolik_zahrat2 ;
    Button nacist_btn, smazat_btn;

    String id, tempo_txt, kolik_vynechat, kolik_zahrat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        editText = findViewById(R.id.editTextNumber);
        tempo = findViewById(R.id.tempo);
        kolik_vynechat2 = findViewById(R.id.kolik_vynechat2);
        kolik_zahrat2 = findViewById(R.id.kolik_zahrat2);
        nacist_btn = findViewById(R.id.nacist_btn);
        smazat_btn = findViewById(R.id.smazat_btn);



        nacist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = tempo.getText().toString();
                String str2 = kolik_vynechat2.getText().toString();
                String str3 = kolik_zahrat2.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("message_key", str);
                intent.putExtra("message_key2", str2);
                intent.putExtra("message_key3", str3);
                startActivity(intent);




            }
        });

        smazat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                potvrzovaciDialog();
            }
        });

        nacteniAZobrazeniDat();


    }




    void nacteniAZobrazeniDat(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("tempo") &&
                getIntent().hasExtra("kolik_vynechat") && getIntent().hasExtra("kolik_zahrat")){

            id = getIntent().getStringExtra("id");
            tempo_txt = getIntent().getStringExtra("tempo");
            kolik_zahrat = getIntent().getStringExtra("kolik_vynechat");
            kolik_vynechat = getIntent().getStringExtra("kolik_zahrat");


            tempo.setText(tempo_txt);
            kolik_vynechat2.setText(kolik_zahrat);
            kolik_zahrat2.setText(kolik_vynechat);

        }else{
            Toast.makeText(this, "Žádné data.", Toast.LENGTH_SHORT).show();
        }
    }

    void potvrzovaciDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Smazat nastavení " + id + " ?");
        builder.setMessage("Jste si jisti, že chcete smazat nastavení " + id + " ?");
        builder.setPositiveButton("Ano", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NastaveniDatabaze myDB = new NastaveniDatabaze(NacitaniNastaveni.this);
                myDB.smazaniJednohoNastaveni(id);
                finish();
            }
        });
        builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
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