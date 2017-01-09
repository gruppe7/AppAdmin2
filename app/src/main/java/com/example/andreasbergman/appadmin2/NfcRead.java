package com.example.andreasbergman.appadmin2;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

/**
 * 
 */

public class NfcRead extends Activity implements Serializable {
    private NfcAdapter nfcAdapter;
    TextView textViewInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        textViewInfo = (TextView)findViewById(R.id.info);

        if(nfcAdapter == null){
            Toast.makeText(this, "NFC NOT supported on this devices!", Toast.LENGTH_LONG).show();
            finish();
        }else if(!nfcAdapter.isEnabled()){Toast.makeText(this, "NFC NOT Enabled!", Toast.LENGTH_LONG).show();
           // finish();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
           // Toast.makeText(this, "onResume() - ACTION_TAG_DISCOVERED", Toast.LENGTH_SHORT).show();

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if(tag == null){
                textViewInfo.setText("tag == null");
            }else{

                String tagInfo = "" ;

                //tagInfo += "\nTag Id: \n";
                byte[] tagId = tag.getId();
              // tagInfo += "length = " + tagId.length +"\n";
                for(int i =tagId.length-1; i >= 0; i--){
                    tagInfo += Integer.toHexString(tagId[i] & 0xFF);

                }

                Long  mNumber = Long.parseLong(tagInfo,16);

                textViewInfo.setText(mNumber +"");
             //  Toast.makeText(this, mNumber +"", Toast.LENGTH_LONG).show();

               intent = new Intent(NfcRead.this, EventActivity.class);
                intent.putExtra( "number", mNumber);
                startActivity(intent);

            }
        }else{
            Toast.makeText(this, "onResume() : " + action, Toast.LENGTH_SHORT).show();
        }



    }


}
