package com.example.instaserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;
    Editable username;
    TextInputLayout textInputLayout1;
    Editable password ;
    Button login ;
    Button phoneLogin;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    android.app.AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//hide the action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//hide the status
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(LoginActivity.this);
        textInputLayout = findViewById(R.id.username);
        ImageView back= findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ChooseModeActivity.class));
            }
        });
         username = textInputLayout.getEditText().getText();
        textInputLayout1 = findViewById(R.id.pass);
         password = textInputLayout1.getEditText().getText();
         login = findViewById(R.id.login);
         Button createAccount = findViewById(R.id.createAccount);
         createAccount.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
             }
         });
        sharedPreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        phoneLogin=findViewById(R.id.phoneLogin);
        phoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PhoneLoginActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty((username.toString())) && username.toString().length()<10)
                    textInputLayout.setError("Invalid username");
                if(TextUtils.isEmpty(password.toString()) && password.toString().length()<5)
                    textInputLayout1.setError("Invalid Password");
                else
                Register(username.toString(),password.toString());
            }
        });
    }
    public void checkInternet(View view){
        if(!isConnected(this)){
            showCustomDialog();
        }
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("PLease connect to the internet to proceed further").setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(),ChooseModeActivity.class));
                    }
                });
    }

    boolean isConnected(LoginActivity loginActivity){
        ConnectivityManager connectivityManger = (ConnectivityManager) loginActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wificonnection = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconnection = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wificonnection!=null && wificonnection.isConnected()) || (mobileconnection!=null && mobileconnection.isConnected()))
        return true;
        else return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    void Register(final String username, String password){
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait, While we are checking the credentails.");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        final FirebaseAuth fAuth = FirebaseAuth.getInstance();
        Log.i("user",username+" "+password);
        fAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"User LogedIn",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.apply();
                    editor.commit();
                    //  if(Prevalent.currentOnlineUser.Authority.equalsIgnoreCase("User"))
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
                else{
                    progressDialog.dismiss();
                    builder = new android.app.AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Login Failed! ");
                    builder.setMessage("Invalid Username Password!");
                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create().show();
                    Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                }
            }
        });
    }

}

