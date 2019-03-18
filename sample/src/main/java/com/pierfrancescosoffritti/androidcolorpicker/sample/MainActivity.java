package com.pierfrancescosoffritti.androidcolorpicker.sample;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.pierfrancescosoffritti.androidcolorpicker.ColorPickerDialog;
import com.pierfrancescosoffritti.androidcolorpicker.ColorPickerSwatch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.open_settings).setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.open_dialog).setOnClickListener(v -> showDialog());
    }

    private void showDialog() {
        ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title, getResources().getIntArray(R.array.colors), getResources().getColor(R.color.colorAccent), 4, ColorPickerDialog.SIZE_SMALL);
        dialog.show(getSupportFragmentManager(), "TAG");

        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                Log.d(getClass().getSimpleName(), ""+color);
            }
        });
    }
}
