package com.wavent.src.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.wavent.R;
import com.wavent.src.adapter.ListMessageAdapter;
import com.wavent.src.adapter.ListUserAdapter;
import com.wavent.src.manager.ApiManager;
import com.wavent.src.model.Event;
import com.wavent.src.model.Message;
import com.wavent.src.model.Session;
import com.wavent.src.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by arnau on 07/12/2016.
 */
public class EventMessageFragment extends Fragment {

    private ArrayList<Message> listMessage;
    private ListView listView;
    private ListMessageAdapter adapter;
    private Button sendMessageButton;
    private EditText textBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_message, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Event detailEvent = getArguments().getParcelable("event");

        listView = (ListView) getActivity().findViewById(R.id.listMessage);
        sendMessageButton = (Button) getActivity().findViewById(R.id.sendMessageButton);
        textBox = (EditText) getActivity().findViewById(R.id.textBox);

        listMessage = new ArrayList<Message>();
        if(detailEvent.getMessages() != null){
            listMessage.addAll(detailEvent.getMessages());
        }
        adapter = new ListMessageAdapter(getActivity(),listMessage);
        listView.setAdapter(adapter);

        //move up activity when keyboard is display
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Envoyer un message
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textBox != null && !textBox.getText().toString().equals("")){
                    Message newMessage = new Message();
                    newMessage.setMessage(textBox.getText().toString());
                    newMessage.setDate(new Date());
                    newMessage.setIdUser(Session.getInstance(null).getUserConnected().getId());
                    newMessage.setId(detailEvent.getId());

                    GsonBuilder builder = new GsonBuilder();
                    builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                        @Override
                        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                            return new JsonPrimitive(src.getTime());
                        }
                    });
                    Gson gson = builder.create();
                    String jsonString =  gson.toJson(newMessage,Message.class);
                    JSONObject params = null;
                    try {
                        params = new JSONObject(jsonString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ApiManager.getInstance().sendMessage(v.getContext(), detailEvent.getId(),params);
                    textBox.setText("");
//                    View view = v.getCurrentFocus();
//                    if (view != null) {
//                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                    }
                }
            }
        });
    }
}
