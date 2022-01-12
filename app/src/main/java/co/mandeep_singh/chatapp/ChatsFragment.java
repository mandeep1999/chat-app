package co.mandeep_singh.chatapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import co.mandeep_singh.chatapp.Adapter.ConversationAdapter;
import co.mandeep_singh.chatapp.Model.ConversationModel;
import co.mandeep_singh.chatapp.Networking.Connection;


public class ChatsFragment extends Fragment implements ConversationAdapter.OnNoteListener{

    View rootView;
    private RecyclerView recyclerView;
    ConversationAdapter adapter;
    ArrayList<ConversationModel> conversationsList = new ArrayList<ConversationModel>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_conversations, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.conversations_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        showData();
        return rootView;
    }
    private void showData() {

        String URL = Connection.getApi();
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL +"conversation/myconversation/", null, response -> {

            try {

                JSONArray conversations  = response.getJSONArray("Conversations");

                for(int i = 0; i < conversations.length(); i++ ){

                    JSONObject conversation = conversations.getJSONObject(i);

                    String createdAt = conversation.getString("createdAt");
                    String _id = conversation.getString("_id");

                    JSONArray users = conversation.getJSONArray("users");
                    JSONObject user1 = users.getJSONObject(0);
                    JSONObject user2 = users.getJSONObject(1);
                    String name1 = user1.getString("name");
                    String name2 = user2.getString("name");
                    String userId1 = user1.getString("_id");
                    String userId2 = user2.getString("_id");

                    JSONObject jobDetails = conversation.getJSONObject("jobDetails");
                    String jobType = jobDetails.getString("jobType");

                    ConversationModel conversationModel = new ConversationModel(_id,userId1,userId2,jobType,name1,name2,createdAt);
                    conversationsList.add(conversationModel);
                    adapter.notifyDataSetChanged();
                }


            } catch (Exception e) {
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

        adapter = new ConversationAdapter(getContext(), conversationsList, this::onNoteClick);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoteClick(int position) {
            Intent i = new Intent(getContext(), ChatScreen.class);
            i.putExtra("conversationId", conversationsList.get(position).get_id());
        getContext().startActivity(i);
    }
}