package com.example.metronom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UrciteNastaveni extends RecyclerView.Adapter<UrciteNastaveni.mojeZobrazeni> {
    private Context context;

    private ArrayList banka_id, tempo, kolik_vynechat, kolik_zahrat;

    UrciteNastaveni(Activity activity, Context context, ArrayList banka_id, ArrayList tempo, ArrayList kolik_vynechat,
                    ArrayList kolik_zahrat){

        this.context = context;
        this.banka_id = banka_id;
        this.tempo = tempo;
        this.kolik_vynechat = kolik_vynechat;
        this.kolik_zahrat = kolik_zahrat;
        System.out.println("5");
    }


    @NonNull
    @Override
    public mojeZobrazeni onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        System.out.println("6");
        return new mojeZobrazeni(view);

    }


    @Override
    public void onBindViewHolder(@NonNull final mojeZobrazeni holder, final int position) {

        holder.banka_id_text.setText(String.valueOf(banka_id.get(position)));
        holder.tempo_text.setText(String.valueOf(tempo.get(position)));
        holder.kolik_vynechat_text.setText(String.valueOf(kolik_vynechat.get(position)));
        holder.kolik_zahrat_text.setText(String.valueOf(kolik_zahrat.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("8");
                Intent intent = new Intent(context, NacitaniNastaveni.class);
                intent.putExtra("id", String.valueOf(banka_id.get(position)));
                intent.putExtra("tempo", String.valueOf(tempo.get(position)));
                intent.putExtra("kolik_vynechat", String.valueOf(kolik_vynechat.get(position)));
                intent.putExtra("kolik_zahrat", String.valueOf(kolik_zahrat.get(position)));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return banka_id.size();
    }

    public class mojeZobrazeni extends RecyclerView.ViewHolder {



        TextView banka_id_text, tempo_text, kolik_vynechat_text, kolik_zahrat_text;
        LinearLayout mainLayout;

        mojeZobrazeni(@NonNull View itemView) {
            super(itemView);
            banka_id_text = itemView.findViewById(R.id.banka_id_text);
            tempo_text = itemView.findViewById(R.id.tempo_text);
            kolik_vynechat_text = itemView.findViewById(R.id.kolik_vynechat_text);
            kolik_zahrat_text = itemView.findViewById(R.id.kolik_zahrat_text);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }

    }

}