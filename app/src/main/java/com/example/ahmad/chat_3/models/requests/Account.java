package com.example.ahmad.chat_3.models.requests;

import com.google.gson.annotations.SerializedName;

public class Account {

    @SerializedName("id")
    public String userID;

    @SerializedName("user_name")
    public String username;
}
