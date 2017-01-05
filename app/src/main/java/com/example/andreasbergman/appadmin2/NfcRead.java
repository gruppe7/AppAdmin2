package com.example.andreasbergman.appadmin2;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by elisabethstorset on 20.12.2016.
 */

public class NfcRead extends Activity{
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
               // tagInfo += "\n";

              //  String[] techList = tag.getTechList();
                //tagInfo += "\nTech List\n";
                //tagInfo += "length = " + techList.length +"\n";
                //for(int i= 0; i<techList.length;  i++){
                  //  tagInfo += techList[i] + "\n ";
                //}

               // textViewInfo.setText(tagInfo);
              //  String a = textViewInfo.getText().toString();




                Long i = Long.parseLong(tagInfo,16);

                textViewInfo.setText(i +"");
                String b = textViewInfo.getText().toString();
               Toast.makeText(this, b, Toast.LENGTH_LONG).show();


            }
        }else{
            Toast.makeText(this, "onResume() : " + action, Toast.LENGTH_SHORT).show();
        }

        intent = new Intent(NfcRead.this, EventActivity.class);
    }


}
