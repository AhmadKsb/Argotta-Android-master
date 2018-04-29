package com.example.ahmad.chat_3.models.requests;

import com.example.ahmad.chat_3.models.responses.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UsersRequestModel extends BaseResponse{

    @SerializedName("users")
    public ArrayList<Account> users;

 

    public UsersRequestModel( ArrayList<Account> users)
    {
     this.users=users;
    }

    // empty constructor for gson
    public UsersRequestModel(){}

}
