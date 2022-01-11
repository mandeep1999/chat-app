package co.mandeep_singh.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import co.mandeep_singh.chatapp.Adapter.AdapterList;
import co.mandeep_singh.chatapp.Model.JobModel;
import co.mandeep_singh.chatapp.Networking.Connection;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    AdapterList adapter;
    ArrayList<JobModel> jobsList = new ArrayList<JobModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_home);
        recyclerView = (RecyclerView) findViewById(R.id.jobs_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showData() {
        Connection connection = new Connection();
        String URL = connection.getApi();
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL +"business/", null, response -> {

            try {
                Log.d("myapp", "The response is " + response.getString("status"));
                JSONObject object = response.getJSONObject("data");

                JSONArray Jarray  = object.getJSONArray("items");

//                for (int i = 0; i < Jarray.length(); i++)
//                {
//                    String name = Jarray.getJSONObject(i).getString("name");
//                    String price = Jarray.getJSONObject(i).getString("price");
//                    String extra = Jarray.getJSONObject(i).getString("extra");
//                    if(extra.equals("null"))extra = "";
//                    JobModel item = new ListModal(name,"MRP : " + price,extra);
//                    itemList.add(item);
//                    adapter.notifyDataSetChanged();
//                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> Log.d("myapp", "Something went wrong"));
        requestQueue.add(jsonObjectRequest);

        adapter = new AdapterList(this, jobsList);
        recyclerView.setAdapter(adapter);
    }

}