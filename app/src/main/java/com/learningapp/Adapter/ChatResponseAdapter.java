package com.learningapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.learningapp.Modal.Message;
import com.learningapp.R;

import java.util.List;

public class ChatResponseAdapter extends RecyclerView.Adapter<ChatResponseAdapter.ViewHolder>{
    List<Message> messageList;

    public ChatResponseAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      //  View chat = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_sender,null);
        View chat = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);
        return new ViewHolder(chat);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messageList.get(position);
        if(message.getSender().equals(Message.SENT_BY_USER)){
            holder.receivedMessageCard.setVisibility(View.GONE);
            holder.sentMessageCard.setVisibility(View.VISIBLE);
            holder.messageSend.setText(message.getMessage());
        }else{
            holder.messageReceived.setText(message.getMessage());
            holder.receivedMessageCard.setVisibility(View.VISIBLE);
            holder.sentMessageCard.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        MaterialCardView sentMessageCard, receivedMessageCard;
        TextView messageReceived, messageSend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageReceived = itemView.findViewById(R.id.receivedMessage);
            messageSend = itemView.findViewById(R.id.sentMessage);
            sentMessageCard = itemView.findViewById(R.id.sentMessageCard);
            receivedMessageCard = itemView.findViewById(R.id.receivedMessageCard);
        }
    }
}
