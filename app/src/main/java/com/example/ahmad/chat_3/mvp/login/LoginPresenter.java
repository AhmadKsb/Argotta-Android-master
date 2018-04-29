package com.example.ahmad.chat_3.mvp.login;

import android.content.SharedPreferences;

import com.example.ahmad.chat_3.firebase.MyFirebaseInstanceIDService;
import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.example.ahmad.chat_3.models.requests.LoginRequestModel;

import com.example.ahmad.chat_3.models.responses.TokenResponse;
import com.example.ahmad.chat_3.api.AccountApi;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.security.MessageDigest;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by lama on 3/22/18.
 */

public class LoginPresenter extends LoginContract.Presenter{


    private AccountApi api;
    private SharedPreferences pref;
    public LoginPresenter(ApiConstructor apiConstructor,SharedPreferences preferences) {
        this.api = apiConstructor.getAccountApi();
        this.pref=preferences;

    }
    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void login(String username, String password) {
        String hash=sha256(password);
        LoginRequestModel model = new LoginRequestModel(username, hash, FirebaseInstanceId.getInstance().getToken());
        Call<TokenResponse> response = api.login(model);

        response.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    TokenResponse tokenResponse = response.body();
                    System.out.println("Response: " + new Gson().toJson(tokenResponse));
                    if (tokenResponse.succeeded) {
                        System.out.println(1);
                        // save token to shared prefs

                        pref.edit().putString("token", tokenResponse.token).apply();

                        if (isViewAttached()) {
                            System.out.println(2);
                            pref.edit().putBoolean("loggedIn", true).apply();
                            pref.edit().putString("token", new Gson().toJson(tokenResponse.token)).apply();
                            getView().onLoginSuccess();
                        }
                    } else {
                        if (isViewAttached()) {
                            getView().showError(tokenResponse.reason);
                        }
                    }
                } else {
                    System.out.println(response.code());
                    if (isViewAttached()) {
                        getView().showError("An error has occurred");
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                if (isViewAttached()) {
                    getView().showError("An error has occurred");
                }
            }
        });
    }
}