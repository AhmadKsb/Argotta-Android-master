package com.example.ahmad.chat_3.mvp.ServerRequest;

import com.example.ahmad.chat_3.models.requests.Account;

/**
 * Created by lama on 3/6/18.
 */

public class ServerRequest {

    private String operation;

    private Account user;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(Account user) {
        this.user = user;
    }



}
