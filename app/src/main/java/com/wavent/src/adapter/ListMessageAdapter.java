package com.wavent.src.adapter;

/**
 * Created by arnau on 09/12/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wavent.R;
import com.wavent.src.model.Message;
import com.wavent.src.model.Session;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by arnaud on 22/01/16.
 */
public class ListMessageAdapter extends ArrayAdapter<Message> {

    private final Context context;
    private final ArrayList<Message> itemsArrayList;
    private static final String SPACE=" ";

    public ListMessageAdapter(Context context, ArrayList<Message> itemsArrayList) {
        super(context, R.layout.row_list_user, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = null;
        if(itemsArrayList.get(position).getIdUser().equals(Session.getInstance(null).getUserConnected().getId())){
             rowView = inflater.inflate( R.layout.row_message_left, parent, false);
        } else {
             rowView = inflater.inflate( R.layout.row_message_right, parent, false);
        }

        ImageView profilePicture = (ImageView) rowView.findViewById(R.id.UserListImage);
        TextView messageText = (TextView) rowView.findViewById(R.id.messageTextView);

        messageText.setText(itemsArrayList.get(position).getMessage());


        return rowView;
    }
}
