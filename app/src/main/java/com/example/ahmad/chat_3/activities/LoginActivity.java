package com.example.ahmad.chat_3.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.example.ahmad.chat_3.firebase.MyFirebaseInstanceIDService;
import com.example.ahmad.chat_3.firebase.MyFirebaseMessagingService;
import com.example.ahmad.chat_3.models.requests.ProfileInfoRequestModel;
import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.example.ahmad.chat_3.generalUtils.GeneralUtils;
import com.example.ahmad.chat_3.R;

import com.example.ahmad.chat_3.mvp.login.LoginContract;
import com.example.ahmad.chat_3.mvp.login.LoginPresenter;
import com.example.ahmad.chat_3.mvp.profileInfo.ProfileInfoContract;
import com.example.ahmad.chat_3.mvp.profileInfo.ProfileInfoPresenter;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends BaseActivity implements LoginContract.View , ProfileInfoContract.View{

     Button login;
     EditText username,password;
     TextView register,forgotPass;
     SharedPreferences pref;
    Context context=LoginActivity.this;
    private LoginContract.Presenter presenter;
    private ProfileInfoContract.Presenter presenterInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        pref=getSharedPreferences("prefs", 0);
        login = findViewById(R.id.loginBT);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);

        register = findViewById(R.id.registerLink);
        forgotPass = findViewById(R.id.forgotPassLink);

        presenter = new LoginPresenter(new ApiConstructor(this), getPrefs());
        presenter.attachView(LoginActivity.this);



        presenterInfo=new ProfileInfoPresenter(new ApiConstructor(this),getPrefs());
        presenterInfo.attachView(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                String pass=password.getText().toString();


               if(GeneralUtils.validate(name,pass).equals(GeneralUtils.validResult.valid))
               {
                   onRequestStart();
                   presenter.login(name,pass);

               }
                // if the email is un-valid
               else if(GeneralUtils.validate(username.getText().toString(),password.getText().toString()).equals(GeneralUtils.validResult.unvalidUsername))
                {
                    username.setError("Un valid username");
                }
                // if the password is unvalid
                else if(GeneralUtils.validate(username.getText().toString(),password.getText().toString()).equals(GeneralUtils.validResult.emptyPassword))
                {

                    username.setError("Empty password or empty email");
                }
                else
                {
                    username.setError("Account not found");
                }
            }
        });
                register.setOnClickListener(v -> {
                    Intent registerIntent = new Intent(LoginActivity.this,FullNameActivity.class);
                       registerIntent.putExtra("Action","Register");
                        Bundle bundle = getIntent().getExtras();
                        if (bundle == null) throw new RuntimeException("Info is null");
                        bundle.putString("Action","Register");
                        registerIntent.putExtras(bundle);
                        startActivity(registerIntent);
        });
                forgotPass.setOnClickListener(v -> {
                        Intent forgotIntent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                        Bundle bundle = getIntent().getExtras();
                        if (bundle == null) throw new RuntimeException("Info is null");
                        forgotIntent.putExtras(bundle);
                        startActivity(forgotIntent);
        });
    }

    @Override
    public void onLoginSuccess() {
        ProfileInfoRequestModel model = new ProfileInfoRequestModel();
        model.token = FirebaseInstanceId.getInstance().getToken();
        System.out.println("Token: " + model.token);
        presenterInfo.setProfileInfo(model);
    }

    @Override
    public void onSuccess() {
        onRequestEnd();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(String error) {
        onRequestEnd();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

}
