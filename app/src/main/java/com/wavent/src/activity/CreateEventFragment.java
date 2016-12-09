package com.wavent.src.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.wavent.R;
import com.wavent.src.manager.ApiManager;
import com.wavent.src.model.Event;
import com.wavent.src.model.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateEventFragment extends Fragment {

    private ImageView imageViewEvent;
    private EditText nameTV;
    private EditText subjectTV;
    private EditText addressTV;
    private EditText nbMaxPartTV;
    private EditText dateTV;
    private Button button;
    private Calendar myCalendar;

    public CreateEventFragment() {
    }

    public static CreateEventFragment newInstance() {
        CreateEventFragment fragment = new CreateEventFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_event, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        nameTV = (EditText) getActivity().findViewById(R.id.editText);
        subjectTV = (EditText) getActivity().findViewById(R.id.editText2);
        addressTV = (EditText) getActivity().findViewById(R.id.editTextAdress);
        nbMaxPartTV = (EditText) getActivity().findViewById(R.id.editTextNbPartMax);
        dateTV = (EditText) getActivity().findViewById(R.id.editTextDate);
        button = (Button) getActivity().findViewById(R.id.button2);
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateTV.setText(sdf.format(myCalendar.getTime()));

            }
        };

        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent(v);
            }
        });
    }


    public void createEvent(View view){

        String name = nameTV.getText().toString();
        String subject = subjectTV.getText().toString();
        String address = addressTV.getText().toString();
        int nbParticipantMax = Integer.parseInt(nbMaxPartTV.getText().toString());
        Date date = new Date(dateTV.getText().toString());

        if(name.equals("") || name == null || subject.equals("") || subject == null || address.equals("") || nbParticipantMax == 0){
            //TODO popup error
            System.out.println("missing params");
        }else{
            Event newEvent = new Event(name,subject,"picture.jpeg", Session.getInstance(null).getUserConnected().getId());
            newEvent.setAddress(address);
            newEvent.setDate(date);
            newEvent.setNbParticipantsMax(nbParticipantMax);
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                @Override
                public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.getTime());
                }
            });
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
                ApiManager.getInstance().createEvent(getActivity(),params);
                Intent intent = new Intent(getActivity(),DrawerActivity.class);
                startActivity(intent);
            }

        }
    }
}
