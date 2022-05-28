package com.example.instaserve;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.net.Uri;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SliderLayout sliderLayout;
    Intent intent;
    HashMap<String, Integer> Hash_file_maps ;
    RecyclerView recyclerView ;
    DatabaseReference Dataref;
    String city[] = {"Search in different Locality", "Indore", "Bhopal", "Jabalpur", "Gwalior", "Ujjain", "Sagar", "Dewas", "Satna", "Ratlam",
            "Murwara", "Singrauli", "Burhanpur", "Khandwa", "Khargone", "Bhind", "Chhindwara", "Guna", "Rewa", "Shivpuri",
            "Vidisha", "Chhatarpur", "Mandsaur", "Khargone", "Neemuch", "Pithampur", "Narmadapuram", "Itarsi",
            "Sehore", "Morena", "Damoh", "Betul", "Seoni", "Datia", "Nagda", "Dindori"};

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    ImageView camera;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        intent = new Intent(getContext(),ShowProviderActivity.class);
        final Spinner spinnerDropDownView = (Spinner) view.findViewById(R.id.city);
        ArrayAdapter<String> aadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, city);
        spinnerDropDownView.setAdapter(aadapter);
        spinnerDropDownView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("city", spinnerDropDownView.getSelectedItem().toString());
                if (!spinnerDropDownView.getSelectedItem().toString().equalsIgnoreCase("Search in different Locality")) {
                    String local = spinnerDropDownView.getSelectedItem().toString();
                    intent.putExtra("city", local);
                    Toast.makeText(getContext(), local, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sliderLayout = view.findViewById(R.id.slider);
        Hash_file_maps = new HashMap<String, Integer>();

        Hash_file_maps.put("Welcome to Your  Intaserve", R.drawable.frutorials);
        Hash_file_maps.put("Transforming Small scale to big market", R.drawable.header_image);
        Hash_file_maps.put("Improving market for your shop", R.drawable.header_image);
        Hash_file_maps.put("", R.drawable.hero_image);


        for(String name : Hash_file_maps.keySet()){

            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description(name)
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);
            sliderLayout.addSlider(textSliderView);
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);
        recyclerView =view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        OnStart();


        return view;
    }

    private void OnStart() {
        super.onStart();
        Log.i("checkin","working");

        Dataref = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("Category");
        FirebaseRecyclerAdapter<Category,ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Category,ViewHolder>(
                        Category.class,
                        R.layout.card_services,
                        ViewHolder.class,
                        Dataref
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, final Category category, final int i) {
                        Log.i("cat",category.toString());
                    viewHolder.setdetails(getContext(),category.getName(),category.getImage());
                    viewHolder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            intent.putExtra("category",category.getName());
                            startActivity(intent);
                        }
                    });
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();

    }
    @Override
    public void onSliderClick(BaseSliderView slider) {

        Toast.makeText(getContext(),slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

}
