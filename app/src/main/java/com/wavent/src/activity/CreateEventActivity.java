package com.wavent.src.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wavent.R;
import com.wavent.src.manager.ApiManager;
import com.wavent.src.model.Event;
import com.wavent.src.model.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class CreateEventActivity extends AppCompatActivity {

    private ImageView imageViewEvent;
    private EditText nameTV;
    private EditText subjectTV;
    private EditText addressTV;
    private EditText nbMaxPartTV;
    private EditText dateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        nameTV = (EditText) findViewById(R.id.editText);
        subjectTV = (EditText) findViewById(R.id.editText2);
        addressTV = (EditText) findViewById(R.id.editTextAdress);
        nbMaxPartTV = (EditText) findViewById(R.id.editTextNbPartMax);
        dateTV = (EditText) findViewById(R.id.editTextDate);

    }

    public void createEvent(View view){

        String name = nameTV.getText().toString();
        String subject = subjectTV.getText().toString();
        String address = addressTV.getText().toString();
        int nbParticipantMax = Integer.parseInt(nbMaxPartTV.getText().toString());
        Date date = new Date();

        if(name.equals("") || name == null || subject.equals("") || subject == null){
            //TODO popup error
        }else{
            Event newEvent = new Event(name,subject,"picture.jpeg", Session.getInstance(null).getUserConnected().getId());
            newEvent.setAddress(address);
            //newEvent.setDate(date);
            newEvent.setNbParticipantsMax(nbParticipantMax);
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            gson.toJson(newEvent,Event.class);
            String jsonString = gson.toJson(newEvent,Event.class);
            JSONObject params = null;
            try {
                 params = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(params != null){
                ApiManager.getInstance().createEvent(CreateEventActivity.this,params);
                Intent intent = new Intent(CreateEventActivity.this,ListEventActivity.class);
                startActivity(intent);
            }

        }
    }
}
