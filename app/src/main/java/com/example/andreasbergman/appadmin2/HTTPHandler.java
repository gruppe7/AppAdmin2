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
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result;
    URL urlObj;
    String url;
    JSONObject jObj = null;
    JSONObject inputObj;
    String request;

    public AsyncResponse delegate = null;

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

                //JSONObject jsonObject = new JSONObject();
                //jsonObject.put("username", "gunnadal");
                //jsonObject.put("password", "abc123");

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
                JSONObject jObj = new JSONObject(finalJSONString);

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
                conn.setRequestMethod("PUT");
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
        try {
            delegate.processFinish(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject postRequest(String url, JSONObject jsonObject) {
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

            //JSONObject jsonObject = new JSONObject();
            //jsonObject.put("username", "gunnadal");
            //jsonObject.put("password", "abc123");

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

    public JSONObject GET(String url) {

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
            JSONObject jObj = new JSONObject(finalJSONString);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jObj;
    }

    public JSONObject PUT(String url, JSONObject jsonObject) {
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

            //JSONObject jsonObject = new JSONObject();
            //jsonObject.put("username", "gunnadal");
            //jsonObject.put("password", "abc123");

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
}
