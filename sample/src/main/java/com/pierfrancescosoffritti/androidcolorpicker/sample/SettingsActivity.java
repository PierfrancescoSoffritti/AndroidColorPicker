package com.pierfrancescosoffritti.androidcolorpicker.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if(savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, SettingsFragment.newInstance(), "tag").commit();
    }
}
