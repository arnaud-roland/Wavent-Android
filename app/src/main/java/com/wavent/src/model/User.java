package com.wavent.src.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arnau on 31/10/2016.
 */
public class User extends BaseObservable implements Parcelable{

    private String id;

    @SerializedName("mail")
    private String mail;

    @SerializedName("password")
    private String password;

    @SerializedName("prenom")
    private String prenom;

    @SerializedName("nom")
    private String nom;

    @SerializedName("profilePicture")
    private String profilePicture;

    @SerializedName("age")
    private int age;

    public User(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public User(String id, String mail, String prenom, String nom, String profilePicture, int age) {
        this.id = id;
        this.mail = mail;
        this.prenom = prenom;
        this.nom = nom;
        this.profilePicture = profilePicture;
        this.age = age;
    }
    public User(User user){
        this.id = user.getId();
        this.mail =  user.getMail();
        this.prenom = user.getPrenom();
        this.nom = user.getNom();
        this.profilePicture = user.getProfilePicture();
        this.age = user.getAge();
    }

    protected User(Parcel in){
        id = in.readString();
        prenom = in.readString();
        nom = in.readString();
        mail = in.readString();
        profilePicture = in.readString();
        age = in.readInt();

    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bindable
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Bindable
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(prenom);
        dest.writeString(nom);
        dest.writeString(mail);
        dest.writeString(profilePicture);
        dest.writeInt(age);
    }
}
