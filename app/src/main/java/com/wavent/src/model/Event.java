package com.wavent.src.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;


/**
 * Created by arnau on 31/10/2016.
 */
public class Event extends BaseObservable implements Serializable, Parcelable {

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

    protected Event(Parcel in){
        name = in.readString();
        subject = in.readString();
        imageUrl = in.readString();
        creator = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Bindable
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(subject);
        dest.writeString(imageUrl);
        dest.writeString(creator);
    }
}
