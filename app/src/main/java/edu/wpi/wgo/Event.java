package edu.wpi.wgo;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Andrew on 4/21/2015.
 */
public class Event implements Parcelable{
    private Bitmap eventPhoto;
    private String name;
    private String location;
    private Date start;
    private Date end;
    private String description;

    public Event(Bitmap eventPhoto, String name, String location, Date start, Date end, String description) {
        this.eventPhoto = eventPhoto;
        this.name = name;
        this.location = location;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public Event(Parcel in){
        this.eventPhoto = in.readParcelable(Bitmap.class.getClassLoader());
        this.name = in.readString();
        this.location = in.readString();
        this.start = (Date)in.readSerializable();
        this.end = (Date)in.readSerializable();
        this.description = in.readString();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        return name + " - " + description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(eventPhoto, 0);
        dest.writeString(name);
        dest.writeString(location);
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
