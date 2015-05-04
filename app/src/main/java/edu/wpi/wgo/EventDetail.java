package edu.wpi.wgo;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class EventDetail extends FragmentActivity {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Event event = (Event)getIntent().getExtras().get("Event");
        ImageView eventPhoto = (ImageView)this.findViewById(R.id.photo);
        if(event.getEventPhoto() != null){
            eventPhoto.setImageBitmap(event.getEventPhoto());
        }
        ((TextView)this.findViewById(R.id.name)).setText(event.getName());
        ((TextView)this.findViewById(R.id.location)).setText(event.getLat() + " " + event.getLng());
        ((TextView)this.findViewById(R.id.start)).setText(event.getStart().toString());
        ((TextView)this.findViewById(R.id.end)).setText(event.getEnd().toString());
        ((TextView)this.findViewById(R.id.description)).setText(event.getDescription());
        ((TextView)this.findViewById(R.id.tags)).setText(event.getTags());

        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        map.addMarker(new MarkerOptions().position(new LatLng(event.getLat(), event.getLng())).title(event.getName()).snippet(event.getTags()));

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent prefIntent = new Intent(this, Preferences.class);
            prefIntent.putExtra( PreferenceActivity.EXTRA_SHOW_FRAGMENT, Preferences.Prefs1Fragment.class.getName() );
            prefIntent.putExtra( PreferenceActivity.EXTRA_NO_HEADERS, true );
            startActivity(prefIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
