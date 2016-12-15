package com.wavent.src.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by arnau on 15/12/2016.
 */
public class Message implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("idUser")
    private String idUser;

    @SerializedName("idEvent")
    private String idEvent;

    @SerializedName("date")
    private Date date;

    @SerializedName("message")
    private String message;

    public Message() {
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(idUser);
        dest.writeString(idEvent);
        dest.writeSerializable(date);
        dest.writeString(message);

    }

    protected Message(Parcel in){
        id = in.readString();
        idUser = in.readString();
        idEvent = in.readString();
        date = (Date) in.readSerializable();
        message = in.readString();
    }
}
