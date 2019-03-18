package com.pierfrancescosoffritti.androidcolorpicker.sample;


import android.os.Bundle;

import com.pierfrancescosoffritti.androidcolorpicker.ColorPickerDialogPreference;

import androidx.preference.PreferenceFragmentCompat;


public class SettingsFragment extends PreferenceFragmentCompat {


    public SettingsFragment() {
    }

    static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

        ColorPickerDialogPreference colorPicker = (ColorPickerDialogPreference) findPreference(getString(R.string.color_picker_key));
        colorPicker.setSupportFragmentManager(getActivity().getSupportFragmentManager());
    }
}
