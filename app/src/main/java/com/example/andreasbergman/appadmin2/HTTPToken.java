package com.example.andreasbergman.appadmin2;

import android.app.Application;

import java.io.Serializable;

/**
 * Created by andreasbergman on 19/11/16.
 */

public class HTTPToken extends Application implements Serializable {
    private String token;
    private String username;
    public String getToken() {
        return token;
    }
    public void setToken(String str) {
        token = str;
    }
    public String getUsername(){return username; }
    public void setUsername(String username){this.username = username;}
}
