package com.stxnext.stxinsider;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity {

    private GoogleMap map;
    private final LatLng OFFICE_LOCATION = new LatLng(52.3944957,16.8936571);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().setTitle("Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prepareMap();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    private void prepareMap() {
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_fragment);
        if (fragment != null) {
            map = fragment.getMap();
            if (map != null) {

                MarkerOptions options = new MarkerOptions();
                options.position(OFFICE_LOCATION);
                map.addMarker(options);

                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                map.getUiSettings().setAllGesturesEnabled(false);

                animateMap();
            }
        }
    }

    private void animateMap() {
        if (map != null) {
            LatLng cameraPosition = new LatLng(OFFICE_LOCATION.latitude + 0.02, OFFICE_LOCATION.longitude);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition, 12), 2000, null);
        }
    }
}
