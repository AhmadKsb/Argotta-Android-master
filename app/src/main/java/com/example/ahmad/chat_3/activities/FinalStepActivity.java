package com.example.ahmad.chat_3.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmad.chat_3.models.requests.ProfileInfoRequestModel;
import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.example.ahmad.chat_3.R;
import com.example.ahmad.chat_3.mvp.profileInfo.ProfileInfoContract;
import com.example.ahmad.chat_3.mvp.profileInfo.ProfileInfoPresenter;
import com.example.ahmad.chat_3.mvp.registration.RegistrationContract;
import com.example.ahmad.chat_3.mvp.registration.RegistrationPresenter;

//mine
public class FinalStepActivity extends BaseActivity implements RegistrationContract.View,ProfileInfoContract.View {

    EditText checkUsername, checkPassword, checkEmail;
    private Button register;
    private CheckBox checkbox1;
    private TextView privacy;


    private ProfileInfoContract.Presenter presenterProfile;
    Context context=FinalStepActivity.this;
    Intent intent;



    private RegistrationContract.Presenter presenter;

    String username;
    String emaill;

    ProfileInfoRequestModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_step);

        presenter = new RegistrationPresenter(new ApiConstructor(this), getPrefs());
        presenter.attachView(this);

        presenterProfile = new ProfileInfoPresenter(new ApiConstructor(this),getPrefs());
        presenterProfile.attachView(this);

        checkUsername = findViewById(R.id.etUsername);
        checkPassword = findViewById(R.id.etPassword);
        checkEmail = findViewById(R.id.etEmail);
        register = findViewById(R.id.bRegister);
        checkbox1 = findViewById(R.id.privacypolicy);
        privacy = findViewById(R.id.tvPrivacy);

        context=FinalStepActivity.this;

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW",
                        Uri.parse("https://www.freeprivacypolicy.com/free-privacy-policy.html"));
                startActivity(viewIntent);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = checkUsername.getText().toString();
                String password = checkPassword.getText().toString();
                String emaill = checkEmail.getText().toString();

                if(!isEmailValid(emaill) && username.matches("") && password.matches("")){  //all fields are empty.
                    showError("Please fill all the fields.");
                    return;
                }
                if(isEmailValid(emaill) && username.matches("") && password.matches("")){   //user and pass are empty.
                    showError("Username and password fields can not be empty.");
                }
                if(isEmailValid(emaill) && !username.matches("")
                        && username.matches("[a-zA-Z]+") && password.matches("")){  //pass is empty.
                    showError("Password field can not be empty.");
                    return;
                }
                if(isEmailValid(emaill) && !username.matches("") && !username.matches("[a-zA-Z0-9]+")
                        && !password.matches("")){
                    showError("Username should be a-Z.");
                    return;
                }
                if(!isEmailValid(emaill) && !username.matches("") && username.matches("[a-zA-Z0-9]+")
                        && !password.matches("")){
                    showError("Please enter a valid email.");
                    return;
                }
                if(!isEmailValid(emaill) && !username.matches("") && username.matches("[a-zA-Z0-9]+")
                        && password.matches("")){
                    showError("Please enter a valid email and fill the password field.");
                    return;
                }

                if(isEmailValid(emaill) && username.matches("")
                        && !password.matches("")){
                    showError("Please provide us with a username.");
                    return;
                }
                if (!isEmailValid(emaill) && username.matches("") && !password.matches("")) {
                    showError("Please provide us with your email and username.");
                    return;
                }
                if (isEmailValid(emaill) && !username.matches("")
                        && username.matches("[a-zA-Z]+") && !password.matches("") && !checkbox1.isChecked()) {
                    showError("Please check the privacy policy box..");
                    return;
                }
                if(checkbox1.isChecked() && isEmailValid(emaill) && !username.matches("")
                        && username.matches("[a-zA-Z]+") && password.matches(""))
                {

                    System.out.println(1);


                }
                onRequestStart();
                presenter.register(username, password); // <--------------

            }
        });
    }

    @Override
    public void onRegistrationSuccess() {
        onRequestEnd();
        intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) throw new RuntimeException("No info from previous activity");
        bundle.putString("username", username);
        bundle.putString("email", emaill);
        intent.putExtras(bundle);
        ProfileInfoRequestModel model = new ProfileInfoRequestModel();
        model.full_name=bundle.getString("full_name");
        model.display_name=bundle.getString("username");
        model.country=bundle.getString("country");
        model.language=bundle.getString("language");
        System.out.println("Final Step Language: " + model.language);
        presenterProfile.setProfileInfo(model);
        //startActivity(intent);

    }

    @Override
    public void onSuccess()
    {
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String error) {
        onRequestEnd();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}



