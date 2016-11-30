package com.wavent.src.model;

/**
 * Created by arnau on 31/10/2016.
 */
public class User {

    private String id;
    private String mail;
    private String password;
    private String prenom;
    private String nom;
    private String profilePicture;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

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
}
