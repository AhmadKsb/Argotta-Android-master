package com.example.ahmad.chat_3.mvp.chat;

import com.example.ahmad.chat_3.models.db.Message;
import com.example.ahmad.chat_3.mvp.BasePresenter;

import java.util.List;

public class ChatContract {

    public static abstract class Presenter extends BasePresenter<View> {
        public abstract void getMessages();
        public abstract void addMessage(Message message);
        public abstract void sendMessage(String message);
    }

    public interface View {
        void displayMessages(List<Message> messages);
        void displayError(String error);
    }
}
