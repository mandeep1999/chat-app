package co.mandeep_singh.chatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import co.mandeep_singh.chatapp.Model.JobModel;
import co.mandeep_singh.chatapp.Networking.Connection;
import co.mandeep_singh.chatapp.R;

public class AdapterList extends RecyclerView.Adapter<AdapterList.MyViewHolder> {

    private List<JobModel> jobsList;
    private Context context;
    private OnNoteListener onNoteListener;

    public AdapterList(Context context, List<JobModel> JobsList, OnNoteListener onNoteListener){
        this.jobsList = JobsList;
        this.context = context;
        this.onNoteListener = onNoteListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_item,parent,false);
        return new MyViewHolder(view,onNoteListener);
    }



    public Context getContext(){
        return context;
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
        String u1 = jobModel.getUserId().trim();
        String u2 = Connection.getUserId().trim();
        if(u1.compareTo(u2) == 0 && u1.equalsIgnoreCase(u2) && u2.equals(u1)){

            holder.chatButton.setBackgroundColor(0xFFFFA500);
        }
    }





    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, jobType, location, salary, lastdate, userId,chatButton;
        OnNoteListener onNoteListener;


        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.onNoteListener = onNoteListener;
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            jobType = itemView.findViewById(R.id.jobType);
            salary = itemView.findViewById(R.id.salary);
            lastdate = itemView.findViewById(R.id.lastdate);
            userId = itemView.findViewById(R.id.userId);
            chatButton = itemView.findViewById(R.id.chatButton);

            chatButton.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
               onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}