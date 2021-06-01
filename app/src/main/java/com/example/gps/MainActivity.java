package com.example.gps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

i

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    protected String latitude;
    protected String longitude;

    public void onRequestPermissionResult(int requestCode,
                                          @NonNull String[] permissions,
                                          @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 12345)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Location Permission Granted", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(MainActivity.this, "Location Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    public void getLocation(View view){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location last_location) {
                    if (last_location != null) {
                        TextView location_info = (TextView) findViewById(R.id.textView2);
                        latitude = String.valueOf(last_location.getLatitude());
                        longitude = String.valueOf(last_location.getLongitude());
                        location_info.setText("Latitiude : " + latitude+ "\nLongitude : " + longitude);
                    }
                }
            });
        }
        else{
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},
                    12345);
        }
    }
    public void goToMaps(View view){
        Uri gmmIntentUri = Uri.parse("google.streetview:cbll="+latitude+","+longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}