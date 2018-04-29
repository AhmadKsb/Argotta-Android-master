package com.example.ahmad.chat_3.models.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Chat {

    @PrimaryKey
    @ColumnInfo(name = "sender_id")
    @NonNull
    public String senderId;

    @ColumnInfo(name = "user_name")
    @NonNull
    public String userName;

    @ColumnInfo(name = "sender_lang_code")
    public String senderLangCode;

    @NonNull
    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(@NonNull String senderId) {
        this.senderId = senderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSenderLangCode() {
        return senderLangCode;
    }

    public void setSenderLangCode(String senderLangCode) {
        this.senderLangCode = senderLangCode;
    }
}
