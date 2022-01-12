package co.mandeep_singh.chatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.mandeep_singh.chatapp.HomeActivity;
import co.mandeep_singh.chatapp.Model.JobModel;
import co.mandeep_singh.chatapp.R;

public class AdapterList extends RecyclerView.Adapter<AdapterList.MyViewHolder> {

    private List<JobModel> jobsList;
    private HomeActivity activity;

    public AdapterList(HomeActivity homeActivity, List<JobModel> JobsList){
        this.jobsList = JobsList;
        activity = homeActivity;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.job_item,parent,false);
        return new MyViewHolder(view);
    }



    public Context getContext(){
        return activity;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        JobModel jobModel = jobsList.get(position);
        holder.name.setText(jobModel.getName());
        holder.jobType.setText(jobModel.getJobType());
        holder.salary.setText(jobModel.getSalary());
        holder.userId.setText(jobModel.getUserId());
        holder.location.setText(jobModel.getLocation());
        holder.lastdate.setText(jobModel.getLastdate());
    }



    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, jobType, location, salary, lastdate, userId;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            jobType = itemView.findViewById(R.id.jobType);
            salary = itemView.findViewById(R.id.salary);
            lastdate = itemView.findViewById(R.id.lastdate);
            userId = itemView.findViewById(R.id.userId);
        }
    }
}