package com.example.ahmad.androidroomdatabase.Database;

import com.example.ahmad.chat_3.db.dao.ChatDao;
import com.example.ahmad.chat_3.models.db.Chat;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Ahmad on 4/11/2018.
 */

public class UserRepository implements ChatDao {

    private ChatDao mLocalDataSource;

    private static UserRepository mInstance;

    public UserRepository(ChatDao mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static UserRepository getInstance(ChatDao mLocalDataSource){
        if(mInstance == null){
            mInstance = new UserRepository(mLocalDataSource);
        }
        return mInstance;
    }


    @Override
    public List<Chat> getAll() {
        return mLocalDataSource.getAll();
    }

    @Override
    public void insertAll(List<Chat> chats) {
        mLocalDataSource.insertAll(chats);
    }

    @Override
    public void deleteFromSender(String senderId) {
        mLocalDataSource.deleteFromSender(senderId);
    }
}
