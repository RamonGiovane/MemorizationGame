package com.game.app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.game.database.Database;
import com.game.model.UserScore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RankingActivity extends AppCompatActivity {

    private Database database;
    private boolean showingPoints;

    private List<UserScore> rankings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showingPoints = true;

        if(database == null) {
            database = new Database(this);
            database.createOrOpen();
        }

        rankings = database.getAll();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> ShowListView(!showingPoints));

        ShowListView(showingPoints);
    }

    private void ShowListView(boolean showingPoints){
        this.showingPoints = showingPoints;

        ListView listView = (ListView) findViewById(R.id.rankings_list);

        if(!showingPoints){
           rankings = database.getAllByPoints();
        }
        else
            rankings = database.getAllByErrors();

        String[] rankingsString = new String[rankings.size()];

        for(int i = 0; i<rankings.size(); i++){
            String value = i+1 + "º   ";
            value += String.format("%-20s", rankings.get(i).getName());

            if(showingPoints)
                value += String.format("%-15s",rankings.get(i).getScore() + " pontos");
            else
                value += String.format("%-10s", rankings.get(i).getErrors() + " erros");

            rankingsString[i] = value;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
               android.R.layout.simple_list_item_1, rankingsString);

        listView.setAdapter(adapter);
    }

    public void ClearRankings(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Apagar as pontuações de jogadores?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        database.clear();
                        ShowListView(showingPoints);
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }
}