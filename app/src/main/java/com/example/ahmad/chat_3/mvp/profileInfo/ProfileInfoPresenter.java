package com.example.ahmad.chat_3.mvp.profileInfo;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.example.ahmad.chat_3.api.AccountApi;
import com.example.ahmad.chat_3.models.requests.ProfileInfoRequestModel;
import com.example.ahmad.chat_3.models.requests.RegistrationRequestModel;
import com.example.ahmad.chat_3.models.responses.TokenResponse;
import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileInfoPresenter extends ProfileInfoContract.Presenter {

    private AccountApi api;
    private SharedPreferences pref;


    public ProfileInfoPresenter(ApiConstructor apiConstructor, SharedPreferences preferences) {
        this.api = apiConstructor.getProfileIntoApi();
        this.pref=preferences;
    }
    @Override
    public void setProfileInfo(ProfileInfoRequestModel model) {

        Call<TokenResponse> response = api.updateInfo(model);
        System.out.println("Model: " + new Gson().toJson(model));

        response.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    TokenResponse tokenResponse = response.body();
                    System.out.println("Response: " + new Gson().toJson(tokenResponse));
                    if (tokenResponse.succeeded) {
                        // save token to shared prefs
                        pref.edit().putString("token", new Gson().toJson(tokenResponse.token)).apply();

                        if (isViewAttached()) {
                            System.out.println("SUCESS");
                            getView().onSuccess();
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
                getView().showError("An error has occured.");
            }
        });
    }
}