package com.wavent.src.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wavent.R;
import com.wavent.src.adapter.ListMessageAdapter;
import com.wavent.src.adapter.ListUserAdapter;
import com.wavent.src.model.Event;
import com.wavent.src.model.Message;
import com.wavent.src.model.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by arnau on 07/12/2016.
 */
public class EventMessageFragment extends Fragment {

    private ArrayList<Message> listMessage;
    private ListView listView;
    private ListMessageAdapter adapter;

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
        listMessage = new ArrayList<Message>();
        if(detailEvent.getMessages() != null){
            listMessage.addAll(detailEvent.getMessages());
        }
        //FIXME
        Message data = new Message();
        data.setDate(new Date());
        data.setIdUser("583db6212cd774690f6e426c");
        data.setMessage("Moi je ramène du pinot noir, bsx.");
        listMessage.add(data);
        adapter = new ListMessageAdapter(getActivity(),listMessage);
        listView.setAdapter(adapter);
    }
}
