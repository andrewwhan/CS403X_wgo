package edu.wpi.wgo;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
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


public class MainActivity extends Activity {

    public final int CREATE_EVENT = 100;
    private List<Event> events;
    ArrayAdapter<Event> adapter;
    private EventDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new EventDatabaseHelper(this);
        events = dbHelper.getLiveEvents();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, events);
        ListView listView = (ListView) findViewById(R.id.eventList);
        listView.setAdapter(adapter);
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
                Intent prefIntent = new Intent(this, Preferences.class);
                prefIntent.putExtra( PreferenceActivity.EXTRA_SHOW_FRAGMENT, Preferences.Prefs1Fragment.class.getName() );
                prefIntent.putExtra( PreferenceActivity.EXTRA_NO_HEADERS, true );
                startActivity(prefIntent);
                break;
            case R.id.create_event:
                Intent createIntent = new Intent(this, CreateEvent.class);
                startActivityForResult(createIntent, CREATE_EVENT);
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
                newEvent.setId(dbHelper.insertEvent(newEvent));
                events.add(newEvent);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Intent intent = new Intent(MainActivity.this, EventDetail.class);
            intent.putExtra("Event", events.get(position));
            startActivity(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, NotificationService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        am.cancel(pi);
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        int minutes = Integer.parseInt(sharedPreferences.getString("notification_frequency", "0"));
        if (minutes > 0) {
            am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + minutes*60*1000,
                    minutes*60*1000, pi);
        }
    }
}
