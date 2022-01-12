package co.mandeep_singh.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Objects;

import co.mandeep_singh.chatapp.Adapter.AdapterList;
import co.mandeep_singh.chatapp.Auth.Auth;
import co.mandeep_singh.chatapp.Model.JobModel;
import co.mandeep_singh.chatapp.Networking.Connection;

public class HomeActivity extends AppCompatActivity implements AdapterList.OnNoteListener {

    private RecyclerView recyclerView;
    private ImageButton logOut;
    AdapterList adapter;
    ArrayList<JobModel> jobsList = new ArrayList<JobModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_home);
        recyclerView = (RecyclerView) findViewById(R.id.jobs_list);
        logOut = (ImageButton) findViewById(R.id.logout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        logOut.setOnClickListener(view -> setLogOut());
        showData();
    }

    private void showData() {

        String URL = Connection.getApi();
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

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

        adapter = new AdapterList(this, jobsList,this);
        recyclerView.setAdapter(adapter);
    }

    //logout user
    public void setLogOut(){
        Auth auth = new Auth();
        auth.signOut(this);
    }

    @Override
    public void onNoteClick(int position) {
        JobModel jobModel = jobsList.get(position);

        Intent intent;
        if(jobModel.getUserId().equals(Connection.getUserId())){
            intent = new Intent(this, ConversationsActivity.class);
        }

        else{

            //handle apply for new job

            intent = new Intent(this, ChatScreen.class);
        }
        startActivity(intent);

    }
}