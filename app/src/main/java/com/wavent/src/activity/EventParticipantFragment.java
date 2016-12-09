package com.wavent.src.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wavent.R;
import com.wavent.src.adapter.ListUserAdapter;
import com.wavent.src.model.Event;
import com.wavent.src.model.User;

import java.util.ArrayList;

/**
 * Created by arnau on 07/12/2016.
 */
public class EventParticipantFragment extends Fragment {

    private ArrayList<User> listUser;
    private ListView listView;
    private ListUserAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_participants, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Event detailEvent = getArguments().getParcelable("event");
        listView = (ListView) getActivity().findViewById(R.id.listUser);
        listUser = new ArrayList<User>();
        if(detailEvent.getParticipants() != null){
            listUser.addAll(detailEvent.getParticipants());
        }
        adapter = new ListUserAdapter(getActivity(),listUser);
        listView.setAdapter(adapter);
    }
}
