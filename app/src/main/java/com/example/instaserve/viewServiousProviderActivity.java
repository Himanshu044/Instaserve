package com.example.instaserve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class viewServiousProviderActivity extends AppCompatActivity {

    TextView name;
    TextView category;
    TextView citi;
    TextView phoneNo;
    TextView whatappNo;
    TextView other;
    TextView address;
    TextView title;
    ImageView call;
    ImageView message;
    String cat;
    String city;
    String phone;
    ImageView whatapp_call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_servious_provider);
        Intent intent=getIntent();
        cat=intent.getStringExtra("category");
        city=intent.getStringExtra("city");
        String name1=intent.getStringExtra("name");
        String cat1=intent.getStringExtra("category");
        String locality1=intent.getStringExtra("locality");
        String title1=intent.getStringExtra("title");
        final String phone1=intent.getStringExtra("phone");
        String charge1=intent.getStringExtra("charge");
        String image1=intent.getStringExtra("image");
        String whatapp1=intent.getStringExtra("whatapp");
        String aadhar1=intent.getStringExtra("aadhar");
        String address1=intent.getStringExtra("address");
        // Toast.makeText(getApplicationContext(),cat+" "+city+" "+phone,Toast.LENGTH_SHORT).show();
        whatapp_call=findViewById(R.id.whatapp_call);
        ImageView imageView=findViewById(R.id.imageView1);
        Picasso.with(viewServiousProviderActivity.this).load(image1).into(imageView);
        name=findViewById(R.id.name);
        name.setText("Name: "+name1);
        category=findViewById(R.id.category_name);
        category.setText("Servious: "+cat1);
        citi=findViewById(R.id.locality);
        citi.setText(locality1);
        title=findViewById(R.id.bloodgp);
        title.setText("Shop Name: "+title1);
        phoneNo=findViewById(R.id.phone_number);
        phoneNo.setText("Contact No: "+phone1);
        whatappNo=findViewById(R.id.whatapp_number);
        whatappNo.setText("Whatapp No: "+whatapp1);
        other=findViewById(R.id.other);
        other.setText("Servious Charge: "+charge1);
        address=findViewById(R.id.address);
        address.setText("Address: "+address1);
        call=findViewById(R.id.call);
        message=findViewById(R.id.message);
        Button book =findViewById(R.id.book);
        Button pay=findViewById(R.id.pay);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Servious Booked",Toast.LENGTH_LONG).show();
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        whatapp_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone="+phone1;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+phone1));
                startActivity(i);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone1, null)));;
            }
        });
    }
}