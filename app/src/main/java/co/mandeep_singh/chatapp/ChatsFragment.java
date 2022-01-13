package co.mandeep_singh.chatapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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


public class ChatsFragment extends Fragment implements ConversationAdapter.OnNoteListener , SwipeRefreshLayout.OnRefreshListener{

    View rootView;
    private RecyclerView recyclerView;
    ConversationAdapter adapter;
    ArrayList<ConversationModel> conversationsList = new ArrayList<ConversationModel>();
    SwipeRefreshLayout mSwipeRefreshLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_conversations, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.conversations_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        showData();

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.indigo,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

//        mSwipeRefreshLayout.post(new Runnable() {
//
//            @Override
//            public void run() {
//
//                mSwipeRefreshLayout.setRefreshing(true);
//
//                // Fetching data from server
//                showData();
//            }
//        });

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

                int len = conversations.length();

                for(int i = 0; i < len; i++ ){

                    JSONObject conversation = conversations.getJSONObject(i);

                    String createdAt = conversation.getString("createdAt");
                    String _id = conversation.getString("_id");

                    JSONObject user = conversation.getJSONObject("users");
                    String name = user.getString("receiverName");
                    String receiverId = user.getString("receiverId");

                    JSONObject jobDetails = conversation.getJSONObject("jobDetails");
                    String jobType = jobDetails.getString("jobType");

                    ConversationModel conversationModel = new ConversationModel(_id,jobType,name,createdAt,receiverId);
                    conversationsList.add(conversationModel);
                    adapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                }


            } catch (Exception e) {
                Log.d("jsonerror",e.getMessage());
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
            i.putExtra("receiverId", conversationsList.get(position).getReceiverId());
        getContext().startActivity(i);
    }

    @Override
    public void onRefresh() {
        conversationsList.clear();
        showData();
    }
}