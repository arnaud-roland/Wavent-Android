package com.wavent.src.manager;

import android.content.Context;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wavent.src.activity.ListEventActivity;
import com.wavent.src.activity.LoginActivity;
import com.wavent.src.model.Session;
import com.wavent.src.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arnau on 31/10/2016.
 */
public class ApiManager {

    private static String API_URL = "http://54.244.143.188:8080/";

    private static ApiManager ourInstance = new ApiManager();

    public static ApiManager getInstance() {
        return ourInstance;
    }

    private ApiManager() {}

    /** --------------------------------------------------------------------------
     *                              INTERFACES
     *  --------------------------------------------------------------------------
     */

    public interface OnAuthCallBack {
        void onSucess(JSONObject userAutowired);
        void onError(VolleyError error);
    }
    public interface OnEventsReceived {
        void onSucess(JSONArray newEvents);
        void onError(VolleyError error);
    }

    public interface OnUserCreated {
        void onSucess(JSONObject userAutowired);
        void onError(VolleyError error);
    }

    /** --------------------------------------------------------------------------
     *                              FUNCTIONS
     *  --------------------------------------------------------------------------
     */
    /**
     * Permet la création d'un user
     * @param context
     * @param params
     */
    public void createUser(final Context context, JSONObject params, final OnUserCreated callback){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, API_URL+"users", params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSucess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                });
        Volley.newRequestQueue(context).add(jsObjRequest);
    }

    /**
     * Permet l'authetification coté serveur
     * @param context
     * @param params
     * @param callback
     */
    public void authenticateUser(Context context, JSONObject params, final OnAuthCallBack callback){

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, API_URL+"users/auth", params, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSucess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                });
        Volley.newRequestQueue(context).add(jsObjRequest);
    }

    /**
     * Récupère les évenements de l'utilisateur connecté
     * @param context
     * @param callback
     */
    public void getEventByUser(Context context, final OnEventsReceived callback){
        String idUser = Session.getInstance(null).getUserConnected().getId();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(API_URL+"events/user/"+idUser,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onSucess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                });
        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }

    public void getEvent(Context context, final OnEventsReceived callback){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(API_URL+"events",
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onSucess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                });
        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }

    public void createEvent(Context context, JSONObject params){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, API_URL+"events", params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("sucess");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error");
                    }
                });
        Volley.newRequestQueue(context).add(jsObjRequest);
    }
}
