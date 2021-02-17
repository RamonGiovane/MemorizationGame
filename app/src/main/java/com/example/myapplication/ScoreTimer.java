package com.example.myapplication;

import android.os.CountDownTimer;
import android.widget.TextView;

public class ScoreTimer extends CountDownTimer {

    private final TextView scoreText;

    public ScoreTimer(TextView scoreText) {
        super(301000, 50);

        this.scoreText = scoreText;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        scoreText.setText(String.valueOf(millisUntilFinished));
    }

    @Override
    public void onFinish() {
        scoreText.setText("1");
    }
}
