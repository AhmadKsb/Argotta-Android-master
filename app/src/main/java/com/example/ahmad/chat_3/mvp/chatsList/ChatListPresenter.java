package com.example.ahmad.chat_3.mvp.chatsList;

import com.example.ahmad.chat_3.db.dao.ChatDao;
import com.example.ahmad.chat_3.models.db.Chat;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChatListPresenter extends ChatsListContract.Presenter {

    private ChatDao chatDao;

    public ChatListPresenter(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    @Override
    public void getChats() {
        Single.defer(() -> Single.just(chatDao.getAll()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chats -> {
                    for (Chat c : chats) {
                        System.out.println(c.getUserName());
                    }
                    if (isViewAttached()) {
                        getView().displayChats(chats);
                    }
                }, e -> {
                    if (isViewAttached()) {
                        getView().displayError(e.getMessage());
                    }
                });
    }
}
