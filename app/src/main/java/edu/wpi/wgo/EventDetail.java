package edu.wpi.wgo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ImageView;


public class EventDetail extends ActionBarActivity {

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
        ((TextView)this.findViewById(R.id.location)).setText(event.getLocation());
        ((TextView)this.findViewById(R.id.start)).setText(event.getStart().toString());
        ((TextView)this.findViewById(R.id.end)).setText(event.getEnd().toString());
        ((TextView)this.findViewById(R.id.description)).setText(event.getDescription());
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
