package com.example.ahmad.chat_3.models.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Message {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("sender_id")
    @ColumnInfo(name = "sender_id")
    public String senderId;

    @SerializedName("sender_lang_code")
    @ColumnInfo(name = "sender_lang_code")
    public String senderLangCode;

    @SerializedName("content")
    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "from_user")
    public boolean fromUser;

    public boolean isFromUser() {
        return fromUser;
    }

    public void setFromUser(boolean fromUser) {
        this.fromUser = fromUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderLangCode() {
        return senderLangCode;
    }

    public void setSenderLangCode(String senderLangCode) {
        this.senderLangCode = senderLangCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
