package co.mandeep_singh.chatapp.Logic;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.mandeep_singh.chatapp.Networking.Connection;

public class HomeLogic {


    public static  void createConversation(String businessId, Context context){

        String URL = Connection.getApi() + "conversation/newconversation/";
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("buisnessId", businessId);
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //api call succeeded
                try {
                    //get conversation id

                    JSONObject jsonObject = response.getJSONObject("Conversation");
                    String conversationId = jsonObject.getString("_id");
                    JSONArray jsonArray = jsonObject.getJSONArray("members");
                    String member1 = jsonArray.getString(0);
                    String member2 = jsonArray.getString(1);
                    String receiverId = "";
                    if(!member1.equals(Connection.getUserId())){
                        receiverId = member1;
                    }
                    else if(!member2.equals(Connection.getUserId()))
                    {
                        receiverId = member2;
                    }
                    else{
                        Toast.makeText(context,"You created this job!!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    firstMessage(conversationId,receiverId,context, "I want to apply for this job.");


                } catch (Exception e) {
                    Toast.makeText(context,"Something went wrong!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //api call failed

                Toast.makeText(context,"Something went wrong!",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //build the headers
                final Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                params.put("Authorization","Bearer "+ Connection.getToken());
                return params;
            }
        };

        Volley.newRequestQueue(context).add(jsonObjectRequest);


    }

    public static  void firstMessage(String conversationId,String receiverId, Context context, String message){

        String URL = Connection.getApi() + "message/newmessage/";
        final  JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("conversationId", conversationId);
            jsonObject.put("text", message);
            jsonObject.put("receiverId", receiverId);
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //api call succeeded
                try {
                    final String success = response.getString("message");
                    if(success.equals("sent text")){
                        Log.d("SUCCESS","SENT");
                    }

                } catch (Exception e) {
                    Toast.makeText(context,"Something went wrong!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //api call failed

                Toast.makeText(context,"Something went wrong!",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //build the headers
                final Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                params.put("Authorization","Bearer "+ Connection.getToken());
                return params;
            }
        };

        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }



}
