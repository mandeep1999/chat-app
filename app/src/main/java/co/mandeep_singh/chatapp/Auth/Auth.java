package co.mandeep_singh.chatapp.Auth;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telecom.InCallService;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import co.mandeep_singh.chatapp.HomeActivity;
import co.mandeep_singh.chatapp.MainActivity;
import co.mandeep_singh.chatapp.Networking.Connection;

public class Auth {



    public void signUp(String name, String number, String password, Activity activity, ProgressBar progressBar) {
        final int[] result = {0};
        String URL = Connection.getApi() + "auth/signup/";
        final  JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("password", password);
            jsonObject.put("phone", number);
            jsonObject.put("role", "user");
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //api call succeeded
                try {
                    progressBar.setVisibility(View.INVISIBLE);

                    final String token = response.getString("token");
                    final String userId = response.getString("userId");

                    SharedPreferences sharedPreferences = activity.getSharedPreferences("AuthDetails", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();

                    myEdit.putString("token", token);
                    myEdit.putString("userId", userId);
                    Connection.setToken(token);
                    Connection.setUserId(userId);
                    myEdit.apply();

                    Intent intent = new Intent(activity, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(activity,"Something went wrong!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //api call failed
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(activity,"Something went wrong!",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
              //build the headers
                final Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                return params;
            }
        };

        Volley.newRequestQueue(activity).add(jsonObjectRequest);


    }

    public void signIn(String number, String password, Activity activity, ProgressBar progressBar) {
        final int[] result = {0};
        String URL = Connection.getApi() + "auth/login/";
        final  JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("password", password);
            jsonObject.put("phone", number);

        }
        catch(JSONException e){
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //api call succeeded
                try {
                    progressBar.setVisibility(View.INVISIBLE);

                    final String token = response.getString("token");
                    final String userId = response.getString("userId");

                    SharedPreferences sharedPreferences = activity.getSharedPreferences("AuthDetails", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();

                    myEdit.putString("token", token);
                    myEdit.putString("userId", userId);
                    Connection.setToken(token);
                    Connection.setUserId(userId);
                    myEdit.apply();

                    Intent intent = new Intent(activity, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(activity,"Something went wrong!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //api call failed
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(activity,"Something went wrong!",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //build the headers
                final Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                return params;
            }
        };

        Volley.newRequestQueue(activity).add(jsonObjectRequest);


    }


    public void signOut(Activity activity){
        SharedPreferences sharedPreferences = activity.getSharedPreferences("AuthDetails",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("token", "");
        myEdit.putString("userId", "");
        Connection.setUserId("");
        Connection.setToken("");
        myEdit.apply();
        Intent i = new Intent(activity,MainActivity.class);
        activity.startActivity(i);
    }



}