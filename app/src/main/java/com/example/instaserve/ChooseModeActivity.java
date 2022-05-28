package com.example.instaserve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class ChooseModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//hide the action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//hide the status
        setContentView(R.layout.activity_choose_mode);
        Button user=findViewById(R.id.user);
        Button servious = findViewById(R.id.servious_provider);
       final SharedPreferences sharedPreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferences.contains("username"))
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                else
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));

            }
        });
        servious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferences.contains("username"))
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                else
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}