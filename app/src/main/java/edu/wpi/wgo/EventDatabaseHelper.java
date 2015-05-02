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

public class EventDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "event.sqlite";
    private static final int VERSION = 3;

    private static final String TABLE_EVENT = "event";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LAT = "lat";
    private static final String COLUMN_LNG = "lng";
    private static final String COLUMN_START = "start";
    private static final String COLUMN_END = "end";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_TAGS = "tags";

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
        db.execSQL("drop table if exists event");
        db.execSQL("create table event (id integer primary key autoincrement, image blob, name text," +
                "lat real, lng real, start integer, end integer, description text, tags text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public long insertEvent(Event e) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_IMAGE, bitmapToByte(e.getEventPhoto()));
        cv.put(COLUMN_NAME, e.getName());
        cv.put(COLUMN_LAT, e.getLat());
        cv.put(COLUMN_LNG, e.getLng());
        cv.put(COLUMN_START, e.getStart().getTime());
        cv.put(COLUMN_END, e.getEnd().getTime());
        cv.put(COLUMN_DESCRIPTION, e.getDescription());
        cv.put(COLUMN_TAGS, e.getTags());
        return getWritableDatabase().insert(TABLE_EVENT, null, cv);
    }

    public List<Event> getEvents(){
        Cursor wrapped = getReadableDatabase().query(TABLE_EVENT, null, null, null, null, null, COLUMN_START);
        wrapped.moveToFirst();
        ArrayList<Event> events = new ArrayList<>();
        for(int i=0; i<wrapped.getCount(); i++){
            Event event = new Event(wrapped.getLong(0), byteToBitmap(wrapped.getBlob(1)),
                    wrapped.getString(2), wrapped.getDouble(3), wrapped.getDouble(4), new Date(wrapped.getLong(5)),
                    new Date(wrapped.getLong(6)), wrapped.getString(7), wrapped.getString(8));
            events.add(event);
            wrapped.moveToNext();
        }
        wrapped.close();
        return events;
    }

    public List<Event> getLiveEvents(){
        Cursor wrapped = getReadableDatabase().query(TABLE_EVENT, null, COLUMN_END + " > ?",
                new String[]{String.valueOf(new GregorianCalendar().getTimeInMillis())}, null, null, COLUMN_START);
        wrapped.moveToFirst();
        ArrayList<Event> events = new ArrayList<>();
        for(int i=0; i<wrapped.getCount(); i++){
            Event event = new Event(wrapped.getLong(0), byteToBitmap(wrapped.getBlob(1)),
                    wrapped.getString(2), wrapped.getDouble(3), wrapped.getDouble(4), new Date(wrapped.getLong(5)),
                    new Date(wrapped.getLong(6)), wrapped.getString(7), wrapped.getString(8));
            events.add(event);
            wrapped.moveToNext();
        }
        wrapped.close();
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
                    wrapped.getString(2), wrapped.getDouble(3), wrapped.getDouble(4), new Date(wrapped.getLong(5)),
                    new Date(wrapped.getLong(6)), wrapped.getString(7), wrapped.getString(8));
            events.add(event);
            wrapped.moveToNext();
        }
        wrapped.close();
        return events;
    }

    public Event queryEvent(long id){
        Cursor wrapped = getReadableDatabase().query(TABLE_EVENT, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null, "1");
        if(wrapped.getCount() > 0){
            wrapped.moveToFirst();
            Event event = new Event(wrapped.getLong(0), byteToBitmap(wrapped.getBlob(1)),
                    wrapped.getString(2), wrapped.getDouble(3), wrapped.getDouble(4), new Date(wrapped.getLong(5)),
                    new Date(wrapped.getLong(6)), wrapped.getString(7), wrapped.getString(8));
            wrapped.close();
            return event;
        }
        wrapped.close();
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
