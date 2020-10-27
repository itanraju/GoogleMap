package com.example.mappactivity;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SearchLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    EditText city;
    Button findlocation;

    String sfindaddress;

    Double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(SearchLocation.this);

        city=findViewById(R.id.city);
        findlocation=findViewById(R.id.location);

        findlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onMapReady(mMap);

                LatLng latLng=new LatLng(lat,lng);
                CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngZoom(latLng,10);
                mMap.animateCamera(cameraUpdate);
                mMap.addMarker(new MarkerOptions().position(latLng).title(sfindaddress));

            }
        });


    }
    public void findCity(String cityname)
    {
        sfindaddress=city.getText().toString();

        Geocoder geocoder=new Geocoder(this);
        try {
            List<Address> addressList=geocoder.getFromLocationName(sfindaddress,1);
            Address address=addressList.get(0);

            lat=address.getLatitude();
            lng=address.getLongitude();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        findCity(sfindaddress);

        // Add a marker in Sydney and move the camera

    }
}