package com.example.instaserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//hide the action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//hide the status
        setContentView(R.layout.activity_feedback);
        final EditText feedback = findViewById(R.id.editTextTextPersonName2);
        final EditText user = findViewById(R.id.editTextTextPersonName1);
        Button b=findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(feedback.getText()))
                    feedback.setError("Empty field");
                if(TextUtils.isEmpty(user.getText()))
                    user.setError("Empty field");
                insert(feedback.getText().toString(),user.getText().toString());
            }
        });
    }
    void insert(String f,String user){
        DatabaseReference dataref= FirebaseDatabase.getInstance().getReference().child("Feedback");
        HashMap<String,String> hp= new HashMap<>();
        hp.put("username",user.replaceAll("\\s", "."));
        hp.put("feedback",f);
        dataref.child(user).setValue(hp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Feedback recorded",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),"Feedback Cann't recorded! "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       startActivity(new Intent(getApplicationContext(),HomeActivity.class));
    }
}