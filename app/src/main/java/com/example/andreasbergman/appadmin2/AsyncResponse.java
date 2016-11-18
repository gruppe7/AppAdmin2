package com.example.andreasbergman.appadmin2;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andreasbergman on 18/11/16.
 */

public interface AsyncResponse {
    void processFinish(JSONObject output) throws JSONException;
}
