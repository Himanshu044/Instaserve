package com.example.instaserve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.View;

public class SnapActivity2 extends AppCompatActivity {

    public  void verified(View view)
    {
        Intent intent=new Intent(getApplicationContext(),SnapActivity3.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//hide the action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//hide the status
        setContentView(R.layout.activity_snap2);
    }
}
