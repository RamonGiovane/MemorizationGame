package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardFocusListener implements View.OnFocusChangeListener {

    private Activity activity;
    private  int textFieldId;

    public KeyboardFocusListener(Activity activity, int textFieldId) {
        this.activity = activity;
        this.textFieldId = textFieldId;
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {

            if(v.getId() == textFieldId && !hasFocus) {
                InputMethodManager imm =  (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }
}
