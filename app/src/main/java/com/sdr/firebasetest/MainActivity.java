package com.sdr.firebasetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.sdr.firebasetest.adapters.MainViewAdapter;
import com.sdr.firebasetest.models.Chat;
import com.sdr.firebasetest.models.ChatRoom;
import com.sdr.firebasetest.models.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_DB_URL = "https://dazzling-torch-2890.firebaseio.com/chat-rooms/";
    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText inputEditText;
    private RecyclerView recyclerView;
    private MainViewAdapter mainViewAdapter = new MainViewAdapter();

    private Firebase rootRef = new Firebase(BASE_DB_URL);

    private List<Chat> chatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEditText = (EditText)findViewById(R.id.inputEditText);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainViewAdapter);

        findViewById(R.id.updateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatRoom chatRoom = new ChatRoom();
                chatRoom.setId(Long.toString(System.currentTimeMillis()/1000l));
                chatRoom.setTitle(inputEditText.getText().toString());
                saveChatRoom(chatRoom);
            }
        });



        getSupportActionBar().setTitle("User : "+App.user.getId());


        connectToFireBase();
    }


    private void saveChatRoom(ChatRoom chatRoom){

        List<Chat> chatList = new ArrayList<>(15);

        for (int i = 0 ; i < 15 ; i++){
            Chat chat = new Chat();
            chat.setTimeStamp(i);
            chat.setUserId(App.user.getId());
            chat.setText("Chat #" + i);
            chatList.add(chat);
        }

        chatRoom.setChatList(chatList);

        Firebase chatRef = rootRef.child(chatRoom.getId());

        chatRef.setValue(chatRoom);

        Log.d(TAG, "Chat saved");
    }

    private void connectToFireBase() {

        rootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatRoom chatRoom = dataSnapshot.getValue(ChatRoom.class);
                Log.d(TAG, "Chat message Added. Text = " + chatRoom.getId());
                updateAdapter(chatRoom);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged " + s);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void updateAdapter(ChatRoom chatRoom) {
        mainViewAdapter.addChatRoom(chatRoom);
    }

}
