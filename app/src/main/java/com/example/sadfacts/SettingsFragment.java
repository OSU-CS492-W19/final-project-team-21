package com.example.sadfacts;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.widget.SeekBar;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.prefs);
        PreferenceAnimals animalPref = (PreferenceAnimals)findPreference("pref_animal");
        Log.d("pref_animal", animalPref.getAnimal());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d("pref_animal", "changed pref");
        if (key.equals("pref_animal")) {
            PreferenceAnimals animalPref = (PreferenceAnimals)findPreference(key);
            Log.d("pref_animal", animalPref.getAnimal());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
}
