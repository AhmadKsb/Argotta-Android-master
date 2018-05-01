package com.example.ahmad.chat_3.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.ahmad.chat_3.R;
import com.example.ahmad.chat_3.models.requests.ProfileInfoRequestModel;
import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.example.ahmad.chat_3.mvp.profileInfo.ProfileInfoPresenter;

import java.util.Locale;

public class SettingsLanguages extends AppCompatActivity {
    String[] languages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);
        languages = getResources().getStringArray(R.array.all_languages);
        final ListAdapter aadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, languages);
        final ListView aadapterListView = findViewById(R.id.languages);

        aadapterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 26) {
                    setLocale("fr");
                    recreate();
                }

                Intent intent = new Intent(SettingsLanguages.this, MainActivity.class);
                Bundle bundle = getIntent().getExtras();
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putString("language",languages[position]);
                System.out.println("Bundle Lang: " + bundle.get("language"));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }


        });

        aadapterListView.setAdapter(aadapter);

    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_lang",lang);
        editor.apply();
        Intent intent = new Intent(SettingsLanguages.this, MainActivity.class);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) bundle = new Bundle();
        bundle.putString("language",lang);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_lang","");
        setLocale(language);

    }

}
