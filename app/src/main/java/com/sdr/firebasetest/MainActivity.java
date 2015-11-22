package com.sdr.firebasetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.sdr.firebasetest.models.Chat;
import com.sdr.firebasetest.models.ChatRoom;
import com.sdr.firebasetest.models.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_DB_URL = "https://dazzling-torch-2890.firebaseio.com/chat-rooms/";
    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText inputEditText;
    private TextView debugTextView;

    private Firebase rootRef = new Firebase(BASE_DB_URL);

    private List<Chat> chatList = new ArrayList<>();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User();
        user.setId(generateUserId());

        inputEditText = (EditText)findViewById(R.id.inputEditText);
        debugTextView = (TextView) findViewById(R.id.textView);

        findViewById(R.id.updateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChat();
            }
        });



        getSupportActionBar().setTitle("User : "+user.getId());


        connectToFireBase();
    }

    private int generateUserId() {
        return (int) (Math.random() * 10000);
    }

    private void sendChat() {



        ChatRoom chatRoom = new ChatRoom();

        chatRoom.setId(inputEditText.getText().toString());
        chatRoom.setTitle("My First Room");

        List<Chat> chatList = new ArrayList<>(15);

        for (int i = 0 ; i < 15 ; i++){
            Chat chat = new Chat();
            chat.setTimeStamp(i);
            chat.setUserId(user.getId());
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
                Log.d(TAG, "Chat message Added. Text = "+chatRoom.getId());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged "+s);
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

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /*// Clear the chat arraylist
                chatList.clear();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Chat chat = child.getValue(Chat.class);
                    chatList.add(chat);
                }

                updateTextList();*/
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    private void updateTextList() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Chat mChat : chatList){
            stringBuilder.append("User: ");
            stringBuilder.append(mChat.getUserId());

            stringBuilder.append(" : ");
            stringBuilder.append(mChat.getText());

            stringBuilder.append("\n");
        }
        debugTextView.setText(stringBuilder.toString());
    }

}
