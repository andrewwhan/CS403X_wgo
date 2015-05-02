package edu.wpi.wgo;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Andrew on 4/21/2015.
 */
public class Event implements Parcelable{
    private long id;
    private Bitmap eventPhoto;
    private String name;
    private double lat;
    private double lng;
    private Date start;
    private Date end;
    private String description;

    SimpleDateFormat time_format = new SimpleDateFormat("hh:mm a", Locale.US);
    SimpleDateFormat date_format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    public Event(Bitmap eventPhoto, String name, double lat, double lng, Date start, Date end, String description) {
        this.eventPhoto = eventPhoto;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public Event(long id, Bitmap eventPhoto, String name, double lat, double lng, Date start, Date end, String description){
        this.id = id;
        this.eventPhoto = eventPhoto;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public Event(Parcel in){
        this.eventPhoto = in.readParcelable(Bitmap.class.getClassLoader());
        this.name = in.readString();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
        this.start = (Date)in.readSerializable();
        this.end = (Date)in.readSerializable();
        this.description = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bitmap getEventPhoto() {
        return eventPhoto;
    }

    public void setEventPhoto(Bitmap eventPhoto) {
        this.eventPhoto = eventPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        String string = name + " - " + description + "\n" + date_format.format(start.getTime()) + " " +
                time_format.format(start.getTime()) + " - ";
        if(!date_format.format(start.getTime()).equals(date_format.format(end.getTime()))){
            string += date_format.format(end.getTime()) + " ";
        }
        string += time_format.format(end.getTime());
        return string;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(eventPhoto, 0);
        dest.writeString(name);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeSerializable(start);
        dest.writeSerializable(end);
        dest.writeString(description);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
