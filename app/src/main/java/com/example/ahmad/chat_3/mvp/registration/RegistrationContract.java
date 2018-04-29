package com.example.ahmad.chat_3.mvp.registration;

import com.example.ahmad.chat_3.mvp.BasePresenter;

/**
 * Created by Ahmad on 2/21/2018.
 */


public class RegistrationContract {

    public interface View {
        void onRegistrationSuccess();
        void showError(String error);
    }

    public static abstract class Presenter extends BasePresenter<View> {
        public abstract void register(String username, String password);
    }

}
