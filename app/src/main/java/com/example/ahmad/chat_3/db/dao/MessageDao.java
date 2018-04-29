package com.example.ahmad.chat_3.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.ahmad.chat_3.models.db.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM message")
    List<Message> getAll();

    @Query("SELECT * FROM message WHERE sender_id LIKE :senderId")
    List<Message> findBySender(String senderId);

    @Insert
    void insertAll(List<Message> messages);

    @Query("DELETE FROM message WHERE sender_id LIKE :senderId")
    void deleteFromSender(String senderId);

    @Query("DELETE FROM message")
    void deleteAll();
}
