package com.learningapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.learningapp.Modal.Chats;
import com.learningapp.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    FirebaseUser firebaseUser;
    private Context context;
    public static final int MSG_TYPE_RECEIVER = 0;
    public static final int MSG_TYPE_SENDER = 1;
    private List<Chats> chatsList;

    public MessageAdapter(Context context, List<Chats> chatsList){
        this.context = context;
        this.chatsList = chatsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType==MSG_TYPE_SENDER){
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_sender, parent, false);
        }
        else{
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_receiver, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chats chats = chatsList.get(position);
        holder.message.setText(chats.getMessage());
    }

    @Override
    public int getItemCount() {
        return chatsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chatsList.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_SENDER;
        }
        else{
            return MSG_TYPE_RECEIVER;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView message;
       // public ImageView profile_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
          //  profile_image = itemView.findViewById(R.id.profile_image);
        }
    }
}
