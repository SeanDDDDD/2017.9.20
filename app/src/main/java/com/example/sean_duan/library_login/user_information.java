package com.example.sean_duan.library_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class user_information extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        intent = getIntent();
        String s = intent.getStringExtra("jieyuelishi");
        Log.e("jieyuelishi",s);
    }
}
