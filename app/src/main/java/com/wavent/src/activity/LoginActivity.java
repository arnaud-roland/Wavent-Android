package com.wavent.src.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wavent.R;
import com.wavent.src.manager.ApiManager;
import com.wavent.src.model.Session;
import com.wavent.src.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arnau on 31/10/2016.
 */
public class LoginActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText passEditText;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameEditText = (EditText) findViewById(R.id.editTextNameAuth);
        passEditText = (EditText) findViewById(R.id.editTextPassAuth);
        pb = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void authenticate(View view){

        pb.setVisibility(ProgressBar.VISIBLE);

        String login = nameEditText.getText().toString();
        String password = passEditText.getText().toString();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put(getString(R.string.api_login_param_user),login);
            jsonParams.put(getString(R.string.api_login_param_pwd),password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiManager.getInstance().authenticateUser(LoginActivity.this, jsonParams, new ApiManager.OnAuthCallBack(){

            @Override
            public void onSucess(JSONObject jsonUser) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                User userAutowired = gson.fromJson(jsonUser.toString(),User.class);

                pb.setVisibility(ProgressBar.INVISIBLE);
                Session.getInstance(userAutowired);
                Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(VolleyError error) {
                pb.setVisibility(ProgressBar.INVISIBLE);
                Toast toast = Toast.makeText(LoginActivity.this, "Erreur d'authentification", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void setRegistration(View view){
        Intent intentReg = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intentReg);
    }
}
