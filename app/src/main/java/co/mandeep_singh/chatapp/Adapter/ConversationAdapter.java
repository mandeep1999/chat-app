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
    private OnNoteListenerC onNoteListenerC;

    public ConversationAdapter(Context context, List<ConversationModel> conversationsList, OnNoteListenerC onNoteListener){
        this.conversationsList = conversationsList;
        this.context = context;
        this.onNoteListenerC = onNoteListenerC;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.conversation_item,parent,false);
        return new MyViewHolder(view,onNoteListenerC);
    }



    public Context getContext(){
        return context;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ConversationModel conversationModel = conversationsList.get(position);
        holder.chatName.setText(conversationModel.getName1());

    }





    @Override
    public int getItemCount() {
        return conversationsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        OnNoteListenerC onNoteListenerC;
        TextView chatName;


        public MyViewHolder(@NonNull View itemView, OnNoteListenerC onNoteListenerC) {
            super(itemView);
            this.onNoteListenerC = onNoteListenerC;
            chatName = itemView.findViewById(R.id.chatName);


            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            onNoteListenerC.onNoteClickC(getAdapterPosition());
        }
    }

    public interface OnNoteListenerC{
        void onNoteClickC(int position);
    }

}