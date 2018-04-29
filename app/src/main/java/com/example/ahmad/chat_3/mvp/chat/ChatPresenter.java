package com.example.ahmad.chat_3.mvp.chat;

import com.example.ahmad.chat_3.api.ChatApi;
import com.example.ahmad.chat_3.db.dao.MessageDao;
import com.example.ahmad.chat_3.models.db.Message;
import com.example.ahmad.chat_3.models.requests.chats.SendMessageRequest;
import com.example.ahmad.chat_3.models.responses.BaseResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatPresenter extends ChatContract.Presenter {

    private String senderId;
    private MessageDao messageDao;
    private ChatApi chatApi;

    public ChatPresenter(String senderId, MessageDao messageDao, ChatApi chatApi) {
        this.senderId = senderId;
        this.messageDao = messageDao;
        this.chatApi = chatApi;
    }

    @Override
    public void getMessages() {
        Single.defer(() -> Single.just(messageDao.getAll()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messages -> {

                    List<Message> filtered = new ArrayList<>();
                    for (Message m : messages) {
                        if (m.getSenderId().equals(senderId)) {
                            filtered.add(m);
                        }
                    }
                    if (isViewAttached()) {
                        getView().displayMessages(filtered);
                    }
                }, e -> {
                    if (isViewAttached()) {
                        getView().displayError(e.getMessage());
                    }
                });
    }

    @Override
    public void addMessage(Message message) {
        Completable.fromAction(() -> messageDao.insertAll(Collections.singletonList(message)))
                .subscribeOn(Schedulers.io())
                .subscribe(() -> System.out.println(89));
    }

    @Override
    public void sendMessage(String message) {
        chatApi.sendMessage(new SendMessageRequest(senderId, message))
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        System.out.println("Response Code: " + response.code());
                        if (response.isSuccessful()) {
                            System.out.println(567);
                            Message m = new Message();
                            m.setContent(message);
                            m.setFromUser(true);
                            m.setSenderId(senderId);
                            addMessage(m);
                        } else {
                            if (isViewAttached()) {
                                getView().displayError("An error has occurred");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        if (isViewAttached()) {
                            getView().displayError("An error has occurred");
                        }
                    }
                });
    }
}
