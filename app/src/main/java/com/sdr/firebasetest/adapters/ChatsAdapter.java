package com.sdr.firebasetest.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdr.firebasetest.ChatRoomActivity;
import com.sdr.firebasetest.R;
import com.sdr.firebasetest.models.Chat;
import com.sdr.firebasetest.models.ChatRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 12/6/15.
 */
public class ChatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Chat> chats = new ArrayList<>();
    private final int userID;

    private static final int VIEW_CHAT = 0;
    private static final int VIEW_USER_CHAT = 1;

    public void addChat(Chat room){
        chats.add(room);
        notifyItemInserted(chats.size() - 1);
    }

    public ChatsAdapter(int userID){
        this.userID = userID;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_CHAT:
                return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_room_item,parent, false));

            case VIEW_USER_CHAT:
                return new MyChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_room_my_item,parent, false));

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyChatViewHolder){
            MyChatViewHolder myChatViewHolder = (MyChatViewHolder) holder;
            myChatViewHolder.textViewLineOne.setText(chats.get(position).getText());
            myChatViewHolder.textViewLineTwo.setText(Integer.toString(chats.get(position).getUserId()));
        } else if (holder instanceof  ChatViewHolder){
            ChatViewHolder myChatViewHolder = (ChatViewHolder) holder;
            myChatViewHolder.textViewLineOne.setText(chats.get(position).getText());
            myChatViewHolder.textViewLineTwo.setText(Integer.toString(chats.get(position).getUserId()));
        }



    }

    @Override
    public int getItemViewType(int position) {

        Chat chat = chats.get(position);

        if(chat.getUserId() == userID){
            return VIEW_USER_CHAT;
        }

        return VIEW_CHAT;
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    protected class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView textViewLineOne, textViewLineTwo;

        public ChatViewHolder(View itemView) {
            super(itemView);
            textViewLineOne = (TextView)itemView.findViewById(R.id.textLine1);
            textViewLineTwo = (TextView)itemView.findViewById(R.id.textLine2);
        }
    }

    protected class MyChatViewHolder extends RecyclerView.ViewHolder {

        TextView textViewLineOne, textViewLineTwo;

        public MyChatViewHolder(View itemView) {
            super(itemView);
            textViewLineOne = (TextView)itemView.findViewById(R.id.textLine1);
            textViewLineTwo = (TextView)itemView.findViewById(R.id.textLine2);
        }
    }

}
