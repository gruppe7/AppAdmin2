package com.example.andreasbergman.appadmin2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

class RestAPIEvent{
    HTTPHandler httpHandler = new HTTPHandler();
    String urlEvents = "http://192.168.1.7:8443/events";

    public void register(JSONObject obj){
        JSONObject response = httpHandler.httpPOST(urlEvents,obj);
    }

    public void attendence(JSONObject obj){
        JSONObject response = httpHandler.httpPOST(urlEvents,obj);

    }

    public void unregister(JSONObject obj) {
        JSONObject response = httpHandler.httpPOST(urlEvents,obj);

    }
}
public class EventActivity extends AppCompatActivity {

    Event event;
    HTTPToken mToken;
    RestAPIEvent restAPIEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        restAPIEvent = new RestAPIEvent();

        TextView name = (TextView)findViewById(R.id.eventNameTV);
        TextView back = (TextView)findViewById(R.id.backLinkTV);//temporarily for testing
        TextView date = (TextView)findViewById(R.id.dateTV);
        TextView desc = (TextView)findViewById(R.id.descriptionTV);
        TextView part = (TextView)findViewById(R.id.participantsTV);

        Intent intent = getIntent();

        if(intent.hasExtra("event")) event = (Event) intent.getExtras().getSerializable("event");
        else event = new Event();

        if(intent.hasExtra("token"))

        name.setText(event.getName());
        date.setText(event.getDateObj().toString());
        part.setText("Participants: " + event.getParticipants());
        desc.setText(event.getDescription());

        back.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    public void OnClickRegister(View view){
        JSONObject registerObj = new JSONObject();
        restAPIEvent.register(registerObj);
    }

    public void OnClickAttendence(View view){
        JSONObject attendeceObj = new JSONObject();
        restAPIEvent.attendence(attendeceObj);
    }

    public void OnClickUnregister(View view){
        JSONObject unregisterObj = new JSONObject();
        restAPIEvent.unregister(unregisterObj);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add(getString(R.string.logout));
        menu.add(getString(R.string.close));
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getTitle()== getString(R.string.logout)){
            Intent intent1 = new Intent(EventActivity.this,LoginActivity.class);
            startActivity(intent1);
            finish();
        }else if(item.getTitle() == getString(R.string.close)) finishAffinity();
        return true;
    }
}
