package com.example.ahmad.chat_3.models.responses;
import com.google.gson.annotations.SerializedName;



public class TokenResponse extends BaseResponse {

    @SerializedName("token")
    public String token;

}
