package co.mandeep_singh.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import co.mandeep_singh.chatapp.Adapter.AdapterList;
import co.mandeep_singh.chatapp.Adapter.ConversationAdapter;
import co.mandeep_singh.chatapp.Model.ConversationModel;
import co.mandeep_singh.chatapp.Model.JobModel;
import co.mandeep_singh.chatapp.Networking.Connection;

public class ConversationsActivity extends AppCompatActivity implements ConversationAdapter.OnNoteListenerC{

    private RecyclerView recyclerView;
    ConversationAdapter adapter;
    ArrayList<ConversationModel> conversationsList = new ArrayList<ConversationModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_conversations);
        recyclerView = (RecyclerView) findViewById(R.id.conversations_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showData();
    }

    private void showData() {

        String URL = Connection.getApi();
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL +"conversation/myconversation/", null, response -> {

            try {

                JSONArray Jarray  = response.getJSONArray("Conversations");

                for (int i = 0; i < Jarray.length(); i++)
                {
                    JSONObject jsonObject = Jarray.getJSONObject(i);
                    JSONArray membersObject = jsonObject.getJSONArray("members");


                    String firstMember = membersObject.get(0).toString();
                    String secondMember = membersObject.get(0).toString();
                        if(firstMember.trim().equals(Connection.getUserId().trim()) || secondMember.trim().equals(Connection.getUserId().trim())) {
                            ConversationModel item = new ConversationModel(firstMember, secondMember);
                            conversationsList.add(item);
                            adapter.notifyDataSetChanged();
                        }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> Log.d("myapp", "Something went wrong")){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //build the headers
                final Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                params.put("Authorization","Bearer "+ Connection.getToken());
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);

        adapter = new ConversationAdapter(this, conversationsList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoteClickC(int position) {

    }
}