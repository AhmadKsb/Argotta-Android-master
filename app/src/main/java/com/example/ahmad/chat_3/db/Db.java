package com.example.ahmad.chat_3.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.ahmad.chat_3.db.dao.ChatDao;
import com.example.ahmad.chat_3.db.dao.MessageDao;
import com.example.ahmad.chat_3.models.db.Chat;
import com.example.ahmad.chat_3.models.db.Message;

@Database(entities = {Chat.class, Message.class}, version = 2)
public abstract class Db extends RoomDatabase {

    public abstract ChatDao chatDao();
    public abstract MessageDao messageDao();
}
