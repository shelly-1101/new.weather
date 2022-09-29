package com.example.newweather;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {

    double lat, lon;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLocation();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLocation()
    {




        //for getting location
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //for accessing location services
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

         //checking permissions

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }
        else
        {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null)
                    {
                        lon = location.getLongitude();
                        lat = location.getLatitude();
                        Toast.makeText(MainActivity.this, "Hurrah!!.....Got your Location!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                        {
                            buildGPSAlertBox();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Some problem in fetching location", Toast.LENGTH_SHORT).show();

                    }

                }
            });

        }
    }
    private void buildGPSAlertBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!").setMessage("It seems your GPS is off. Turn it on?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        builder.create().show();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID)
        {
            case R.id.refresh:
                break;

            case R.id.get_location:
                break;

            case R.id.About:
                break;

            case R.id.rate_it:
                break;

            case R.id.share:
                break;

            case R.id.exit:
                finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

