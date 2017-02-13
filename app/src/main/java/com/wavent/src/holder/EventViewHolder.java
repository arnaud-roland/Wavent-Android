package com.wavent.src.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wavent.R;
import com.wavent.src.listener.OnClickReceiverListener;
import com.wavent.src.manager.ApiManager;
import com.wavent.src.model.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by arnau on 29/11/2016.
 */
public class EventViewHolder extends RecyclerView.ViewHolder{

    private TextView nameEventTV;
    private TextView subjectEventTV;
    private TextView dateEventTV;
    private ImageView imageView;
    private ProgressBar progressBar;
    private ImageButton button;

    private int mExpandedPosition = View.GONE;



    public EventViewHolder(View itemView) {
        super(itemView);

        nameEventTV = (TextView) itemView.findViewById(R.id.textName);
        subjectEventTV = (TextView) itemView.findViewById(R.id.textSubject);
        dateEventTV = (TextView) itemView.findViewById(R.id.textDate);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar3);
        button = (ImageButton) itemView.findViewById(R.id.expandableButton);

    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(Context context, final Event event, final OnClickReceiverListener listener){

        nameEventTV.setText(event.getName());
        subjectEventTV.setText(event.getSubject());

        if(event.getDate()!=null){
            SimpleDateFormat formater = null;
            formater = new SimpleDateFormat("EEEE d MMM yyyy");
            dateEventTV.setText(formater.format(event.getDate()));
        }

        button.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mExpandedPosition == View.VISIBLE){
                    subjectEventTV.setVisibility(View.GONE);
                    mExpandedPosition = View.GONE;
                } else {
                    subjectEventTV.setVisibility(View.VISIBLE);
                    mExpandedPosition = View.VISIBLE;
                }
            }
        });
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(event);
            }
        });
        Glide.with(context)
                .load(event.getImageUrl())
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
                .into(imageView);
    }
}
