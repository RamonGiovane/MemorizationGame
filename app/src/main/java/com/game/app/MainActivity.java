package com.game.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.game.database.Database;
import com.game.model.UserScore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Integer> sequence;

    private ProgressBar progressBar;

    private int correctNumbers;

    private int errorCount;

    private TextView scoreText;

    private ScoreTimer scoreTimer;

    private EditText nameTextField;

    private Database database;
    private Intent rankingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartDatabase();

        correctNumbers = 0;
        GenerateNumbers();

        int id = getResources().getIdentifier("progressBar", "id", getPackageName());
        progressBar = (ProgressBar) findViewById(id);


        id = getResources().getIdentifier("score", "id", getPackageName());
        scoreText = (TextView) findViewById(id);

        id = getResources().getIdentifier("text_field", "id", getPackageName());
        nameTextField = (EditText) findViewById(id);
        nameTextField.setOnFocusChangeListener(new KeyboardFocusListener(this, id));

        scoreTimer = new ScoreTimer(scoreText);
        scoreTimer.start();

        rankingActivity = new Intent(this, RankingActivity.class);
    }

    private void StartDatabase() {
        database = new Database(this);

        database.createOrOpen();

        for(UserScore u : database.getAll())
            Log.d("SCORES", u.getName() + " " + u.getErrors() + " " + u.getScore());
    }


    private void SetWin(boolean enable){

       if(enable) scoreTimer.cancel();

        int id = getResources().getIdentifier("win_title", "id", getPackageName());
        findViewById(id).setVisibility(enable ? View.VISIBLE : View.GONE);

        id = getResources().getIdentifier("win_text", "id", getPackageName());
        findViewById(id).setVisibility(enable ? View.VISIBLE : View.GONE);


        id = getResources().getIdentifier("progressBar", "id", getPackageName());
        findViewById(id).setVisibility(enable ? View.GONE : View.VISIBLE);


        id = getResources().getIdentifier("progress_text", "id", getPackageName());
        findViewById(id).setVisibility(enable ? View.GONE : View.VISIBLE);

        id = getResources().getIdentifier("mistakes_text", "id", getPackageName());
        findViewById(id).setVisibility(enable ? View.VISIBLE : View.GONE);

        id = getResources().getIdentifier("mistakes", "id", getPackageName());
        TextView text = (TextView) findViewById(id);
        text.setVisibility(enable ? View.VISIBLE : View.GONE);
        text.setText(String.valueOf(errorCount));

        for (int i = 1; i <= 6; i++) {
            id = getResources().getIdentifier("button" + i, "id", getPackageName());
            Button button = (Button) findViewById(id);
            button.setVisibility(enable ? View.GONE : View.VISIBLE);
        }


        nameTextField.setVisibility(enable ? View.VISIBLE : View.GONE);


        id = getResources().getIdentifier("ioo", "id", getPackageName());
        findViewById(id).setVisibility(enable ? View.VISIBLE : View.GONE);


        id = getResources().getIdentifier("save_button", "id", getPackageName());
        findViewById(id).setVisibility(enable ? View.VISIBLE : View.GONE);

        id = getResources().getIdentifier("buttonrestart", "id", getPackageName());
        findViewById(id).setVisibility(enable ? View.INVISIBLE : View.VISIBLE);

    }

    public void ResetScreen(View view){

        correctNumbers = 0;

        SetWin(false);

        ChangeBackgroundColor(view,  getResources().getString(R.string.color_neutral));

        ChangeProgressBar();

    }
    public void Restart(View view){

        GenerateNumbers();

        SetWin(false);

        ResetScreen(view);

        scoreTimer.start();
    }

    private void ChangeBackgroundColor(final View someView, String color) {
        // Find the root view
        View root = someView.getRootView();

        Log.d("color", color);
        // Set the color
        root.setBackgroundColor(Color.parseColor(color));
    }

    public void CheckNumber(View view){

        Button button = (Button) view;

        int number = Integer.parseInt(button.getText().toString());



        if(number == sequence.get(correctNumbers)){

            ChangeBackgroundColor(view, getResources().getString(
                    getResources().getIdentifier("color" + number, "string", getPackageName())));

            view.setVisibility(View.INVISIBLE);

            correctNumbers++;

            ChangeProgressBar();
        }

        else {
            errorCount++;
            ResetScreen(view);
        }


        if(correctNumbers == 6)
            SetWin(true);
    }

    private void ChangeProgressBar(){

        progressBar.setProgress(0);
        progressBar.setMax(6);
        progressBar.setProgress(correctNumbers);

    }

    private void GenerateNumbers(){

        errorCount = 0;

        Random r = new java.util.Random();

        sequence = new ArrayList<>();

        while(sequence.size()  < 6){
            int value  = r.nextInt(6) + 1;

            boolean repeated = false;

            for(int v : sequence){
                if(value == v) {
                    repeated = true;
                    break;
                }
            }

            if(!repeated) sequence.add(value);
        }
    }

    public void SaveAndRestart(View view){

        String name = nameTextField.getText().toString();
        int score = Integer.parseInt(scoreText.getText().toString());
        int errors = errorCount;

        if(nameTextField.getText().toString().isEmpty()) {
            Toast.makeText(this, "Digite seu nome", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        database.insertRecord(new UserScore(score, errors, name, false));

        Restart(view);
    }

    public void OpenRankings(View view){
        startActivity(rankingActivity);
    }

}
