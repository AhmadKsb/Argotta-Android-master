package com.example.ahmad.chat_3.api;

/**
 * Created by lama on 3/6/18.
 */
import com.example.ahmad.chat_3.models.requests.LoginRequestModel;
import com.example.ahmad.chat_3.models.requests.ProfileInfoRequestModel;
import com.example.ahmad.chat_3.models.requests.RegistrationRequestModel;
import com.example.ahmad.chat_3.models.requests.UsersRequestModel;
import com.example.ahmad.chat_3.models.responses.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AccountApi {

    @POST("account/register")
    Call<TokenResponse> register(@Body RegistrationRequestModel model);

    @POST("account/login")
    Call<TokenResponse> login(@Body LoginRequestModel model);


    @GET("Users")
    Call<UsersRequestModel> getUsers();

    @POST("account/update")
    Call<TokenResponse> updateInfo(@Body ProfileInfoRequestModel model);
}
