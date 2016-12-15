package com.wavent.src.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;


/**
 * Created by arnau on 31/10/2016.
 */
public class Event extends BaseObservable implements Serializable, Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("subject")
    private String subject;

    @SerializedName("address")
    private String address;

    @SerializedName("date")
    private Date date;

    @SerializedName("pictureEvent")
    private String imageUrl;

    @SerializedName("creator")
    private String creator;

    @SerializedName("participants")
    private List<User> participants = new ArrayList<User>();

    @SerializedName("nbParticipantsMax")
    private int nbParticipantsMax;

    @SerializedName("finished")
    private boolean isFinished;

    @SerializedName("messages")
    private List<Message> messages = new ArrayList<Message>();

    public Event() {
    }

    public Event(String name, String subject, String imageUrl, String creator) {
        this.name = name;
        this.subject = subject;
        this.imageUrl = imageUrl;
        this.creator = creator;
    }

    protected Event(Parcel in){
        id = in.readString();
        name = in.readString();
        subject = in.readString();
        address = in.readString();
        imageUrl = in.readString();
        creator = in.readString();
        participants = in.readArrayList(User.class.getClassLoader());
        messages = in.readArrayList(Message.class.getClassLoader());
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Bindable
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Bindable
    public int getNbParticipantsMax() {
        return nbParticipantsMax;
    }

    public void setNbParticipantsMax(int nbParticipantsMax) {
        this.nbParticipantsMax = nbParticipantsMax;
    }

    @Bindable
    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(subject);
        dest.writeString(address);
        dest.writeString(imageUrl);
        dest.writeString(creator);
        dest.writeList(participants);
        dest.writeList(messages);

    }

    public void addParticipant(User user){
        participants.add(user);
    }

    public void addMessage(Message message){
        messages.add(message);
    }
}
