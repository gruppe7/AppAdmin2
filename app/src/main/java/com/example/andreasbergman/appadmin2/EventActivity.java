package com.example.andreasbergman.appadmin2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class EventActivity extends AppCompatActivity {

    Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        TextView name = (TextView)findViewById(R.id.eventNameTV);
        TextView back = (TextView)findViewById(R.id.backLinkTV);//temporarily for testing
        TextView date = (TextView)findViewById(R.id.dateTV);
        TextView desc = (TextView)findViewById(R.id.descriptionTV);

        Intent intent = getIntent();

        if(intent.hasExtra("event")) {
            event = (Event) intent.getExtras().getSerializable("event");
        }
        else {
            event = new Event();
        }
        name.setText(event.getName());
        date.setText(event.getDate());
        desc.setText(event.getDescription());

        back.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
