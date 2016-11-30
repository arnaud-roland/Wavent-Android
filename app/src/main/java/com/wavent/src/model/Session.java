package com.wavent.src.model;

/**
 * Created by arnau on 29/11/2016.
 */
public class Session {

    private static Session instance;
    private User userConnected;

    public static Session getInstance(User user) {
        if (instance == null) {
            instance = new Session(user);
        }
        return instance;
    }

    public Session (User user){
        this.userConnected = new User(user);
    }

    public User getUserConnected(){
        return userConnected;
    }

}
