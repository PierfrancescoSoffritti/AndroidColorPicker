package com.pierfrancescosoffritti.androidcolorpicker.sample;


import android.os.Bundle;

import com.pierfrancescosoffritti.androidcolorpicker.ColorPickerDialogPreference;

import androidx.preference.PreferenceFragmentCompat;


public class SettingsFragment extends PreferenceFragmentCompat {

    ColorPickerDialogPreference colorPickerDialog;

    public SettingsFragment() {
    }

    static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

        colorPickerDialog = (ColorPickerDialogPreference) findPreference(getString(R.string.color_picker_key));
        colorPickerDialog.setSupportFragmentManager(getActivity().getSupportFragmentManager());
    }

    @Override
    public void onPause() {
        super.onPause();
        colorPickerDialog.dismiss();
    }
}
