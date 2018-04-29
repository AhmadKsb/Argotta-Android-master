package com.example.ahmad.chat_3.mvp.registration;

import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.example.ahmad.chat_3.models.requests.RegistrationRequestModel;
import com.example.ahmad.chat_3.models.responses.TokenResponse;
import com.example.ahmad.chat_3.api.AccountApi;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import android.content.SharedPreferences;

import java.security.MessageDigest;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by lama on 2/22/18.
 */

public class RegistrationPresenter extends RegistrationContract.Presenter
{

    private AccountApi api;
    private SharedPreferences pref;

    public RegistrationPresenter(ApiConstructor apiConstructor, SharedPreferences preferences) {
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
    public void register(String username, String password) {
        System.out.println(2);
        String hash = sha256(password);
        RegistrationRequestModel model = new RegistrationRequestModel(username, hash, FirebaseInstanceId.getInstance().getToken());
        Call<TokenResponse> response = api.register(model);

        response.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                System.out.println(3);
                if (response.isSuccessful()) {
                    System.out.println(4);
                    TokenResponse tokenResponse = response.body();
                    System.out.println("Response: " + new Gson().toJson(tokenResponse));
                    if (tokenResponse.succeeded) {
                        System.out.println(41);
                        // save token to shared prefs
                        pref.edit().putBoolean("loggedIn", true).apply();
                        pref.edit().putString("token", new Gson().toJson(tokenResponse.token)).apply();

                        if (isViewAttached()) {
                            System.out.println(411);
                            getView().onRegistrationSuccess();
                        }
                    } else {
                        System.out.println(42);
                        if (isViewAttached()) {
                            System.out.println(421);
                            getView().showError(tokenResponse.reason);
                        }
                    }
                } else {
                    System.out.println(5);
                    System.out.println(response.code());
                    if (isViewAttached()) {
                        System.out.println(6);
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
