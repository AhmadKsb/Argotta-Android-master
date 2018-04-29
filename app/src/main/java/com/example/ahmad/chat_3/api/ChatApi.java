package com.example.ahmad.chat_3.api;

import com.example.ahmad.chat_3.models.requests.chats.SendMessageRequest;
import com.example.ahmad.chat_3.models.responses.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatApi {

    @POST("Chat/sendMessage")
    Call<BaseResponse> sendMessage(@Body SendMessageRequest messageRequest);
}
