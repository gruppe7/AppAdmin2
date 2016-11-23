package com.example.andreasbergman.appadmin2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by andreasbergman on 23/11/16.
 */

public class EventAdapter extends ArrayAdapter<Event> {
    private Activity activity;
    private ArrayList<Event> lEvent;
    private LayoutInflater inflater = null;

  /*  public EventAdapter (Activity activity, int textViewResourceId, ArrayList<Event> lEvent){
        super(activity,textViewResourceId, lEvent);
        try{
            this.activity = activity;
            this.lEvent = lEvent;
            this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public int getCount(){
        return lEvent.size();
    }

    public static class ViewHolder{
        public TextView display_name;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View vi = convertView;
        final ViewHolder holder;
        try{
            if(convertView == null){
                vi = inflater.inflate(R.layout.activity_event_list,null);
                holder = new ViewHolder();

            }
        }catch (Exception e){

        }
        return vi;
    }*/

    public EventAdapter(Context context, ArrayList<Event> events){
        super(context, 0, events);
    }

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
