package com.example.instaserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;
    Editable username ;
    TextInputLayout textInputLayout1;
    Editable email;
    TextInputLayout textInputLayout2;
    Editable phoneNumber;
    TextInputLayout textInputLayout3 ;
    Editable password;
    Button submit;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    HashMap<String, Object> user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//hide the action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//hide the status
        setContentView(R.layout.activity_register);
        textInputLayout = findViewById(R.id.username);
         username = textInputLayout.getEditText().getText();
         textInputLayout1 = findViewById(R.id.email);
      email = textInputLayout1.getEditText().getText();
        progressDialog = new ProgressDialog(RegisterActivity.this);
         textInputLayout2 = findViewById(R.id.phoneNumber);
         phoneNumber = textInputLayout2.getEditText().getText();
       textInputLayout3 = findViewById(R.id.password);
        password = textInputLayout3.getEditText().getText();
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(username.toString()))
                    textInputLayout.setError("Empty field");
                if(TextUtils.isEmpty(email.toString()))
                   textInputLayout1.setError("Empty field");
                if(TextUtils.isEmpty(phoneNumber.toString()) || phoneNumber.toString().length()!=10)
                    textInputLayout2.setError("Invalid Number");
                if(TextUtils.isEmpty(password.toString()) && password.toString().length()<5)
                    textInputLayout3.setError("Invalid Password");
                insertData();
            }
        });
    }

    void insertData(){
        progressDialog.setTitle("Registering User");
        progressDialog.setMessage("Please wait, While we are checking the credentails.");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        user = new HashMap<>();
        user.put("name",username.toString());
        user.put("contactno",phoneNumber.toString());
        user.put("email",email.toString());
        fAuth.createUserWithEmailAndPassword(email.toString(),password.toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"User Created",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            }else{
                                Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{

                    builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("Login Failed! ");
                    builder.setMessage("Invalid Username Password!");
                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create().show();
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}