package com.wavent.src.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wavent.R;
import com.wavent.databinding.UserFragmentBinding;
import com.wavent.src.model.Session;
import com.wavent.src.model.User;


/**
 * Created by arnau on 08/12/2016.
 */
public class UserFragment extends Fragment {

    public UserFragment() {
    }

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UserFragmentBinding binding = DataBindingUtil.inflate(inflater,R.layout.user_fragment,container,false);
        User detailUser = Session.getInstance(null).getUserConnected();
        binding.setUser(detailUser);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
