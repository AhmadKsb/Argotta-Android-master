package com.example.ahmad.chat_3.mvp.login;

import com.example.ahmad.chat_3.mvp.BasePresenter;
import com.example.ahmad.chat_3.mvp.registration.RegistrationContract;

/**
 * Created by Ahmad on 2/21/2018.
 */


public class LoginContract {
    public interface View {
        void onLoginSuccess();
        void showError(String error);
    }

    public static abstract class Presenter extends BasePresenter<LoginContract.View> {
        public abstract void login(String username, String password);
    }
}
