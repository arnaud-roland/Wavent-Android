package com.wavent.src.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wavent.R;
import com.wavent.databinding.ActivityDetailBinding;
import com.wavent.src.model.Event;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        Event detaiEvent = (Event) getIntent().getParcelableExtra("myEvent");
        binding.setEvent(detaiEvent);
    }
}
