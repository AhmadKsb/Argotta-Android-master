package com.example.ahmad.chat_3.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ahmad.chat_3.R;

public class FullNameActivity extends AppCompatActivity  {

    private EditText name, familyName;
    private Button nextBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_name);

        name = findViewById(R.id.etName);
        familyName = findViewById(R.id.etFamilyName);
        nextBt = findViewById(R.id.nextBT2);





        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // todo confusing variable naming
                // what should name and name1 be? use for example nameEditText and name (or nameStr)

                String name1 = name.getText().toString().trim();
                String familyName1 = familyName.getText().toString().trim();
                Intent myintent=new Intent(FullNameActivity.this, WelcomeActivity.class).putExtra("name", name1);

                if ((name1.matches("") && familyName1.matches(""))){
                    Toast.makeText(FullNameActivity.this,"Name and family name should be a-Z and not empty.", Toast.LENGTH_LONG).show();
                    return;
                }
                if ((name1.matches("") && !familyName1.matches(""))){
                    Toast.makeText(FullNameActivity.this,"Name should be a-Z and not empty.", Toast.LENGTH_LONG).show();
                    return;
                }
                if ((!name1.matches("") && familyName1.matches(""))){
                    Toast.makeText(FullNameActivity.this,"Family name should be a-Z and not empty.", Toast.LENGTH_LONG).show();
                    return;
                }
//                if ((name1.matches("") || !name1.matches("[a-zA-Z]+")) ||
//                        familyName1.matches("") || !familyName1.matches("[a-zA-Z]+")) {
//
//                    Toast.makeText(FullNameActivity.this,"Name and family name should be a-Z and not empty.", Toast.LENGTH_LONG).show();
//                    return;
//                }
                else {

                    Intent intent = new Intent(getApplicationContext(), CountryActivity.class);
                    Bundle bundle = getIntent().getExtras();
                    if (bundle == null) {
                        bundle = new Bundle();
                    }
                    bundle.putString("first_name", name1);
                    bundle.putString("last_name", familyName1);
                    bundle.putString("full_name",name1+" "+ familyName1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }


}
