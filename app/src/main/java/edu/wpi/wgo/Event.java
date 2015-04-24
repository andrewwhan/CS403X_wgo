package edu.wpi.wgo;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by Andrew on 4/21/2015.
 */
public class Event {
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
}
