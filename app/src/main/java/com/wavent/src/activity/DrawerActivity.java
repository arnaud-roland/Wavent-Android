package com.wavent.src.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wavent.R;
import com.wavent.databinding.ActivityDrawerBinding;
import com.wavent.databinding.NavHeaderMainBinding;
import com.wavent.src.model.Session;



public class DrawerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDrawerBinding binding =  DataBindingUtil.setContentView(this, R.layout.activity_drawer);
        NavHeaderMainBinding _bind = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header_main, binding.navView, false);
        binding.navView.addHeaderView(_bind.getRoot());
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                displayFragment(item);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        _bind.setUser(Session.getInstance(null).getUserConnected());

        Class fragmentClass = ListAllEventFragment.class;
        launchFragment(fragmentClass,null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void displayFragment(MenuItem item){
        int id = item.getItemId();
        Class fragmentClass = null;
        Boolean param = null;
        if (id == R.id.nav_list_events) {
            fragmentClass = ListAllEventFragment.class;
        } else if (id == R.id.nav_create_event) {
            fragmentClass = CreateEventFragment.class;
        } else if (id == R.id.nav_past_events) {
            fragmentClass = ListMyEventFragment.class;
            param = true;
        } else if (id == R.id.nav_my_events) {
           fragmentClass = ListMyEventFragment.class;
            param = false;
        } else if (id == R.id.nav_profile) {
            fragmentClass = UserFragment.class;
        } else if (id == R.id.nav_send) {
            //agmentClass = FragmentTwo.class;
        }
        launchFragment(fragmentClass, param);
    }

    public void launchFragment(Class fragmentClass, Boolean param){
        Fragment fragment = null;
        try {
            if(param != null){
                fragment = ListMyEventFragment.newInstance(param);
            } else {
                fragment = (Fragment) fragmentClass.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

    }
}
