package com.pierfrancescosoffritti.androidcolorpicker.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title, getResources().getIntArray(R.array.colors), getResources().getColor(R.color.colorAccent), 4, ColorPickerDialog.SIZE_SMALL);
//        dialog.show(getFragmentManager(), "TAG");
//
//        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
//            @Override
//            public void onColorSelected(int color) {
//                Log.d(getClass().getSimpleName(), ""+color);
//            }
//        });

        findViewById(R.id.open_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
