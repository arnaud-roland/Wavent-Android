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
import com.wavent.src.model.User;

import java.util.ArrayList;

/**
 * Created by arnaud on 22/01/16.
 */
public class ListUserAdapter extends ArrayAdapter<User> {

    private final Context context;
    private final ArrayList<User> itemsArrayList;
    private static final String SPACE=" ";

    public ListUserAdapter(Context context, ArrayList<User> itemsArrayList) {
        super(context, R.layout.row_list_user, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate( R.layout.row_list_user, parent, false);

        ImageView profilePicture = (ImageView) rowView.findViewById(R.id.UserListImage);
        TextView nameText = (TextView) rowView.findViewById(R.id.UserListName);

        nameText.setText(itemsArrayList.get(position).getPrenom()+" "+itemsArrayList.get(position).getNom());

        //TODO Glide download picture

        return rowView;
    }
}
