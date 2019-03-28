package com.project.protip;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Network;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener{
    TextView textView2,textView4;
    private MapView mMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    public void contact(View view){
        //Toast.makeText(this, "Phone have been clicked", Toast.LENGTH_SHORT).show();
       if(view.getId()==R.id.phone){
           if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
               ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
               ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
               ClipData clip = ClipData.newPlainText("Phone", "8961653006");
               clipboard.setPrimaryClip(clip);
               Toast.makeText(this, "Since you have not granted permissions, the number is copied to your clipboard", Toast.LENGTH_LONG).show();
           }else {
               dialContactPhone("9163000516");
           }
       }
       else if(view.getId()==R.id.mail){
           String subject = "";
           String body = "";
           Intent intent = new Intent(Intent.ACTION_VIEW);
           Uri data = Uri.parse("mailto:coc.ayan1@gmail.com?subject=" + subject + "&body=" + body);
           intent.setData(data);
           startActivity(intent);
       }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                dialContactPhone("9163000516");
            }
        }
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
    protected DrawerLayout drawerLayout;
    protected ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       textView2 = (TextView)findViewById(R.id.textView2);
       textView4 = (TextView)findViewById(R.id.textView4);
       textView2.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent ac commodo felis. Sed ac neque congue, consequat dui a, tristique nisi. Nam nec sodales nibh, euismod tempus lorem. Aliquam erat volutpat. Aenean molestie, justo vel euismod tincidunt, nunc ipsum convallis turpis, a semper felis justo vitae massa. Etiam faucibus dignissim quam, ut cursus urna ornare nec. Nam ultricies elit blandit orci suscipit, ac porttitor diam tincidunt. Phasellus tempus velit eget neque hendrerit, bibendum tincidunt turpis cursus.");
       textView4.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus euismod ipsum ut purus porta euismod. Cras gravida lacus nec rhoncus tincidunt. Integer non rutrum leo, sit amet facilisis nisl. Pellentesque malesuada ante eget odio pellentesque scelerisque. Nullam vitae mollis neque. Aenean at massa sit amet turpis placerat ultrices. Ut hendrerit eleifend nulla pellentesque fermentum. Cras lobortis massa massa, ut ornare turpis luctus quis. Integer ullamcorper justo non erat fringilla mollis. Etiam velit enim, tincidunt ut mollis ac, scelerisque at erat. Suspendisse vel augue posuere, convallis erat eget, ornare lacus. Vivamus nec est porta, blandit orci quis, pretium nunc. In id tempus erat.");
       textView2.setTextColor(Color.parseColor("#FFFFFF"));
       NavigationView navigationView = (NavigationView)findViewById(R.id.nv);
       navigationView.setNavigationItemSelectedListener(this);
       initGoogleMap(savedInstanceState);
       if(savedInstanceState == null)
        navigationView.setCheckedItem(R.id.home);
        }
    private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(22.763448,88.3508166)).title("ProTip Pvt Ltd"));
        LatLng location = new LatLng(22.763448,88.3508166);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flcontent,new Home()).commit();
            break;
            case R.id.search:
                getSupportFragmentManager().beginTransaction().replace(R.id.flcontent,new Search()).commit();
                break;
            case R.id.student:
                getSupportFragmentManager().beginTransaction().replace(R.id.flcontent,new Student()).commit();
                break;
            case R.id.parents:
                getSupportFragmentManager().beginTransaction().replace(R.id.flcontent,new Parents()).commit();
                break;
            case R.id.college:
                getSupportFragmentManager().beginTransaction().replace(R.id.flcontent,new College()).commit();
                break;
            case R.id.setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.flcontent,new Settings()).commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

