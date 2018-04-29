package com.example.ahmad.chat_3.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.example.ahmad.chat_3.mvp.ServerRequest.Constants;
import com.example.ahmad.chat_3.mvp.profileInfo.ProfileInfoContract;
import com.example.ahmad.chat_3.R;

import java.util.List;


public class ProfileInfoActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView imageToUpload;
    TextView name,email,phoneNumber,country;
    Button chat;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imageToUpload.setImageURI(selectedImage);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_info);

        name=findViewById(R.id.fullName);
        email=findViewById(R.id.email_ET);
        chat=findViewById(R.id.chat);
        phoneNumber=findViewById(R.id.phone_ET);
        country=findViewById(R.id.country_ET);


        name.setText(getIntent().getBundleExtra("first_name ") + " " + getIntent().getBundleExtra("last_name"));
        email.setText(getIntent().getStringExtra("email"));
        phoneNumber.setText(getIntent().getStringExtra("phone_num"));
        country.setText(getIntent().getStringExtra("country"));

        imageToUpload =  findViewById(R.id.profile_image);
        imageToUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                switch (v.getId()) {
                    case R.id.profile_image:
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                        break;

                }

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileInfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
