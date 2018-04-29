package com.example.ahmad.chat_3.activities;

import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.Toast;

import com.example.ahmad.chat_3.R;


public class CountryActivity extends AppCompatActivity {
    private EditText countryName, phoneNb;
    private Button nextBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // todo badly formatted code
        // todo remove commented or unnecessary code

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        countryName = findViewById(R.id.etCountry);
        phoneNb = findViewById(R.id.etNB);
        nextBt = findViewById(R.id.nextBT3);


        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String country = countryName.getText().toString().trim();
                String phone = phoneNb.getText().toString().trim();

                if (country.matches("") && phone.matches("")){
                    Toast.makeText(CountryActivity.this,"Please fill both your Country and your mobile number.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!country.matches("") && phone.matches("")){
                    Toast.makeText(CountryActivity.this,"Please provide us with your mobile number.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (country.matches("") && !phone.matches("")){
                    Toast.makeText(CountryActivity.this,"Please provide us with your country name.", Toast.LENGTH_LONG).show();
                    return;
                }
//       if (!country.matches("[a-zA-Z]+") && !phone.matches("")){
//           Toast.makeText(CountryActivity.this,"Country name should be a-Z and mobile number not empty.", Toast.LENGTH_LONG).show();
//           return;
//       }
                else {
                    Intent intent = new Intent(CountryActivity.this, FinalStepActivity.class);
                    Bundle bundle = getIntent().getExtras();

                    if (bundle == null) throw new RuntimeException("No info from last activity");

                    bundle.putString("country", country);
                    bundle.putString("phone_num", phone);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });


    }
}
