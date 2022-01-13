package co.mandeep_singh.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import co.mandeep_singh.chatapp.Adapter.MessagesAdapter;
import co.mandeep_singh.chatapp.Logic.HomeLogic;
import co.mandeep_singh.chatapp.Model.MessageModel;
import co.mandeep_singh.chatapp.Networking.Connection;


import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class ChatScreen extends AppCompatActivity {


    //socket logic
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Connection.getApi());
        } catch (URISyntaxException e) {}
    }



    private RecyclerView recyclerView;
    private EditText inputMessage;
    View sendBtn;
    MessagesAdapter adapter;
    ProgressBar progressBar;
    ArrayList<MessageModel> messagesList = new ArrayList<MessageModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_chat_screen);
        Intent intent = getIntent();
        String conversationId = intent.getStringExtra("conversationId");
        String receiverId = intent.getStringExtra("receiverId");
         progressBar = (ProgressBar) findViewById(R.id.progressBarChat);
         recyclerView = (RecyclerView) findViewById(R.id.chatRecycleView);
         inputMessage = (EditText) findViewById(R.id.inputMessage);
         sendBtn = findViewById(R.id.sendBtn);
         sendBtn.setOnClickListener(view->sendMessage(conversationId,receiverId));
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         getMyMessages(conversationId);

         //socket connect
        mSocket.connect();
        mSocket.emit("addUser", Connection.getUserId());
       mSocket.on("getMessage", onNewMessage);

    }

    public Context getAct(){
        return this;
    }


    public void getMyMessages(String conversationId){

        String URL = Connection.getApi() + "message/mymessages/";
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("conversationId", conversationId);
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

                    JSONArray jsonArray = response.getJSONArray("messages");


                    for(int i = 0; i < jsonArray.length(); i++){

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        final String text = jsonObject1.getString("text");
                        final String sender = jsonObject1.getString("sender");
                        final String conversationId = jsonObject1.getString("conversationId");
                        final String _id = jsonObject1.getString("_id");
                        final String createdAt = jsonObject1.getString("createdAt");

                        MessageModel item = new MessageModel(text,sender,conversationId,_id,createdAt);
                        messagesList.add(item);
                        adapter.notifyDataSetChanged();

                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //api call failed
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
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

        Volley.newRequestQueue(this).add(jsonObjectRequest);

        adapter = new MessagesAdapter(this, messagesList);

        recyclerView.setAdapter(adapter);
    }


    //soket

    public void sendMessage(String conversationId,String receiverId){
        String message = inputMessage.getText().toString().trim();
        HomeLogic.firstMessage(conversationId,receiverId,getApplicationContext(),message);
        messagesList.add(new MessageModel(message,Connection.getUserId(),conversationId,"test", LocalDateTime.now().toString()));
        adapter.notifyDataSetChanged();
        inputMessage.setText("");
    }



    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Activity activity = (Activity) getAct();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    String text;
                    String sender,conversationId,createdAt,_id;


                    try {
                        JSONObject myMessage = data.getJSONObject("myMessage");

                        text = myMessage.getString("text");
                        sender = myMessage.getString("sender");
                        conversationId = myMessage.getString("conversationId");
                        _id = myMessage.getString("_id");
                        createdAt = myMessage.getString("createdAt");
                        // add the message to view
                        messagesList.add(new MessageModel(text,sender,conversationId,_id,createdAt));
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Log.d("msg", e.getMessage());
                    }


                }
            });
        }
    };


}


