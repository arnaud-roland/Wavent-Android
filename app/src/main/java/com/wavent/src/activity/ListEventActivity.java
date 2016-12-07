package com.wavent.src.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.wavent.R;
import com.wavent.databinding.ActivityListBinding;
import com.wavent.databinding.NavHeaderMainBinding;
import com.wavent.src.adapter.RecyclerAdapter;
import com.wavent.src.listener.OnClickReceiverListener;
import com.wavent.src.manager.ApiManager;
import com.wavent.src.model.Event;
import com.wavent.src.model.Session;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListEventActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private List<Event> currentEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityListBinding binding =  DataBindingUtil.setContentView(this, R.layout.activity_list);

        NavHeaderMainBinding _bind = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header_main, binding.navView, false);
        binding.navView.addHeaderView(_bind.getRoot());
        _bind.setUser(Session.getInstance(null).getUserConnected());

        //Set up du recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter(this.recyclerView, currentEvents);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.setOnClickReceiverListener(new OnClickReceiverListener() {
            @Override
            public void onItemClicked(Event event) {
                Intent intent = new Intent(ListEventActivity.this, EventActivity.class);
                intent.putExtra("myEvent", (Parcelable) event);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListEventActivity.this, CreateEventActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Récupèration des évenements de l'utilisateur
        ApiManager.getInstance().getEvent(ListEventActivity.this, new ApiManager.OnEventsReceived() {
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
                currentEvents.addAll(events);
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_grid) {
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        } else if (id == R.id.action_linear){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
