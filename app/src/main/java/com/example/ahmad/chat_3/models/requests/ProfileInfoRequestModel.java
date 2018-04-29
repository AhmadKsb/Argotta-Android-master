package com.example.ahmad.chat_3.models.requests;

import com.google.gson.annotations.SerializedName;


public class ProfileInfoRequestModel {


    @SerializedName("display_name")
    public String display_name;

    @SerializedName("full_name")
    public String full_name;

    @SerializedName("email")
    public String email;

    @SerializedName("country")
    public String country;

    @SerializedName("pass_hash")
    public String pass_hash;

    @SerializedName("language")
    public String language;

    @SerializedName("firebase_token")
    public String token;

    // empty constructor for gson
    public ProfileInfoRequestModel() {}

}
