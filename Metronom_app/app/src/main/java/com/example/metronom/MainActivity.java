package com.example.metronom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.MediaPlayer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;


public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;
    String pauzaVynechaniPole, hratVynechaniPole;
    Long pauzaKlepani, hrat;
    Boolean hraje = false;
    ConstraintLayout swagLayout;



    private static SoundPool soundPool;
    private static HashMap<String, Integer> soundPoolMap;
    static int mod = 0;
    Spinner spinner;
    Spinner spinner2;
    ArrayAdapter adapter;
    ArrayAdapter adapter2;
    Thread t1;
    private int kolikata = 4;
    boolean stop = false;
    String zvuk;
    String zvyrazeni;
    String nic;



    private Handler mHandler = new Handler();
    private int kdyZvednout = 8000000;
    private int kolikratJsemKlepnul=0;
    EditText editText;
    TextView textView4;
    TextView textView5;



    Switch aSwitch2;
    EditText editTextNumber4, editTextNumber3, editTextNumber2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTextNumber);
        swagLayout = findViewById(R.id.swag_layout_id);

        mp = MediaPlayer.create(MainActivity.this, R.raw.zvuk1);

        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<String, Integer>();

        Integer i = 123;

        i.toString();


        Intent intent = getIntent();
        String str = intent.getStringExtra("message_key");

        editText.setText(str);






       List<String> states = Arrays.asList("zvuk1.wav","zvuk2.wav");
       spinner = findViewById(R.id.spinnerZvuky);
       adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, states);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinner.setAdapter(adapter);

        List<String> states2 = Arrays.asList("8000", "4","3");
        spinner2 = findViewById(R.id.spinnerZvyrazneni);
        adapter2 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, states2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        textView5 = findViewById(R.id.textView5);
        textView4 = findViewById(R.id.textView4);

        editTextNumber4=findViewById(R.id.editTextNumber2);
        editTextNumber3=findViewById(R.id.editTextNumber3);

        editTextNumber2=findViewById(R.id.editTextNumber2);







        aSwitch2 = findViewById(R.id.switch4);
        editTextNumber4.setVisibility(View.INVISIBLE);
        editTextNumber3.setVisibility(View.INVISIBLE);
        textView5.setVisibility(View.INVISIBLE);
        textView4.setVisibility(View.INVISIBLE);






        aSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(aSwitch2.isChecked())
                {
                    textView4.setText("Kolik zahrát:");
                    textView5.setText("Kolik vynechat:");
                    editTextNumber2.setVisibility(View.VISIBLE);
                    editTextNumber3.setVisibility(View.VISIBLE);
                    textView5.setVisibility(View.VISIBLE);
                    textView4.setVisibility(View.VISIBLE);

                    Intent intent = getIntent();
                    String str2 = intent.getStringExtra("message_key2");
                    String str3 = intent.getStringExtra("message_key3");
                    editTextNumber3.setText(str2);
                    editTextNumber2.setText(str3);
                    swagLayout.setVisibility(View.VISIBLE);



                }
                else {
                    textView4.setText("");
                    textView5.setText("");
                    editTextNumber2.setVisibility(View.INVISIBLE);
                    editTextNumber3.setVisibility(View.INVISIBLE);
                    swagLayout.setVisibility(View.GONE);
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
                NastaveniDatabaze myDB = new NastaveniDatabaze(MainActivity.this);
                myDB.pridaniNastaveni(editText.getText().toString().trim(),
                        editTextNumber3.getText().toString().trim(),
                        Integer.valueOf(editTextNumber4.getText().toString().trim()));
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






    private class RunnableImpl implements Runnable {

        public void run()
        {
            try
            {
                stop = false;
                nastaveniRychlosti(Integer.parseInt(editText.getText().toString()));

                while(!stop)
                {
                    if (metronomKdyzHraje())
                    {
                        prehravaniZvuku(zvuk);
                    }
                }
            }
            catch (Exception ex)
            {

            }
        }
    }



    public void startOpakovat(View v)
    {
        pauzaVynechaniPole = editTextNumber3.getText().toString();
        hratVynechaniPole = editTextNumber2.getText().toString();


        if (swagLayout.getVisibility()==View.VISIBLE) {

            if (!pauzaVynechaniPole.isEmpty() && !hratVynechaniPole.isEmpty()){
                pauzaKlepani = Long.parseLong(pauzaVynechaniPole +"000");
                hrat = Long.parseLong(hratVynechaniPole +"000");


                if (!hraje){
                    hraje = true;
                    mp = MediaPlayer.create(MainActivity.this, R.raw.zvuk1);

                    vynechaniDob();
                }
            }
        }else {
            t1 = new Thread(new RunnableImpl());
            t1.start();
            zvuk = spinner.getSelectedItem().toString();
            zvyrazeni = spinner2.getSelectedItem().toString();
        }
    }
    public void stopOpakovat(View v) {

        stop=true;
        if (hraje) {
            hraje = false;
            mp.setLooping(false);
            mp.stop();
        }

    }


    public void nastaveniRychlosti(int bpm)
    {
        if (bpm == 0)
        {
            mod = 1000;
        }
        else
        {
            mod = 60000 / bpm;
        }
    }

    public boolean metronomKdyzHraje()
    {
        if ((System.currentTimeMillis() % mod) == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void prehravaniZvuku(final String filePath)
    {
        try
        {
            if (!soundPoolMap.containsKey(filePath))
            {
                AssetFileDescriptor afd = getAssets().openFd(filePath);
                if (afd == null)
                {
                    System.out.println("Nelze najít:" + filePath);
                    return;
                }

                int id = soundPool.load(afd, 1);
                soundPoolMap.put(filePath, id);
            }
            int id = soundPoolMap.get(filePath);
            int number = Integer.parseInt(zvyrazeni);
            if(kdyZvednout!=-1) {
                if (((kolikratJsemKlepnul) % number) == 0) {
                    soundPool.play(id, 1.0f, 1.0f, 1, 0, 2.0f);
                } else {
                    soundPool.play(id, 1.0f, 1.0f, 1, 0, 1.0f);

                }
            }else    soundPool.play(id, 1.0f, 1.0f, 1, 0, 1.0f);
            kolikratJsemKlepnul++;
            System.out.println("Zvuk " + id);

        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + ex.toString());
        }
    }

    public void vynechaniDob()
    {
        if (hraje)
        {

            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    if (hraje)
                    {
                        mp.setLooping(true);
                        mp.start();


                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {

                                mp.setLooping(false);
                                mp.stop();

                                vynechaniDob();


                            }
                        }, hrat);
                    }


                }
            }, pauzaKlepani);
        }



    }





}