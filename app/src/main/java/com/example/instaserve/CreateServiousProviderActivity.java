package com.example.instaserve;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CreateServiousProviderActivity extends AppCompatActivity {

    ImageView image;
    EditText serviousprovider_name;
    EditText phone;
    EditText whatapp;
    EditText city;
    EditText title;
    EditText charge;
    EditText servious_name;
    EditText addar;
    DatabaseReference dataref;
    StorageReference image_save;
    EditText address;
    Button submit;
    private ProgressDialog progressDialog;

    public void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//hide the action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//hide the status
        setContentView(R.layout.activity_create_servious_provider);
        image=findViewById(R.id.image);
        serviousprovider_name=findViewById(R.id.serviousprovider_name);
        phone=findViewById(R.id.Phone);
        whatapp=findViewById(R.id.whatapp);
        title=findViewById(R.id.title);
        servious_name=findViewById(R.id.servious_name);
        addar=findViewById(R.id.addar);
        address=findViewById(R.id.address);
        city=findViewById(R.id.city);
        charge=findViewById(R.id.charge);
        submit=findViewById(R.id.submit);
        progressDialog = new ProgressDialog(this);
        image_save = FirebaseStorage.getInstance().getReference().child("Servious_Provider");
        dataref = FirebaseDatabase.getInstance().getReference().child("Servious_Provider");
        image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                chooseImageClicked();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              valid();
            }
        });

    }

    void valid(){
        if(TextUtils.isEmpty(serviousprovider_name.toString())){
            serviousprovider_name.setError("Empty Field servious provider Name");
        }
        if(TextUtils.isEmpty(phone.toString()) && phone.toString().length()==10){
            phone.setError("Empty or Field Invalid Number");
        }
        if(TextUtils.isEmpty(whatapp.toString()) && whatapp.toString().length()==10){
            whatapp.setError("Empty or Field Invalid Number");
        }
        if(TextUtils.isEmpty(title.toString())){
            title.setError("Empty Field servious provider Name");
        }
        if(TextUtils.isEmpty(addar.toString())){
            addar.setError("Empty Field");
        }
        if(TextUtils.isEmpty(address.toString())){
            address.setError("Empty Field");
        }
        if(TextUtils.isEmpty(city.toString())){
           city.setError("Empty Field");
        }
        if(TextUtils.isEmpty(charge.toString())){
            charge.setError("Empty Field");
        }
        if(TextUtils.isEmpty(servious_name.getText().toString()))
            servious_name.setError("Empty Field");
        CreateAccount();
    }

    private void CreateAccount() {
        progressDialog.setTitle("Creating Category");
        progressDialog.setMessage("Please wait, While we are checking the credentails.");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        String saveDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveTime = currentTime.format(calendar.getTime());
        final String image_name = serviousprovider_name.getText().toString()+saveDate+saveTime +".jpg";
        image.setDrawingCacheEnabled(true);
        image.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final byte[] data = baos.toByteArray();
        final UploadTask uploadTask = image_save.child(image_name).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(getApplicationContext(),exception.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl = uri.toString();
                        Log.i("urlimage",imageUrl);
                        String phoneNo=phone.getText().toString();
                        String name=serviousprovider_name.getText().toString();
                        String whatap=whatapp.getText().toString();
                        String charg = charge.getText().toString();
                        String titl=title.getText().toString();
                        String citi=city.getText().toString();
                        String aadhar = addar.getText().toString();
                        String addres = address.getText().toString();
                        String category=servious_name.getText().toString();
                        HashMap <String,String> hashMap = new HashMap<String,String>();
                        hashMap.put("contactNo",phoneNo);
                        hashMap.put("name",name);
                        hashMap.put("Category",category);
                        hashMap.put("whatapp",whatap);
                        hashMap.put("title",titl);
                        hashMap.put("image",imageUrl);
                        hashMap.put("Charge",charg);
                        hashMap.put("locality",citi);
                        hashMap.put("aadhar",aadhar);
                        hashMap.put("address",addres);
                        dataref.child(phoneNo).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Creating done",Toast.LENGTH_LONG).show();
                                   startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                }else{
                                    Toast.makeText(getApplicationContext(),"Error " + task.getException().toString(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                });
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(getApplicationContext(),"Account Created!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),CreateCategoryActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),ChooseModeActivity.class));
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    public void chooseImageClicked() {
        Log.i("image", "Method");
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            getPhoto();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage = data.getData();
        if(requestCode == 1&& resultCode == RESULT_OK && data != null){
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                image.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}