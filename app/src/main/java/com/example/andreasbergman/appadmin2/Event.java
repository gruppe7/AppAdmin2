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
import java.util.ArrayList;
import java.util.Date;

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

    @Override
    public String toString(){
        return name + " Description: " + description + " Number of participants: " + participants + " Date " + date;
    }

    public ArrayList<Event> listUtEventer (){

        String urlEvents = "http://10.22.160.172:8443/events";
        new JSONTask().execute(urlEvents);
        int i = 0;
        return events;
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            String finalJSON = "";
            try{
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    buffer.append(line);

                }
                String finalJSONString = buffer.toString();
                finalJSON = finalJSONString;
                JSONArray pArray = new JSONArray(finalJSONString);


                return finalJSONString;

            }catch (MalformedURLException e){

            }catch (IOException e){
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();
                }

                try {
                    if (bufferedReader != null){
                        bufferedReader.close();
                    }
                } catch (IOException e) {

                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray pArray = new JSONArray(s);

                int x = 0;
                for(int i = 0; i < pArray.length(); i++){
                    JSONObject e = null;

                    e = pArray.getJSONObject(i);
                    events.add(new Event(e.getString("name"),e.getString("description"), e.getInt("participants"), e.getString("date"), e.getInt("dinnerParticipants")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
