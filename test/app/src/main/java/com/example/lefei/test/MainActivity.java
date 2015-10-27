package com.example.lefei.test;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, OnTabChangeListener {

    TabHost tabs;

    //service tab variables
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    private ArrayList<Object> childItems2 = new ArrayList<Object>();

    //direction tab variable
    //Button map_button;
    private GoogleMap myMap;
    private static final float zoom = 14.0f;
    double lat = 42.349240;
    double lng = -71.050046;

    //social tab variables
    ImageButton facebook;
    ImageButton twitter;
    ImageButton instagram;
    ImageButton blog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tabs=(TabHost)findViewById(R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec;

        spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("");
        tabs.addTab(spec);

        ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.list);

        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        setGroupParents();
        setChildData();

        CustomExpandableList adapter = new CustomExpandableList(parentItems, childItems, childItems2);

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);
       // expandableList.setOnChildClickListener(this);




        spec = tabs.newTabSpec("tag3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("");
        tabs.addTab(spec);

        /*map_button = (Button)findViewById(R.id.map_button);

        map_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), Map.class);
                startActivity(i);
            }
        });*/

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);






        spec = tabs.newTabSpec("tag4");
        spec.setContent(R.id.tab4);
        spec.setIndicator("");
        tabs.addTab(spec);

        facebook = (ImageButton) findViewById (R.id.facebook_button);
        twitter = (ImageButton) findViewById (R.id.twitter_button);
        instagram = (ImageButton) findViewById (R.id.instagram_button);
        blog = (ImageButton) findViewById (R.id.blog_button);

        facebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    ApplicationInfo info = getPackageManager().
                            getApplicationInfo("com.facebook.katana", 0 );
                    Toast.makeText(MainActivity.this,
                            "true", Toast.LENGTH_SHORT).show();
                } catch( PackageManager.NameNotFoundException e ){
                    Toast.makeText(MainActivity.this,
                            "false", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tabs.getTabWidget().setDividerDrawable(null);
        tabs.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.service_icon_onclick);
        tabs.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.booking_icon);
        tabs.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.direction_icon);
        tabs.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.social_icon);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int tab = tabs.getCurrentTab();
                View v = tabs.getTabWidget().getChildAt(tab);
                if (tab == 0) {
                    v.setBackgroundResource(R.drawable.service_icon_onclick);
                    tabs.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.booking_icon);
                    tabs.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.direction_icon);
                    tabs.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.social_icon);
                } else if (tab == 1) {
                    v.setBackgroundResource(R.drawable.booking_icon_onclick);
                    tabs.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.service_icon);
                    tabs.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.direction_icon);
                    tabs.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.social_icon);

                } else if (tab == 2) {
                    v.setBackgroundResource(R.drawable.direction_icon_onclick);
                    tabs.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.booking_icon);
                    tabs.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.service_icon);
                    tabs.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.social_icon);




                } else if (tab == 3) {
                    v.setBackgroundResource(R.drawable.social_icon_onclick);
                    tabs.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.booking_icon);
                    tabs.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.service_icon);
                    tabs.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.direction_icon);
                }
            }
        });
    }



    @Override
    public void onTabChanged(String tabId) {
        // TODO Auto-generated method stub

    }

    public void setGroupParents() {
        parentItems.add("Cuts");
        parentItems.add("Elite");
        parentItems.add("Executive");
    }

    public void setChildData() {

        // Cuts
        ArrayList<String> child = new ArrayList<String>();
        child.add("Buzz Style Cut");
        child.add("Hair Cut");
        child.add("Artistic Shave");
        childItems.add(child);

        // Elite
        child = new ArrayList<String>();
        child.add("Cut");
        child.add("Shampoo");
        child.add("Scalp Maniplation");
        child.add("Shoe Shine");
        childItems.add(child);

        // Executive
        child = new ArrayList<String>();
        child.add("Cut");
        child.add("Shampoo");
        child.add("Scalp & Face Massage");
        child.add("Hot Towel");
        child.add("Straight-Razor Shave");
        child.add("Shoe Shine");
        childItems.add(child);

        // Cuts
        ArrayList<String> child2 = new ArrayList<String>();
        child2.add("20 min   $25");
        child2.add("30 min   $35");
        child2.add("30 min   $45");
        childItems2.add(child2);

        // Elite
        child2 = new ArrayList<String>();
        child2.add("30 min   $50");
        child2.add("30 min   $50");
        child2.add("30 min   $50");
        child2.add("30 min   $50");
        childItems2.add(child2);

        // Executive
        child2 = new ArrayList<String>();
        child2.add("60 min   $75");
        child2.add("60 min   $75");
        child2.add("60 min   $75");
        child2.add("60 min   $75");
        child2.add("60 min   $75");
        child2.add("60 min   $75");
        childItems2.add(child2);


    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.logout:

                SaveSharedPreference.setUserName(MainActivity.this, "");

                Intent i = new Intent(getBaseContext(), Start.class);
                startActivity(i);
                return true;






            default:
                return super.onOptionsItemSelected(item);
        }
    }



    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"someone@gmail.com"};
        String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onMapReady(GoogleMap map) {
        LatLng company = new LatLng(lat, lng);
        map.addMarker(new MarkerOptions()
                .position(company)
                .title("Mobile Cuts Boston")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
        map.moveCamera(CameraUpdateFactory.newLatLng(company));

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
    }
}
