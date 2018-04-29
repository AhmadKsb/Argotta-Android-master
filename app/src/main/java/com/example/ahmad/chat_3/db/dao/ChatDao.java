package com.example.ahmad.chat_3.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.ahmad.chat_3.models.db.Chat;

import java.util.List;

@Dao
public interface ChatDao {

    @Query("SELECT * FROM chat")
    List<Chat> getAll();

    @Insert
    void insertAll(List<Chat> chats);

    @Query("DELETE FROM chat WHERE sender_id LIKE :senderId")
    void deleteFromSender(String senderId);
}
