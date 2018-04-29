package com.example.ahmad.chat_3.mvp.profileInfo; /**
 * Created by Ahmad on 2/21/2018.
 */

import android.graphics.Bitmap;

import com.example.ahmad.chat_3.models.requests.ProfileInfoRequestModel;
import com.example.ahmad.chat_3.mvp.BasePresenter;
import com.example.ahmad.chat_3.mvp.registration.RegistrationContract;

import java.util.List;

public class ProfileInfoContract {
    public interface View {
        void onSuccess();
        void showError(String error);
      //  void displayAvailableLanguages(List<String> languages);
    }

    public static abstract class Presenter extends BasePresenter<ProfileInfoContract.View>{
        public abstract void setProfileInfo(ProfileInfoRequestModel model);

    }
}
