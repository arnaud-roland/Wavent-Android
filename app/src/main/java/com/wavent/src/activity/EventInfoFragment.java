package com.wavent.src.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wavent.R;
import com.wavent.databinding.ActivityEventBinding;
import com.wavent.databinding.FragmentEventInfoBinding;
import com.wavent.src.model.Event;

/**
 * Created by arnau on 07/12/2016.
 */
public class EventInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentEventInfoBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_event_info,container,false);
        Event detailEvent = getArguments().getParcelable("event");
        binding.setEvent(detailEvent);
        return binding.getRoot();
    }
}
