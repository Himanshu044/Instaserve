package com.example.instaserve;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    View view;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }
    public void setdetails(Context context, String title, String image){
        TextView textView = view.findViewById(R.id.servious_name);
        Log.i("user", title);
        de.hdodenhof.circleimageview.CircleImageView imageView = view.findViewById(R.id.cat_image);
        TextView textView1=view.findViewById(R.id.skill);
        textView1.setText("200+ Skilled "+ title +" ready to work");
        textView.setText(title.toUpperCase());
        System.out.println(image);
        Picasso.with(context).load(image).into(imageView);
        //   Picasso.get().load(image).into(imageView);
    }
}
