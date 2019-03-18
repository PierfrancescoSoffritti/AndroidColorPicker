package com.pierfrancescosoffritti.androidcolorpicker.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, SettingsFragment.newInstance()).commit();
    }
}
