package edu.wpi.wgo;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Daniel on 5/1/2015.
 */
public class MapActivity extends Activity implements OnMapReadyCallback {

    LatLng wpi;
    Button doneButton;
    Marker mark;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendCoordinates();
            }
        });


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public void sendCoordinates() {
        Intent intent = new Intent();
        intent.putExtra("lat", mark.getPosition().latitude);
        intent.putExtra("lng", mark.getPosition().longitude);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public void onMapReady(GoogleMap map) {
        wpi = new LatLng(42.273856,-71.805976);
        mark = map.addMarker(new MarkerOptions()
                .position(wpi)
                .draggable(true));
        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener(){

            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });
    }

}
