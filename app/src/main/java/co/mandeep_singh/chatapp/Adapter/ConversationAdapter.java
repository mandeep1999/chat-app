package co.mandeep_singh.chatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.mandeep_singh.chatapp.Model.ConversationModel;
import co.mandeep_singh.chatapp.R;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MyViewHolder>{

    private List<ConversationModel> conversationsList;
    private Context context;
    private OnNoteListener onNoteListener;

    public ConversationAdapter(Context context, List<ConversationModel> conversationsList, OnNoteListener onNoteListener){
        this.conversationsList = conversationsList;
        this.context = context;
        this.onNoteListener = onNoteListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.conversation_item,parent,false);
        return new MyViewHolder(view,onNoteListener);
    }



    public Context getContext(){
        return context;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ConversationModel conversationModel = conversationsList.get(position);
        holder.chatName.setText(conversationModel.getName());
        holder.jobTypeC.setText(conversationModel.getJobType());
    }





    @Override
    public int getItemCount() {
        return conversationsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        OnNoteListener onNoteListener;
        TextView chatName, jobTypeC;


        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.onNoteListener = onNoteListener;
            chatName = itemView.findViewById(R.id.chatName);
            jobTypeC = itemView.findViewById(R.id.jobTypeC);

            itemView.setOnClickListener(this::onClick);
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