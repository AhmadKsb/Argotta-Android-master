package com.example.ahmad.chat_3.models.requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lama on 3/22/18.
 */

public class LoginRequestModel {


    @SerializedName("display_name")
    public String displayName;

    @SerializedName("pass_hash")
    public  String passhash;

    @SerializedName("firebase_token")
    public String firebaseToken;


    public LoginRequestModel(String displayName, String passhash, String firebaseToken) {
        this.displayName = displayName;
        this.passhash = passhash;
        this.firebaseToken = firebaseToken;
    }

    // empty constructor for gson
    public LoginRequestModel() {}
}
