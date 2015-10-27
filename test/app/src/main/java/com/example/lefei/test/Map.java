package com.example.lefei.test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Lefei on 10/23/2015.
 */
public class Map extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap myMap;
    private static final float zoom = 14.0f;
    double lat = 42.349240;
    double lng = -71.050046;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_frag);

        //myMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        //myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

       // myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));


            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


    }






    public void onClick(View v) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onMapReady(GoogleMap map) {
        LatLng company = new LatLng(lat, lng);
        map.addMarker(new MarkerOptions()
                .position(company)
                .title("Mobile Cuts Boston")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
        map.moveCamera(CameraUpdateFactory.newLatLng(company));
    }
}

