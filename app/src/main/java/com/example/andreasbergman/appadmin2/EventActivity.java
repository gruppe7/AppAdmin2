package com.example.andreasbergman.appadmin2;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

class RestAPIEvent{
    HTTPHandler httpHandler = new HTTPHandler();
    String urlEvents = "http://10.0.0.95:8443/events";
    String urlLoggedIn = "http://10.0.0.95:8443/event/1/joinEventLoggedIn";


    public JSONObject register(JSONObject obj){
        JSONObject response = httpHandler.httpPOST(urlLoggedIn,obj);
        if(response != null){
            //Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
        }

        return response;
    }

    public JSONObject attendence(JSONObject obj){
        JSONObject response = httpHandler.httpPOST(urlLoggedIn,obj);
        return response;
    }

    public JSONObject unregister(JSONObject obj) {
        JSONObject response = httpHandler.httpPOST(urlLoggedIn,obj);
        return response;
    }
}
public class EventActivity extends AppCompatActivity {
    private NfcAdapter nfcAdapter;
    TextView textViewInfo;


    Event event;
    HTTPToken mToken;
    Long mNumber;
    RestAPIEvent restAPIEvent;
    EditText txtUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //textViewInfo = (TextView)findViewById(R.id.nfcInfo);

        restAPIEvent = new RestAPIEvent();

        TextView name = (TextView)findViewById(R.id.eventNameTV);
        TextView back = (TextView)findViewById(R.id.backLinkTV);//temporarily for testing
        TextView date = (TextView)findViewById(R.id.dateTV);
        TextView desc = (TextView)findViewById(R.id.descriptionTV);
        TextView part = (TextView)findViewById(R.id.participantsTV);
        txtUsername = (EditText)findViewById(R.id.txtUsername);

        Intent intent = getIntent();

        if(intent.hasExtra("event")) event = (Event) intent.getExtras().getSerializable("event");
        else event = new Event();
        if(intent.hasExtra("token"))mToken = (HTTPToken) intent.getExtras().getSerializable("token");
        else mToken = null;
        if(intent.hasExtra("number")) mNumber = (Long) intent.getExtras().getSerializable("number");
        else mNumber = null;


        name.setText(event.getName());
     //   date.setText(event.getDateObj().toString());
        part.setText("Participants: " + event.getParticipants());
        desc.setText(event.getDescription());

        if(nfcAdapter == null){
            Toast.makeText(this, "NFC NOT supported on this devices!", Toast.LENGTH_LONG).show();
            //finish();
        }else if(!nfcAdapter.isEnabled()){Toast.makeText(this, "NFC NOT Enabled!", Toast.LENGTH_LONG).show();
            //finish();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void OnClickRegister(View view) throws JSONException {
        Intent intent = new Intent(EventActivity.this, NfcRead.class);

        JSONObject registerObj = new JSONObject();
        JSONObject response = new JSONObject();

            registerObj.put("username","gunnadal");
          //  registerObj.put("eventId", event.getEventId());
            registerObj.put("token",mToken.getToken());
            response = restAPIEvent.register(registerObj);

        Intent intentNfc = getIntent();

        if( mToken != null){
            //registerObj.put("username",username);
            registerObj.put("token",mToken.getToken());
            response = restAPIEvent.register(registerObj);
        }

        int RP = 0;
        RP = response.getInt("responseCode");

        if(RP == 200 || RP == 201){
            Toast.makeText(this, "Success!", Toast.LENGTH_LONG).show();
        }

    }

    public void OnClickAttendence(View view) throws JSONException {
        Intent intent = new Intent(EventActivity.this, NfcRead.class);
        JSONObject attendeceObj = new JSONObject();
        JSONObject response = new JSONObject();

        if( mNumber != null && mToken != null){
            attendeceObj.put("eventId", event.getEventId());
            attendeceObj.put("token",mToken.getToken());
            response = restAPIEvent.attendence(attendeceObj);
        }

        //HER MÅ VI FÅ TIL SLIK AT EN TOAST KOMMER OPP OM DET ER RIKTIG ELLER FEIL
    }

    public void OnClickUnregister(View view) throws JSONException {
        Intent intent = new Intent(EventActivity.this, NfcRead.class);
        JSONObject unregisterObj = new JSONObject();
        JSONObject response = new JSONObject();
        if( mNumber != null && mToken != null) {
            unregisterObj.put("cardId", mNumber);
            unregisterObj.put("eventId", event.getEventId());
            unregisterObj.put("token", mToken.getToken());
            response = restAPIEvent.unregister(unregisterObj);
        }
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
