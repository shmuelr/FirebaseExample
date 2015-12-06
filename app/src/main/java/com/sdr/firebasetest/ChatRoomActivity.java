package com.sdr.firebasetest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.sdr.firebasetest.adapters.ChatsAdapter;
import com.sdr.firebasetest.models.Chat;
import com.sdr.firebasetest.models.ChatRoom;

public class ChatRoomActivity extends AppCompatActivity {

    public static final String KEY_ROOM_ID = "key_id";
    private static final String TAG = ChatRoomActivity.class.getSimpleName();

    private static String BASE_DB_URL = "https://dazzling-torch-2890.firebaseio.com/chat-rooms/";

    private Firebase rootRef;


    private String roomID;

    private RecyclerView recyclerView;
    private EditText inputET;
    private Button button;

    private ChatsAdapter chatsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        roomID = getIntent().getStringExtra(KEY_ROOM_ID);
        BASE_DB_URL = BASE_DB_URL + roomID+"/chatList/";
        rootRef = new Firebase(BASE_DB_URL);
        getSupportActionBar().setTitle(roomID);

        setGui();
        connectToFireBase();
    }

    private void setGui() {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        button = (Button)findViewById(R.id.updateButton);
        inputET = (EditText)findViewById(R.id.inputEditText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatsAdapter = new ChatsAdapter(App.user.getId());

        recyclerView.setAdapter(chatsAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChatUp();
            }
        });
    }

    private void sendChatUp() {
        String text = inputET.getText().toString();
        Chat chat = new Chat();
        chat.setText(text);
        chat.setUserId(App.user.getId());

        rootRef.child(chatsAdapter.getItemCount()+"").setValue(chat);
    }

    private void connectToFireBase() {

        rootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, dataSnapshot.toString());
                Chat chat = dataSnapshot.getValue(Chat.class);
                Log.d(TAG, "Chat message Added. Text = " + chat.getText());
                chatsAdapter.addChat(chat);

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


}
