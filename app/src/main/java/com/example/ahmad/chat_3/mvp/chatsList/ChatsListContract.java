package com.example.ahmad.chat_3.mvp.chatsList;

import com.example.ahmad.chat_3.models.db.Chat;
import com.example.ahmad.chat_3.mvp.BasePresenter;

import java.util.List;

public class ChatsListContract {

    public static abstract class Presenter extends BasePresenter<View> {
        public abstract void getChats();
    }

    public interface View {
        void displayChats(List<Chat> chats);
        void displayError(String error);
    }
}
