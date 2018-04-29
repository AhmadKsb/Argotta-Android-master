package com.example.ahmad.chat_3.mvp.users;

import com.example.ahmad.chat_3.models.requests.Account;
import com.example.ahmad.chat_3.mvp.BasePresenter;

import java.util.List;

public class UserContract {

    public interface View {
        void displayUsers(List<Account> users);
        void showError(String error);
    }

    public static abstract class Presenter extends BasePresenter<UserContract.View> {
        public abstract void getUsers();
        public abstract void createChat(Account account);
    }
}
