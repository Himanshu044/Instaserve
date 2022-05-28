package com.example.instaserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowfeedbackActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//hide the action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//hide the status
        setContentView(R.layout.activity_showfeedback);
        recyclerView =findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        setInfo();
    }
    private void setInfo() {
        DatabaseReference Dataref = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("Feedback");
        FirebaseRecyclerAdapter<feedback, FeedbackHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<feedback,FeedbackHolder>(
                        feedback.class,
                        R.layout.card_feedback,
                        FeedbackHolder.class,
                        Dataref

                ) {


                    @Override
                    protected void populateViewHolder(FeedbackHolder feedbackHolder, feedback feedback, int i) {
                        feedbackHolder.setdetails(getApplicationContext(),feedback.getUsername(),feedback.getFeedback());
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