package co.mandeep_singh.chatapp;

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


public class ChatsFragment extends Fragment implements ConversationAdapter.OnNoteListenerC{

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

                JSONArray Jarray  = response.getJSONArray("Conversations");

                for (int i = 0; i < Jarray.length(); i++)
                {
                    JSONObject jsonObject = Jarray.getJSONObject(i);
                    JSONArray membersObject = jsonObject.getJSONArray("members");


                    String firstMember = membersObject.get(0).toString();
                    String secondMember = membersObject.get(0).toString();

                        ConversationModel item = new ConversationModel(firstMember, secondMember);
                        conversationsList.add(item);
                        adapter.notifyDataSetChanged();


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

        adapter = new ConversationAdapter(getContext(), conversationsList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoteClickC(int position) {

    }
}