package com.example.andreasbergman.appadmin2;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by andreasbergman on 18/11/16.
 */

public class HTTPHandler extends AsyncTask<Void, String, JSONObject>{

    //Connections and streams to RestAPI
    private HttpURLConnection conn;
    private DataOutputStream wr;

    //Objects and variables
    private StringBuilder result;
    private URL urlObj;
    private String url;
    private JSONArray returnArray = null;
    private JSONObject inputObj, jObj;
    private String request;
    private String token;
    private HTTPToken mToken;

    public HTTPHandler(){

    }


    public HTTPHandler(String rq, String url, JSONObject jsonObj){
        this.request = rq;
        this.inputObj = jsonObj;
        this.url = url;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        if(request.equals("POST")){
            try{
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);

                conn.connect();

                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(inputObj.toString());
                wr.flush();
                wr.close();

                int res = conn.getResponseCode();

                InputStream in = new BufferedInputStream(conn.getInputStream());
                //Receive the response from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                try {
                    jObj = new JSONObject(result.toString());
                    jObj.put("responseCode", res);
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                }
                return jObj;
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(request.equals("GET")){
            try {
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.connect();

                InputStream stream = conn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);

                }
                String finalJSONString = buffer.toString();
                JSONArray array = new JSONArray(buffer.toString());


                JSONObject temp = new JSONObject();
                temp.put("Events",array);

                jObj = temp;

                return jObj;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else if (request.equals("PUT")){
            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);
                conn.setRequestMethod("httpPUT");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);

                conn.connect();

                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(inputObj.toString());
                wr.flush();
                wr.close();

                InputStream in = new BufferedInputStream(conn.getInputStream());
                //Receive the response from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                try {
                    jObj = new JSONObject(result.toString());
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();

            return jObj;

        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {

    }

    /**
     * POST-request
     *
     * @param url  the url for http request
     * @param jsonObject  the JSON object necessary for POST request. HTTP headers etc.
     * @return return the JSON object from RestAPI
     */
    public JSONObject httpPOST(String url, JSONObject jsonObject) {
        // request method is POST
        try {
            urlObj = new URL(url);

            conn = (HttpURLConnection) urlObj.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            conn.connect();

            wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            wr.flush();
            wr.close();

            int res = conn.getResponseCode();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            //Receive the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            try {
                jObj = new JSONObject(result.toString());
                jObj.put("responseCode", res);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.disconnect();

        return jObj;
    }

    /**
     * GET-request
     *
     * @param url  the url for http request
     * @return return the JSON object from RestAPI with the information from the url
     */
    public JSONArray httpGET(String url) {

        try {
            urlObj = new URL(url);
            conn = (HttpURLConnection) urlObj.openConnection();
            conn.connect();

            InputStream stream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
            String JSONString = buffer.toString();

            returnArray = new JSONArray(JSONString);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return returnArray;
    }

    /**
     * PUT-request
     *
     * @param url  the url for http request
     * @return return the JSON object from RestAPI with the information from the url
     */
    public JSONObject httpPUT(String url, JSONObject jsonObject) {
        // request method is POST
        try {
            urlObj = new URL(url);

            conn = (HttpURLConnection) urlObj.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            conn.connect();

            wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            wr.flush();
            wr.close();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            //Receive the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            try {
                jObj = new JSONObject(result.toString());
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.disconnect();

        return jObj;
    }


    public String getToken(){
        return this.token;
    }

    public void setRequest(String request){
        this.request = request;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setToken(String token){
        this.token = token;
    }
}
