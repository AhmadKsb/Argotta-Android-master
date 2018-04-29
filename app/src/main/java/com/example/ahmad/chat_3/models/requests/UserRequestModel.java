package com.example.ahmad.chat_3.models.requests;

import com.google.gson.annotations.SerializedName;

public class UserRequestModel {

    @SerializedName("id")
    public String userID;


    @SerializedName("user_name")
    String user_name;

    public UserRequestModel(String userID, String user_name)
    {
       this.userID=userID;
       this.user_name=user_name;
    }

    // empty constructor for gson
    public UserRequestModel(){}

}
