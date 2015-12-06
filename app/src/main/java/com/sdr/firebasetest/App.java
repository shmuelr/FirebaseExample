package com.sdr.firebasetest;

import android.app.Application;

import com.firebase.client.Firebase;
import com.sdr.firebasetest.models.User;

/**
 * Created by user on 11/15/15.
 */
public class App extends Application {

    public static User user;



    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        user = new User();
        user.setId(generateUserId());
    }

    private int generateUserId() {
        return (int) (Math.random() * 10000);
    }

}
