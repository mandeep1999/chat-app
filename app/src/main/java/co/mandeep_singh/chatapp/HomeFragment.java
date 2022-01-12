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
import android.widget.ImageButton;

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

import co.mandeep_singh.chatapp.Adapter.AdapterList;
import co.mandeep_singh.chatapp.Auth.Auth;
import co.mandeep_singh.chatapp.Model.JobModel;
import co.mandeep_singh.chatapp.Networking.Connection;
import co.mandeep_singh.chatapp.R;


public class HomeFragment extends Fragment implements AdapterList.OnNoteListener{

    View rootView;
    private RecyclerView recyclerView;
    private ImageButton logOut;
    AdapterList adapter;
    ArrayList<JobModel> jobsList = new ArrayList<JobModel>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container,false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.jobs_list);
        logOut = (ImageButton) rootView.findViewById(R.id.logout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        logOut.setOnClickListener(view -> setLogOut());
        showData();
        return  rootView;
    }

    private void showData() {

        String URL = Connection.getApi();
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL +"business/", null, response -> {

            try {
                Log.d("myapp", "The response is " + response.getString("message"));


                JSONArray Jarray  = response.getJSONArray("business");

                for (int i = 0; i < Jarray.length(); i++)
                {
                    JSONObject jsonObject = Jarray.getJSONObject(i);
                    String _id = jsonObject.getString("_id");
                    String name = jsonObject.getString("name");
                    String jobType = jsonObject.getString("jobType");
                    String location = jsonObject.getString("location");
                    String salary = jsonObject.getString("salary");
                    String lastdate = jsonObject.getString("lastdate");
                    String userId = jsonObject.getString("userId");
                    String date = lastdate.substring(0, 10);
                    JobModel item = new JobModel(_id,name,jobType,location,salary,"Apply by : " + date,userId);
                    jobsList.add(item);
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

        adapter = new AdapterList(getContext(), jobsList,this);
        recyclerView.setAdapter(adapter);
    }

    //logout user
    public void setLogOut(){
        Auth auth = new Auth();
        auth.signOut(getActivity());
    }

    @Override
    public void onNoteClick(int position) {
        JobModel jobModel = jobsList.get(position);
        Intent intent;

            //handle apply for new job

            intent = new Intent(getContext(), ChatScreen.class);

        startActivity(intent);

    }
}