package edu.wpi.wgo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public final int CREATE_EVENT = 100;
    private List<Event> events = new ArrayList<Event>();
    ArrayAdapter<Event> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        events.add(new Event(null, "Dummy", "123 Highland", new GregorianCalendar().getTime(),
                new GregorianCalendar().getTime(), "Stuff's going on"));
        adapter = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_1, events);
        ListView listView = (ListView) findViewById(R.id.eventList);
        listView.setAdapter(adapter);

        // Create a message handling object as an anonymous class.


        listView.setOnItemClickListener(mMessageClickedHandler);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_settings:
                return true;
            case R.id.create_event:
                Intent intent = new Intent(this, CreateEvent.class);
                startActivityForResult(intent, CREATE_EVENT);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CREATE_EVENT){
            if(resultCode == RESULT_OK){
                Bundle results = data.getExtras();
//                Event newEvent = new Event((Bitmap)results.get("Bitmap"), (String)results.get("Name"),
//                        (String)results.get("Location"), (Date)results.get("Start"),
//                        (Date)results.get("End"), (String)results.get("Description"));
                Event newEvent = (Event)results.get("Event");
                events.add(newEvent);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // Do something in response to the click
            Intent intent = new Intent(MainActivity.this, EventDetail.class);
            intent.putExtra("Event", events.get(position));
            startActivity(intent);
        }
    };
}
