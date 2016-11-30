package com.wavent.src.model;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;


/**
 * Created by arnau on 31/10/2016.
 */
public class Event implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("subject")
    private String subject;

    @SerializedName("pictureEvent")
    private String imageUrl;

    @SerializedName("creator")
    private String creator;

    public Event() {
    }

    public Event(String name, String subject, String imageUrl, String creator) {
        this.name = name;
        this.subject = subject;
        this.imageUrl = imageUrl;
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
