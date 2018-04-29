package com.example.ahmad.chat_3.mvp.users;

import android.database.sqlite.SQLiteConstraintException;

import com.example.ahmad.chat_3.db.dao.ChatDao;
import com.example.ahmad.chat_3.models.db.Chat;
import com.example.ahmad.chat_3.models.requests.Account;
import com.example.ahmad.chat_3.models.requests.UsersRequestModel;
import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.example.ahmad.chat_3.api.AccountApi;

import java.util.Arrays;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;

public class UserPresenter extends  UserContract.Presenter {

    private AccountApi api;
    private ChatDao chatDao;

    public UserPresenter(ApiConstructor apiConstructor, ChatDao chatDao) {
        this.api = apiConstructor.getAuthAccountApi();
        this.chatDao = chatDao;
    }

    @Override
    public void getUsers() {
        System.out.println(5665);
        Call<UsersRequestModel> response = api.getUsers();

        response.enqueue(new Callback<UsersRequestModel>() {
            @Override
            public void onResponse(Call<UsersRequestModel> call, retrofit2.Response<UsersRequestModel> response) {
                System.out.println(5);

                if (response.isSuccessful()) {
                    System.out.println(11);
                    UsersRequestModel usersRequestModel = response.body();
                    if (usersRequestModel.succeeded) {
                        System.out.println(12);
                        System.out.println(usersRequestModel.users);
                        if (isViewAttached()) {
                            System.out.print(15);
                            getView().displayUsers(usersRequestModel.users);
                        }
                    } else {
                        System.out.println(13);
                        if (isViewAttached()) {
                            System.out.print(14);
                            System.out.print(usersRequestModel.reason);
                            getView().showError(usersRequestModel.reason);
                        }
                    }
                } else {
                    System.out.println("Resonse Code: " + response.code());
                    System.out.println(4443);
                    if (isViewAttached()) {
                        System.out.print(3);
                        getView().showError("An error has occurred");
                    }
                }
            }

            @Override
            public void onFailure(Call<UsersRequestModel> call, Throwable t) {
                System.out.println(44436565);
                if (isViewAttached()) {
                    System.out.print(4);
                    getView().showError("An error has occurred");
                }
            }
        });
    }

    @Override
    public void createChat(Account account) {
        Chat chat = new Chat();
        chat.setSenderId(account.userID);
        chat.setUserName(account.username);

        Completable.fromAction(() -> chatDao.insertAll(Arrays.asList(chat)))
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {}, e -> {
                    if (!(e instanceof SQLiteConstraintException)) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
