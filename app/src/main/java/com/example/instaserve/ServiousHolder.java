package com.example.instaserve;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ServiousHolder extends RecyclerView.ViewHolder {
    View view;
    public ServiousHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }
    public void setdetails(Context context, String name,String charge,String title,String phoneNo, String image){
        TextView textView = view.findViewById(R.id.servious_name);
        Log.i("user", title);
        ImageView imageView = view.findViewById(R.id.image);
        TextView nam=view.findViewById(R.id.name);
        TextView charg=view.findViewById(R.id.charge);
        TextView titl=view.findViewById(R.id.title);
        TextView phone=view.findViewById(R.id.phoneNo);
        nam.setText(name);
        charg.setText("Charge: "+charge);
        titl.setText("Title: "+title);
        phone.setText("Contact No: "+phoneNo);
        System.out.println(image);
        Picasso.with(context).load(image).into(imageView);
        //   Picasso.get().load(image).into(imageView);
    }
}
