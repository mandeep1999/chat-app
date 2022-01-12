package co.mandeep_singh.chatapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.mandeep_singh.chatapp.ChatScreen;
import co.mandeep_singh.chatapp.Model.MessageModel;
import co.mandeep_singh.chatapp.Networking.Connection;
import co.mandeep_singh.chatapp.R;

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {


        private List<MessageModel> messagesList;
        private ChatScreen activity;

        public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

        public MessagesAdapter(ChatScreen chatScreen, List<MessageModel> messagesList){

            this.messagesList = messagesList;
            this.activity = chatScreen;
        }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           if(viewType == VIEW_TYPE_SENT){
               return new SentMessageViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_container_sent_message,parent,false));
           }
           else{
               return new ReceivedMessageViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_container_received_message,parent,false));
           }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if(getItemViewType(position) == VIEW_TYPE_SENT){
           ((SentMessageViewHolder) holder).setData(messagesList.get(position));
    }
    else{
        ((ReceivedMessageViewHolder)holder).setData(messagesList.get(position));
    }
    }

    public Context getContext(){
            return getContext();
        }

        @Override
        public int getItemCount() {
            return messagesList.size();
        }

    @Override
    public int getItemViewType(int position) {
        if(Connection.getUserId().equals(messagesList.get(position).getSender())){
            return VIEW_TYPE_SENT;
        }
        else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    public class SentMessageViewHolder extends RecyclerView.ViewHolder{
            TextView textMessageSent,textDateTimeSent;
            public SentMessageViewHolder(@NonNull View itemView){
                super(itemView);
                textMessageSent = itemView.findViewById(R.id.textMessageSent);
                textDateTimeSent = itemView.findViewById(R.id.textDateTimeSent);
            }
            void setData(MessageModel messageModel){
                textMessageSent.setText(messageModel.getText());
                textDateTimeSent.setText(messageModel.getCreatedAt());
            }
        }

    public class ReceivedMessageViewHolder extends RecyclerView.ViewHolder{
        TextView textMessageR,textDateTimeR;
        public ReceivedMessageViewHolder(@NonNull View itemView){
            super(itemView);
            textMessageR = itemView.findViewById(R.id.textMessageR);
            textDateTimeR = itemView.findViewById(R.id.textDateTimeR);
        }
        void setData(MessageModel messageModel){
            textMessageR.setText(messageModel.getText());
            textMessageR.setText(messageModel.getCreatedAt());
        }
    }

    }
