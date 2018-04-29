package com.example.ahmad.chat_3.models.requests.chats;

import com.google.gson.annotations.SerializedName;

public class SendMessageRequest {

    public SendMessageRequest(String recepientId, String messsage) {
        this.recepientId = recepientId;
        this.messsage = messsage;
    }

    public SendMessageRequest() {}

    @SerializedName("recipient_id")
    public String recepientId;

    @SerializedName("message")
    public String messsage;
}
