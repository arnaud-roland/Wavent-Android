package com.wavent.src.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wavent.R;
import com.wavent.src.manager.ApiManager;
import com.wavent.src.model.Event;
import com.wavent.src.model.Session;
import com.wavent.src.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {

    private ImageView profilePicture;
    private EditText firstName;
    private EditText lastName;
    private EditText mail;
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        profilePicture = (ImageView) findViewById(R.id.imageViewProfile);
        firstName = (EditText) findViewById(R.id.editTextPrenom);
        lastName = (EditText) findViewById(R.id.editTextNom);
        mail = (EditText) findViewById(R.id.editTextMail);
        pwd = (EditText) findViewById(R.id.editTextPassword);
    }

    public void saveUser(View v){
        String errorMessage = checkDataUser();
        if(errorMessage !=null){
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.error_title_registration))
                    .setMessage(errorMessage)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {}
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            User newUser =  new User(mail.getText().toString(),pwd.getText().toString());
            newUser.setPrenom(firstName.getText().toString());
            newUser.setNom(lastName.getText().toString());

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            gson.toJson(newUser,User.class);
            String jsonString = gson.toJson(newUser,User.class);
            JSONObject params = null;
            try {
                params = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ApiManager.getInstance().createUser(RegistrationActivity.this,params, new ApiManager.OnUserCreated() {
                @Override
                public void onSucess(JSONObject jsonUser) {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    User userAutowired = gson.fromJson(jsonUser.toString(),User.class);

                    Session.getInstance(userAutowired);
                    Intent intent = new Intent(RegistrationActivity.this, ListEventActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onError(VolleyError error) {
                    Toast toast = Toast.makeText(RegistrationActivity.this, "Erreur d'authentification", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }

    }

    public String checkDataUser(){

        String message = null;

        if(firstName.getText().toString().length()==0 ){
            message = getResources().getString(R.string.error_firstName_null);
        } else if( firstName.getText().toString().length()<3){
            message = getResources().getString(R.string.error_firstName_inf);
        }

        if(lastName.getText().toString().length()==0){
            message = getResources().getString(R.string.error_LastName_null);
        } else if( lastName.getText().toString().length()<3){
            message = getResources().getString(R.string.error_LastName_inf);
        }

        if(mail.getText().toString().length()==0){
            message = getResources().getString(R.string.error_mail_null);
        } else if( lastName.getText().toString().length()<3){
            message = getResources().getString(R.string.error_mail_inf);
        }
        //TODO gestion regexp

        if(pwd.getText().toString().length()==0){
            message = getResources().getString(R.string.error_pwd_null);
        }  else if( lastName.getText().toString().length()<6){
            message = getResources().getString(R.string.error_pwd_inf);
        }

        return message;
    }
}
