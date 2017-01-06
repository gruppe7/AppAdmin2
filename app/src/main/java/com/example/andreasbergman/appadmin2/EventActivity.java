package com.example.andreasbergman.appadmin2;

import android.content.Intent;
import android.nfc.NfcAdapter;
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
    String urlEvents = "http://10.22.160.227:8443/events";

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
    private NfcAdapter nfcAdapter;
    TextView textViewInfo;


    Event event;
    HTTPToken mToken;
    RestAPIEvent restAPIEvent;
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

        Intent intent = getIntent();

        if(intent.hasExtra("event")) event = (Event) intent.getExtras().getSerializable("event");
        else event = new Event();

        if(intent.hasExtra("token"))

        name.setText(event.getName());
        date.setText(event.getDateObj().toString());
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

    public void OnClickRegister(View view){
        Intent intent = new Intent(EventActivity.this, NfcRead.class);
        //JSONObject registerObj = new JSONObject();
        //restAPIEvent.register(registerObj);
    }

    public void OnClickAttendence(View view){
        Intent intent = new Intent(EventActivity.this, NfcRead.class);

        //JSONObject attendeceObj = new JSONObject();
        //restAPIEvent.attendence(attendeceObj);
    }

    public void OnClickUnregister(View view){
        Intent intent = new Intent(EventActivity.this, NfcRead.class);

        //JSONObject unregisterObj = new JSONObject();
        //restAPIEvent.unregister(unregisterObj);
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
   /* @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            Toast.makeText(this, "onResume() - ACTION_TAG_DISCOVERED", Toast.LENGTH_SHORT).show();

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if(tag == null){
                textViewInfo.setText("tag == null");
            }else{
                String tagInfo = tag.toString() + "\n";

                tagInfo += "\nTag Id: \n";
                byte[] tagId = tag.getId();
                tagInfo += "length = " + tagId.length +"\n";
                for(int i =tagId.length-1; i >= 0; i--){
                    tagInfo += Integer.toHexString(tagId[i] & 0xFF) + " ";

                    // tagInfo += Integer.toHexString(tagId[i] & 0xFF) + " ";

                }
                tagInfo += "\n";

                String[] techList = tag.getTechList();
                tagInfo += "\nTech List\n";
                tagInfo += "length = " + techList.length +"\n";
                for(int i= 0; i<techList.length;  i++){
                    tagInfo += techList[i] + "\n ";
                }

                textViewInfo.setText(tagInfo);
            }
        }else{
            Toast.makeText(this, "onResume() : " + action, Toast.LENGTH_SHORT).show();
        }


    }*/
}
