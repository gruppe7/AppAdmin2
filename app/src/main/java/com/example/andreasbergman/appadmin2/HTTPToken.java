package com.example.andreasbergman.appadmin2;

import android.app.Application;

/**
 * Created by andreasbergman on 19/11/16.
 */

public class HTTPToken extends Application {
    private String token;
    public String getToken() {
        return token;
    }
    public void setToken(String str) {
        token = str;
    }
}
