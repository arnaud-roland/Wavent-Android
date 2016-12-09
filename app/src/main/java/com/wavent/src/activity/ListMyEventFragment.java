package com.wavent.src.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.annimon.stream.Stream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.wavent.R;
import com.wavent.src.adapter.RecyclerAdapter;
import com.wavent.src.listener.OnClickReceiverListener;
import com.wavent.src.manager.ApiManager;
import com.wavent.src.model.Event;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by arnau on 08/12/2016.
 */
public class ListMyEventFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private List<Event> currentEvents = new ArrayList<>();

    private static final String ARE_FINISHED = "eventsFinished";
    private boolean areFinished;

    public ListMyEventFragment() {
    }

    public static ListMyEventFragment newInstance(boolean eventFinished) {
        ListMyEventFragment fragment = new ListMyEventFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARE_FINISHED, eventFinished);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            areFinished = getArguments().getBoolean(ARE_FINISHED);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        if(areFinished){
//            getActivity().setTitle("Evenements terminés");
//        } else {
//            getActivity().setTitle("Evenements en cours");
//        }
        //Set up du recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter(this.recyclerView, currentEvents);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.setOnClickReceiverListener(new OnClickReceiverListener() {
            @Override
            public void onItemClicked(Event event) {
                Intent intent = new Intent(getActivity(), EventActivity.class);
                intent.putExtra("myEvent", (Parcelable) event);
                startActivity(intent);
            }
        });

        //Récupèration des évenements de l'utilisateur
        ApiManager.getInstance().getEventByUser(getContext(), new ApiManager.OnEventsReceived() {
            @Override
            public void onSucess(JSONArray newEvents) {
                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    }
                });
                final Gson gson = builder.create();
                List<Event> events = new ArrayList<Event>();
                Type listType = new TypeToken<ArrayList<Event>>(){ }.getType();
                events = gson.fromJson(newEvents.toString(),listType);

                //TODO do filter with Stream or RetroLambda
                List<Event> eventsFinished = new ArrayList<Event>();
                List<Event> eventsEncour = new ArrayList<Event>();

                for(Event event : events){
                    if(event.isFinished()){
                        eventsFinished.add(event);
                    } else {
                        eventsEncour.add(event);
                    }
                }
                if(areFinished){
                    currentEvents.addAll(eventsFinished);
                } else {
                    currentEvents.addAll(eventsEncour);
                }
                recyclerAdapter.notifyDataSetChanged();
                recyclerAdapter.setLoaded();
            }

            @Override
            public void onError(VolleyError error) {
                System.out.println("false");
            }
        });

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_grid) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        } else if (id == R.id.action_linear){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        return super.onOptionsItemSelected(item);
    }


}
