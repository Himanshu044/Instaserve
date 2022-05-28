package com.example.instaserve;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class FeedbackHolder extends RecyclerView.ViewHolder {
    View view;
    public FeedbackHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }
    public void setdetails(Context context, String title, String feedback){
        TextView textView = view.findViewById(R.id.name);
        TextView textView1=view.findViewById(R.id.feedback);
        textView.setText(title);
        Log.i("user", title);
        textView1.setText(feedback);
      //  Picasso.with(context).load(image).into(imageView);
        //   Picasso.get().load(image).into(imageView);
    }
}
