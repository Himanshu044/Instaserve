package com.example.instaserve;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowProviderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//hide the action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//hide the status
        setContentView(R.layout.activity_show_provider);
        TextView textView = findViewById(R.id.text);
        textView.setText("Show all Servious Provider");
        textView.setVisibility(View.VISIBLE);
        setTitle("Show all Servious Provider");
        Intent intent=getIntent();
        recyclerView =findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        if(intent.hasExtra("city")==false) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("PLease Select your city").setCancelable(false);
            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
            }).create().show();
        }

        String category=intent.getStringExtra("category");
        String city=intent.getStringExtra("city");
        Log.i("test",category+" "+city);
        if(category==null || city==null){
            textView.setText("Currently no servious available");
        }
        setInfo(category,city);
    }

    private void setInfo(final String category, final String city) {
       DatabaseReference Dataref = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("Servious_Provider");
        FirebaseRecyclerAdapter<ServiousProvider, ServiousHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ServiousProvider, ServiousHolder>(
                        ServiousProvider.class,
                        R.layout.card_services_provider,
                        ServiousHolder.class,
                        Dataref.orderByChild("Category").equalTo(category.toLowerCase())

                ) {
                    @Override
                    protected void populateViewHolder(ServiousHolder serviousHolder, final ServiousProvider serviousProvider, int i) {
                        Log.i("data",serviousProvider.toString());
                       // if(serviousProvider.getLocality().equalsIgnoreCase(city))
                        serviousHolder.setdetails(getApplicationContext(),serviousProvider.getName(),serviousProvider.getCharge(),serviousProvider.getTitle(),serviousProvider.getContactNo(),serviousProvider.getImage());
                       serviousHolder.view.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               Intent intent=new Intent(getApplicationContext(),viewServiousProviderActivity.class);
                               intent.putExtra("name",serviousProvider.getName());
                               intent.putExtra("category",serviousProvider.getCategory());
                               intent.putExtra("locality",serviousProvider.getLocality());
                               intent.putExtra("title",serviousProvider.getTitle());
                               intent.putExtra("phone",serviousProvider.getContactNo());
                               intent.putExtra("charge",serviousProvider.getCharge());
                               intent.putExtra("image",serviousProvider.getImage());
                               intent.putExtra("whatapp",serviousProvider.getWhatapp());
                               intent.putExtra("aadhar",serviousProvider.getAadhar());
                               intent.putExtra("address",serviousProvider.getAddress());
                               startActivity(intent);
                           }
                       });
                    }

                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
    }
}