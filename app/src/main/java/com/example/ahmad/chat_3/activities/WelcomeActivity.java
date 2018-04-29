package com.example.ahmad.chat_3.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.ahmad.chat_3.R;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    ImageView logo;
    Button startButton;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        textview=findViewById(R.id.welcome_txt);


        String fname = getIntent().getStringExtra("first_name");
        String lname = getIntent().getStringExtra("last_name");


        textview.append(String.format(" %s %s ", fname, lname));
        logo = (ImageView) findViewById(R.id.logoArgotta);
        logo.animate().rotation(720f).setDuration(2000);

        startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, ProfileInfoActivity.class);
                Bundle bundle = getIntent().getExtras();
                if (bundle == null) throw new RuntimeException("No info from previous activity");
                bundle.putString("first_name",fname);
                startActivity(intent);
                finish();
            }
        });


    }
}
