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

public class Event implements Serializable, Comparable<Event> {

    private String name;
    private String description;
    private int participants;
    private int dinnerParticipants;
    private int eventId;
    private String date;
    private Date parsed;

    private ArrayList<Event> events = new ArrayList<Event>();

    private ArrayList<Participants> attendingParticipants;

    public Event (){
        attendingParticipants = new ArrayList<Participants>();
    }

    public Event(int eventId,String name, String description, int participants, String date, int dinnerParticipants) throws ParseException {
        this.eventId = eventId;
        this.name = name;
        this.description = description;
        this.participants = participants;
        String temp = date;
        this.dinnerParticipants = dinnerParticipants;

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");;
        parsed = sdf.parse(temp);
        String outputPattern = "dd-MM-yyyy";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        this.date = outputFormat.format(parsed);
    }

    public String getName() {
        return name;
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

    public Date getDateObj(){
        return parsed;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // + " Description: " + description + " Number of participants: " + participants + " Date " + date

    @Override
    public String toString(){
        return date + " " + name ;
    }

    @Override
    public int compareTo(Event o) {
        return getDateObj().compareTo(o.getDateObj());
    }
}
