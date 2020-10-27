package com.example.mappactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationDataActivity extends AppCompatActivity {

    Button find;

    FusedLocationProviderClient fusedLocationProviderClient;

    public static Double lat,lng;
    public static String getAddress;

    TextView latlng,fulladdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_data);

        find = findViewById(R.id.find);

        latlng=findViewById(R.id.latlng);
        fulladdress=findViewById(R.id.address);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LocationDataActivity.this);
                if (ActivityCompat.checkSelfPermission(LocationDataActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationDataActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if(location!=null)
                        {
                            lat=location.getLatitude();
                            lng=location.getLongitude();

                            findAddress(lat,lng);

                            latlng.setText(lat +","+lng);
                            fulladdress.setText(getAddress);

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        e.printStackTrace();
                    }
                });

            }
        });


    }
    public void findAddress(Double lat,Double lng)
    {

        Geocoder geocoder=new Geocoder(LocationDataActivity.this, Locale.getDefault());

        try {
            List<Address> addressList=geocoder.getFromLocation(lat,lng,1);
            Address address=addressList.get(0);
            getAddress=address.getAddressLine(0);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}