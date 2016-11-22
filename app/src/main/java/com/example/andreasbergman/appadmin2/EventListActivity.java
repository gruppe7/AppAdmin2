package com.example.andreasbergman.appadmin2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class EventListActivity extends AppCompatActivity implements AsyncResponse{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private ListView eventlist;
    private Event[] events;//test list
    NavigationView navigationView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private HTTPHandler HttpHandler;
    private JSONObject allEvents;
    ArrayList<Event> arrayOfEvents;

    @Override
    public void processFinish(JSONObject output) throws JSONException {
        arrayOfEvents = new ArrayList<Event>();


        try {
            JSONArray pArray = new JSONArray(allEvents);

            int x = 0;
            for(int i = 0; i < pArray.length(); i++){
                JSONObject e = null;
                e = pArray.getJSONObject(i);
                arrayOfEvents.add(new Event(e.getString("name"),e.getString("description"), e.getInt("participants"), e.getString("date"), e.getInt("dinnerParticipants")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //Elisabeth tester
    public class EventAdapter extends ArrayAdapter<Event>{
        public EventAdapter(Context context, ArrayList<Event> events){
            super(context, 0, events);
        }
        @Override
        public View getView(int position, View conertView, ViewGroup parent){
            Event event = getItem(position);
            if(conertView == null){
                conertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_event_list, parent, false);
            }
            //ListView e = (ListView)conertView.findViewById(R.id.listview1);
            //e.setTextAlignment(event.name);
            TextView test = (TextView)conertView.findViewById(R.id.textView);
            test.setText(event.getName());
            return conertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        String urlEvents = "http://192.168.1.9:8443/events";
        HttpHandler = new HTTPHandler("GET", urlEvents,null);
       // ArrayList<Event> arrayOfEvents = new ArrayList<Event>();


        try {
            allEvents = HttpHandler.execute().get();
            //JSONArray pArray = new JSONArray(allEvents);

            //for(int i = 0; i < pArray.length(); i++){
            //    JSONObject e = null;
            //    e = pArray.getJSONObject(i);
            //    arrayOfEvents.add(new Event(e.getString("name"),e.getString("description"), e.getInt("participants"), e.getString("date"), e.getInt("dinnerParticipants")));
            //}
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //making the list
        Resources resources = getResources();

       // events = resources.getStringArray(R.array.event); //test list
       // eventlist = (ListView)findViewById(listview1);  // Get ListView object from xml

       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, events); // Define a new Adapter
        //eventlist.setAdapter(adapter);     // Assign adapter to ListView
        //eventlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //EventAdapter adapter1 = new EventAdapter(this, arrayOfEvents);
            //ListView listView =(ListView)findViewById(R.id.listview1);
            //listView.setAdapter(adapter1);

        /*    @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {
                int itemPosition     = position;
                String  itemValue    = (String) eventlist.getItemAtPosition(position);

                //method to send the selected event
                Intent intent = new Intent(EventListActivity.this, EventActivity.class);
                startActivity(intent);
                finish();

                //Toast.makeText(getApplicationContext(), "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG).show(); //test

            }
        });*/

        //@andreasbergman
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch ((item.getItemId())){
                    case R.id.registration_id:
                        Intent intent = new Intent(EventListActivity.this,CardRegistrationActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.logout_id:
                        Intent intent1 = new Intent(EventListActivity.this,LoginActivity.class);
                        startActivity(intent1);
                        finish();
                        break;

                }
                return false;
            }
        });

}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("EventList Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        public AsyncResponse delegate = null;

        public JSONTask(AsyncResponse delegate){
            this.delegate = delegate;
        }

        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            String finalJSON = "";
            try{
                JSONObject jsonObject = HttpHandler.GET(urls[0]);

                return jsonObject.toString();

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
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            int i = 0;
        }
    }
}
