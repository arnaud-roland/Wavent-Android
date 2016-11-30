package com.wavent.src.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.wavent.R;
import com.wavent.src.manager.ApiManager;
import com.wavent.src.model.Event;
import com.wavent.src.model.Session;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateEventActivity extends AppCompatActivity {

    private ImageView imageViewEvent;
    private EditText nameTV;
    private EditText subjectTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        nameTV = (EditText) findViewById(R.id.editText);
        subjectTV = (EditText) findViewById(R.id.editText2);

    }

    public void createEvent(View view){

        String name = nameTV.getText().toString();
        String subject = subjectTV.getText().toString();

        if(name.equals("") || name == null || subject.equals("") || subject == null){

        }else{
            Event newEvent = new Event(name,subject,"picture.jpeg", Session.getInstance(null).getUserConnected().getId());
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
