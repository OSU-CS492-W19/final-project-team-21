package com.example.sadfacts;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.widget.SeekBar;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.prefs);
        PreferenceAnimals animalPref = (PreferenceAnimals)findPreference(getString(R.string.pref_animal));
        Log.d(getString(R.string.pref_animal), animalPref.getAnimal());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "changed pref");
        if (key.equals(getString(R.string.pref_animal))) {
            PreferenceAnimals animalPref = (PreferenceAnimals)findPreference(key);
            Log.d(TAG, "gif pref changed: " + animalPref.getAnimal());
        } else if(key.equals("sad_facts")) {
            Log.d(TAG, "sad_facts value changed!");
        } else if(key.equals("happy_facts")) {
            Log.d(TAG, "happy_facts value changed!");
        } else if(key.equals("cool_facts")) {
            Log.d(TAG, "cool_facts value changed!");
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
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
