package com.sdr.firebasetest.models;

import java.util.List;

/**
 * Created by user on 11/22/15.
 */
public class ChatRoom {

    String title;
    String id;
    List<Chat> chatList;

    public ChatRoom(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }
}
