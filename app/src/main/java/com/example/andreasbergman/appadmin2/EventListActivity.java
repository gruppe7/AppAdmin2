package com.example.andreasbergman.appadmin2;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.andreasbergman.appadmin2.R.id.listview1;

class RestAPIEventlist {
    HTTPHandler httpHandler = new HTTPHandler();
    String urlEvents = "http://192.168.1.7:8443/events";

    /**
     * getEventList
     * @return return an array of events from DB
     */
    public JSONObject getEventList() throws JSONException {

        JSONArray returnArray = httpHandler.httpGET(urlEvents);

        JSONObject temp = new JSONObject();
        temp.put("events",returnArray);

        return temp;
    }
}
public class EventListActivity extends AppCompatActivity{

    private ArrayAdapter<Event> eventArrayAdapter;
    private ListEventTask mListTask = null;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private ListView listView;
    NavigationView navigationView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private HTTPHandler HttpHandler;
    private JSONObject allEvents;
    ArrayList<Event> arrayOfEvents;
    private HTTPToken mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        HttpHandler = new HTTPHandler();
        arrayOfEvents = new ArrayList<Event>();
        eventArrayAdapter = new ArrayAdapter<Event>(this,android.R.layout.simple_list_item_1,arrayOfEvents);

        Intent intent = getIntent();

        if(intent.hasExtra("token")) {
            mToken = (HTTPToken) intent.getExtras().getSerializable("token");
        }
        else {
            mToken = new HTTPToken();
        }


        mListTask = new EventListActivity.ListEventTask();
        mListTask.execute((Void)null);

        listView = (ListView)findViewById(listview1);  // Get ListView object from xml
        listView.setAdapter(eventArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Launch FriendActivity with selected friend
                Event valgtEvent = (Event) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(EventListActivity.this, EventActivity.class);
                intent.putExtra("event", valgtEvent);
                intent.putExtra("token",mToken);

                startActivityForResult(intent, 10);
            }
        });

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

        //Creating resourcelist
        Resources resources = getResources();
/*
        //setContentView(R.layout.navigation_header);
        TextView tv1 = (TextView)findViewById(R.id.textView);
        tv1.setText(mToken.getUsername());

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch ((item.getItemId())){
                    case R.id.logout_id:
                        Intent intent1 = new Intent(EventListActivity.this,LoginActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                }
                return false;
            }
        });*/

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

    public class ListEventTask extends AsyncTask<Void, Void, JSONObject> {

        String URL;
        RestAPIEventlist restAPIEvent;
        public ListEventTask(){
            restAPIEvent = new RestAPIEventlist();
        }

        @Override
        protected JSONObject doInBackground(Void... urls) {
            JSONObject result;
            try{
                result = restAPIEvent.getEventList();

                return result;

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result == null){
                String t = "";
            }
            try {
                JSONArray eventer = result.getJSONArray("events");

                for (int i = 0; i < eventer.length(); i++) {
                    JSONObject e = null;
                    e = eventer.getJSONObject(i);
                    arrayOfEvents.add(new Event(e.getString("name"), e.getString("description"), e.getInt("participants"), e.getString("date"), e.getInt("dinnerParticipants")));
                }
                Collections.sort(arrayOfEvents);

/*
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    EventAdapter adapter1 = new EventAdapter(this, arrayOfEvents);
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {
                        int itemPosition     = position;
                        String  itemValue    = (String) listView.getItemAtPosition(position);

                        //method to send the selected event
                        Intent intent = new Intent(EventListActivity.this, EventActivity.class);
                        startActivity(intent);
                        finish();

                        //Toast.makeText(getApplicationContext(), "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG).show(); //test

                    }
                });*/
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}