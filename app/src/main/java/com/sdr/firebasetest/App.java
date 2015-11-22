package com.sdr.firebasetest;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by user on 11/15/15.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
