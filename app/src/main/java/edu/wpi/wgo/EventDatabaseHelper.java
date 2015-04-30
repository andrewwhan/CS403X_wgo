package edu.wpi.wgo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Andrew on 4/27/2015.
 */
public class EventDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "event.sqlite";
    private static final int VERSION = 1;

    private static final String TABLE_EVENT = "event";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_START = "start";
    private static final String COLUMN_END = "end";
    private static final String COLUMN_DESCRIPTION = "description";

    public EventDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public EventDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public EventDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table event (id integer primary key autoincrement, image blob, name text," +
                "location text, start integer, end integer, description text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertEvent(Event e) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_IMAGE, bitmapToByte(e.getEventPhoto()));
        cv.put(COLUMN_NAME, e.getName());
        cv.put(COLUMN_LOCATION, e.getLocation());
        cv.put(COLUMN_START, e.getStart().getTime());
        cv.put(COLUMN_END, e.getEnd().getTime());
        cv.put(COLUMN_DESCRIPTION, e.getDescription());
        return getWritableDatabase().insert(TABLE_EVENT, null, cv);
    }

    public List<Event> getEvents(){
        Cursor wrapped = getReadableDatabase().query(TABLE_EVENT, null, null, null, null, null, null);
        wrapped.moveToFirst();
        ArrayList<Event> events = new ArrayList<>();
        for(int i=0; i<wrapped.getCount(); i++){
            Event event = new Event(wrapped.getLong(0), byteToBitmap(wrapped.getBlob(1)),
                    wrapped.getString(2), wrapped.getString(3), new Date(wrapped.getLong(4)),
                    new Date(wrapped.getLong(5)), wrapped.getString(6));
            events.add(event);
            wrapped.moveToNext();
        }
        return events;
    }

    public List<Event> upcomingEvents(long period){
        Cursor wrapped = getReadableDatabase().query(TABLE_EVENT, null, COLUMN_START + " < ? AND "
        + COLUMN_START + " > ?", new String[]{String.valueOf(new GregorianCalendar().getTimeInMillis() + period),
        String.valueOf(new GregorianCalendar().getTimeInMillis())}, null, null, null);
        wrapped.moveToFirst();
        ArrayList<Event> events = new ArrayList<>();
        for(int i=0; i<wrapped.getCount(); i++){
            Event event = new Event(wrapped.getLong(0), byteToBitmap(wrapped.getBlob(1)),
                    wrapped.getString(2), wrapped.getString(3), new Date(wrapped.getLong(4)),
                    new Date(wrapped.getLong(5)), wrapped.getString(6));
            events.add(event);
        }
        return events;
    }

    public Event queryEvent(long id){
        Cursor wrapped = getReadableDatabase().query(TABLE_EVENT, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null, "1");
        if(wrapped.getCount() > 0){
            wrapped.moveToFirst();
            Event event = new Event(wrapped.getLong(0), byteToBitmap(wrapped.getBlob(1)),
                    wrapped.getString(2), wrapped.getString(3), new Date(wrapped.getLong(4)),
                    new Date(wrapped.getLong(5)), wrapped.getString(6));
            return event;
        }

        return null;
    }

    public byte[] bitmapToByte(Bitmap bitmap){
        if(bitmap == null){
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public Bitmap byteToBitmap(byte[] image){
        if(image == null){
            return null;
        }
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
