package com.example.metronom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.uloz_menu:
                Intent ht1 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(ht1);
                return true;


            case R.id.nacti_menu:
                Intent ht2 = new Intent(MainActivity.this, Nacti.class);
                startActivity(ht2);
                return true;

            case R.id.napoveda_menu:
                setContentView(R.layout.napoveda);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void Zvuk(View view)
    {
        Intent ht3= new Intent(MainActivity.this, Zvuk.class);
        startActivity(ht3);



    }



}
