package com.example.myapplication;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Integer> sequence;

    private int correctNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correctNumbers = 0;
        GenerateNumbers();
    }

    public void CheckNumber(View view){

        Button button = (Button) view;

        int number = Integer.parseInt(button.getText().toString());

        //int colorNumber = ((ColorDrawable) button.getBackground()).getColor();


        if(number == sequence.get(correctNumbers)){
            GridLayout gl = (GridLayout) findViewById(R.id.gridLayout);
          //  gl.setBackgroundColor(colorNumber);

            view.setVisibility(View.INVISIBLE);

            correctNumbers++;
        }
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
