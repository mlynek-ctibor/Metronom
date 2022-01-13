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
import android.database.sqlite.SQLiteOpenHelper;
import java.lang.NullPointerException;
import java.lang.RuntimeException;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Intent;
import android.util.Log;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    private double bpm;
    private int beat;
    private int noteValue;
    private int silence;

    private double beatSound;
    private double sound;
    private final int tick = 1000; // samples of tick

    private boolean play = true;

    private AudioGenerator audioGenerator = new AudioGenerator(8000);


    EditText editText;
    MediaPlayer player;
    TextView textView7;
    TextView textView8;
    TextView textView4;
    TextView textView5;

    Switch aSwitch;
    Switch aSwitch2;
    EditText editTextNumber4, editTextNumber3, editTextNumber6, editTextNumber2;
    Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        audioGenerator.createPlayer();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTextNumber);

        textView7 = findViewById(R.id.textView7);
       textView8 = findViewById(R.id.textView8);
        textView5 = findViewById(R.id.textView5);
       textView4 = findViewById(R.id.textView4);

        editTextNumber4=findViewById(R.id.editTextNumber2);
        editTextNumber3=findViewById(R.id.editTextNumber3);
        editTextNumber6=findViewById(R.id.editTextNumber6);
        editTextNumber2=findViewById(R.id.editTextNumber2);
        /*editTextTime=findViewById(R.id.editTextTime);*/



        add_button= findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.addBook(editText.getText().toString().trim(),
                        editTextNumber3.getText().toString().trim(),
                       Integer.valueOf(editTextNumber4.getText().toString().trim()));
            }
        });




        aSwitch = findViewById(R.id.switch2);
        aSwitch2 = findViewById(R.id.switch4);
        editTextNumber4.setVisibility(View.INVISIBLE);
        editTextNumber3.setVisibility(View.INVISIBLE);
        editTextNumber4.setVisibility(View.INVISIBLE);
        editTextNumber6.setVisibility(View.INVISIBLE);




        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(aSwitch.isChecked())
                {
                    textView7.setText("O kolik zrychlit:");
                    textView8.setText("Jak dlouho hrát:");
                    editTextNumber4.setVisibility(View.VISIBLE);
                    editTextNumber6.setVisibility(View.VISIBLE);
                }
                else {
                    textView7.setText("");
                    textView8.setText("");
                    editTextNumber4.setVisibility(View.INVISIBLE);
                    editTextNumber6.setVisibility(View.INVISIBLE);

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
                    editTextNumber2.setVisibility(View.VISIBLE);
                    editTextNumber3.setVisibility(View.VISIBLE);

                }
                else {
                    textView4.setText("");
                    textView5.setText("");
                    editTextNumber2.setVisibility(View.INVISIBLE);
                    editTextNumber3.setVisibility(View.INVISIBLE);
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

            case R.id.btnAdd:
                Intent ht1 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(ht1);
                return true;


            case R.id.btnView:
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


    public void calcSilence() {
        silence = (int) (((60/Integer.parseInt(editText.getText().toString()))*8000)-tick);
    }

    public void play(View v) {
        calcSilence();
        double[] tick =
                audioGenerator.getSineWave(this.tick, 8000, beatSound);
        double[] tock =
                audioGenerator.getSineWave(this.tick, 8000, sound);
        double silence = 0;
        double[] sound = new double[8000];
        int t = 0,s = 0,b = 0;
        do {
            for(int i=0;i<sound.length&&play;i++) {
                if(t<this.tick) {
                    if(b == 0)
                        sound[i] = tock[t];
                    else
                        sound[i] = tick[t];
                    t++;
                } else {
                    sound[i] = silence;
                    s++;
                    if(s >= this.silence) {
                        t = 0;
                        s = 0;
                        b++;
                        if(b > (this.beat-1))
                            b = 0;
                    }
                }
            }
            audioGenerator.writeSound(sound);
        } while(play);
    }

    public void stop(View v) {
        play = false;
        audioGenerator.destroyAudioTrack();
    }




















}