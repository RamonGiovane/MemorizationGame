package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Integer> sequence;

    private ProgressBar progressBar;

    private int correctNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correctNumbers = 0;
        GenerateNumbers();

        int id = getResources().getIdentifier("progressBar", "id", getPackageName());
        progressBar = (ProgressBar) findViewById(id);

    }

    private void SetWin(boolean enable){

        int id = getResources().getIdentifier("win_title", "id", getPackageName());
        findViewById(id).setVisibility(enable ? View.VISIBLE : View.GONE);

        id = getResources().getIdentifier("win_text", "id", getPackageName());
        findViewById(id).setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    public void ResetScreen(View view){

        correctNumbers = 0;

        for (int i = 1; i <= 6; i++) {
            int id = getResources().getIdentifier("button"+i, "id", getPackageName());
            Button button = (Button) findViewById(id);
            button.setVisibility(View.VISIBLE);
        }

        ChangeBackgroundColor(view,  getResources().getString(R.string.color_neutral));

        ChangeProgressBar();

    }
    public void Restart(View view){

        GenerateNumbers();

        SetWin(false);

        ResetScreen(view);
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

        else
            ResetScreen(view);



        if(correctNumbers == 6)
            SetWin(true);
    }

    private void ChangeProgressBar(){

        progressBar.setProgress(0);
        progressBar.setMax(6);
        progressBar.setProgress(correctNumbers);

    }

    private void GenerateNumbers(){
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
}
