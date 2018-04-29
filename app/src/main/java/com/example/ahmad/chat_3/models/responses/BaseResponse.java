package com.example.ahmad.chat_3.models.responses;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lama on 3/7/18.
 */

public class BaseResponse {

    @SerializedName("succeeded")
    public  boolean succeeded;

    @SerializedName("reason")
    public String reason;
}
