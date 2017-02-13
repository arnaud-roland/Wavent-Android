package com.wavent.src.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wavent.R;
import com.wavent.databinding.FragmentEventInfoBinding;
import com.wavent.src.manager.ApiManager;
import com.wavent.src.model.Event;
import com.wavent.src.model.Session;
import com.wavent.src.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arnau on 07/12/2016.
 */
public class EventInfoFragment extends Fragment {

    private Event currentEvent;
    private TextView participe;
    private FloatingActionButton fab;
    private ImageView eventPicture;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentEventInfoBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_event_info,container,false);
        final Event detailEvent = getArguments().getParcelable("event");
        currentEvent = getArguments().getParcelable("event");
        binding.setEvent(detailEvent);

        //Permet de participer à un évenement
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = Session.getInstance(null).getUserConnected();
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                gson.toJson(user,User.class);
                String jsonString = gson.toJson(user,User.class);
                JSONObject params = null;
                try {
                    params = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ApiManager.getInstance().joinEvent(v.getContext(),detailEvent.getId(),params);
                detailEvent.addParticipant(user);

            }
        });

        //Permet de lancer googleMap sur le lieu de l'évenement
        binding.textAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+detailEvent.getAddress());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        participe = (TextView) getActivity().findViewById(R.id.tvParticipe);
        eventPicture = (ImageView) getActivity().findViewById(R.id.imageViewEventInfo);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar4);

        if(currentEvent.getParticipants().contains(Session.getInstance(null).getUserConnected())){
            participe.setVisibility(View.VISIBLE);
            fab.setImageResource(R.drawable.check);
        } else {
            participe.setVisibility(View.GONE);
        }

        Glide.with(getActivity())
                .load(currentEvent.getImageUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(eventPicture);
    }
}
