package com.example.ahmad.chat_3.mvp;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.ahmad.chat_3.api.AccountApi;
import com.example.ahmad.chat_3.api.ChatApi;
import com.example.ahmad.chat_3.mvp.ServerRequest.Constants;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lama on 3/7/18.
 */

public class ApiConstructor {

    private Context context;

    public  ApiConstructor(Context context) {
        this.context=context;
    }

    public AccountApi getAccountApi() {
        return getClient().create(AccountApi.class);
    }

    public AccountApi getAuthAccountApi() {
        return getAuthClient().create(AccountApi.class);
    }

    public AccountApi getProfileIntoApi()
    {
        return getAuthClient().create(AccountApi.class);
    }




    public ChatApi getChatApi() {
        return getAuthClient().create(ChatApi.class);
    }

    private Retrofit getClient() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL).build();
    }

    private Retrofit getAuthClient() {
        OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {

                    SharedPreferences sharedPreferences = context.getSharedPreferences("prefs",0);
                    String token = sharedPreferences.getString("token",null);
                    System.out.println("TOKEN: "+token);
                    token = token.substring(1, token.length()-1);
                    System.out.println("TOKEN: "+token);
                    if(token == null)
                    {
                        throw new RuntimeException("Token is null");
                    }

                    Request authorisedRequest = chain.request().newBuilder()
                            .addHeader("Authorization", token).build();
                    return chain.proceed(authorisedRequest);
                }).build();


        return new Retrofit.Builder()
                .client(defaultHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build();
    }


}
