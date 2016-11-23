package com.example.andreasbergman.appadmin2;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by andreasbergman on 20/10/16.
 */

public class Event implements Serializable {

    private String name;
    private String description;
    private int participants;
    private int dinnerParticipants;
    private static int eventId = 0;
    private String date;
    private java.sql.Date sqlDate;

    private ArrayList<Event> events = new ArrayList<Event>();

    private ArrayList<Participants> attendingParticipants;

    public Event (){
        attendingParticipants = new ArrayList<Participants>();
    }

    public Event(String name, String description, int participants, String date, int dinnerParticipants) {
        eventId++;
        this.name = name;
        this.description = description;
        this.participants = participants;
        this.date = date;
        this.dinnerParticipants = dinnerParticipants;
    }

    public String getName() {
        return name;
    }

    public void convertDate(String d) throws ParseException {

        sqlDate = java.sql.Date.valueOf(d);

        //Date date1 = new java.util.Date(sqlDate.getTime());
        // wherever you get this
        //DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        //String newDate = date2.toString();
        //setDate(newDate);
// Print what date is today!

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getDinnerParticipants() {
        return dinnerParticipants;
    }

    public void setDinnerParticipants(int dinnerParticipants) {
        this.dinnerParticipants = dinnerParticipants;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // + " Description: " + description + " Number of participants: " + participants + " Date " + date

    @Override
    public String toString(){
        return date + " " + name ;
    }
}
