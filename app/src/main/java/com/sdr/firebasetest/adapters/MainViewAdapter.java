package com.sdr.firebasetest.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdr.firebasetest.ChatRoomActivity;
import com.sdr.firebasetest.R;
import com.sdr.firebasetest.models.ChatRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 12/6/15.
 */
public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.ViewHolder> {

    List<ChatRoom> chatRoomList = new ArrayList<>();

    public void addChatRoom(ChatRoom room){
        chatRoomList.add(room);
        notifyItemInserted(chatRoomList.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_room_item,parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewLineOne.setText(chatRoomList.get(position).getTitle());
        holder.textViewLineTwo.setText(chatRoomList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return chatRoomList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewLineOne, textViewLineTwo;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewLineOne = (TextView)itemView.findViewById(R.id.textLine1);
            textViewLineTwo = (TextView)itemView.findViewById(R.id.textLine2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            ChatRoom room = chatRoomList.get(getAdapterPosition());

            Intent intent = new Intent(v.getContext(), ChatRoomActivity.class);
            intent.putExtra(ChatRoomActivity.KEY_ROOM_ID, room.getId());
            v.getContext().startActivity(intent);

        }
    }
}
