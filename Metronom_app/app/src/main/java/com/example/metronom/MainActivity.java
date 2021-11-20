package com.example.metronom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
    int pockat;
    EditText rychlost;
    MediaPlayer player;
    TextView textView7;
    TextView textView8;
    TextView textView4;
    TextView textView5;
    int i=0;
    Switch aSwitch;
    Switch aSwitch2;
    EditText editTextName, editTextNumber4, editTextNumber3, editTextTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rychlost = findViewById(R.id.editTextNumber);

        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        textView5 = findViewById(R.id.textView5);
        textView4 = findViewById(R.id.textView4);
        editTextName=findViewById(R.id.editTextNumber4);
        editTextNumber4=findViewById(R.id.editTextNumber2);
        editTextNumber3=findViewById(R.id.editTextNumber3);
        editTextTime=findViewById(R.id.editTextTime);

        aSwitch = findViewById(R.id.switch2);
        aSwitch2 = findViewById(R.id.switch4);
        editTextName.setVisibility(View.INVISIBLE);
        editTextNumber3.setVisibility(View.INVISIBLE);
        editTextNumber4.setVisibility(View.INVISIBLE);
        editTextTime.setVisibility(View.INVISIBLE);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(aSwitch.isChecked())
                {
                    textView7.setText("O kolik zrychlit:");
                    textView8.setText("Jak dlouho hrát:");
                    editTextName.setVisibility(View.VISIBLE);
                    editTextTime.setVisibility(View.VISIBLE);
                }
                else {
                    textView7.setText("");
                    textView8.setText("");
                    editTextName.setVisibility(View.INVISIBLE);
                    editTextTime.setVisibility(View.INVISIBLE);

                }
            }
        });


        aSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(aSwitch2.isChecked())
                {
                    textView4.setText("Kolik zahrát:");
                    textView5.setText("Kolik vynechat:");
                    editTextNumber3.setVisibility(View.VISIBLE);
                    editTextNumber4.setVisibility(View.VISIBLE);

                }
                else {
                    textView4.setText("");
                    textView5.setText("");
                    editTextNumber3.setVisibility(View.INVISIBLE);
                    editTextNumber4.setVisibility(View.INVISIBLE);
                }
            }
        });




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

    public void Zvuk(View view) {
        Intent ht3 = new Intent(MainActivity.this, Zvuk.class);
        startActivity(ht3);


    }




    public void zacniOpakovat(View v) {
        player = MediaPlayer.create(MainActivity.this, R.raw.metronom);
        float inter = (float) (60.0/Integer.parseInt(rychlost.getText().toString()));
        pockat = Math.round(inter*1000);
        System.out.println("Cekam "+pockat);
        i = 1;

        Opakovani.run();
    }

    public void stopOpakovat(View v) {
        mHandler.removeCallbacks(Opakovani);
    }

    private Runnable Opakovani = new Runnable() {
        @Override
        public void run() {

            player.start();

            System.out.println("TIKTAK: "+i);
            i++;
            mHandler.postDelayed(this, pockat);

        }

    };






}