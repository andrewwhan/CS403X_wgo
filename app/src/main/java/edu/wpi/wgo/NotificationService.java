package edu.wpi.wgo;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import java.util.List;

/**
 * Created by Andrew on 4/24/2015.
 */
public class NotificationService extends Service {

    private PowerManager.WakeLock mWakeLock;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void handleIntent(Intent intent) {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "WGO");
        mWakeLock.acquire();

        new PollTask().execute();
    }

    private class PollTask extends AsyncTask<Void, Void, Void> {
        List<Event> upcomingEvents = null;
        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(NotificationService.this);
            upcomingEvents = new EventDatabaseHelper(NotificationService.this).upcomingEvents(
                    Integer.parseInt(sharedPreferences.getString("notification_time", "0")) * 60 * 1000);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(upcomingEvents != null && !upcomingEvents.isEmpty()){
                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this);
                builder.setSmallIcon(R.drawable.goat);
                builder.setContentTitle("Event Goating On!");
                builder.setContentText(upcomingEvents.size() + " events happening soon");
                NotificationManager notServ = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                notServ.notify(100, builder.build());
                stopSelf();
            }

        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        handleIntent(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_NOT_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        mWakeLock.release();
    }
}