package com.example.ahmad.chat_3.mvp.ServerRequest;

import com.example.ahmad.chat_3.models.requests.Account;

/**
 * Created by lama on 3/6/18.
 */

public class ServerResponse {

    private String result;
    private String message;
    private Account user;
    private String succeeded;


    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public Account getUser() {
        return user;
    }

}
