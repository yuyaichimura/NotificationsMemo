package com.yuyaichimura.notificationsmemo.view.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.yuyaichimura.notificationsmemo.R;

/**
 * Created by yuya on 7/25/14.
 */
public class Fragment_Preferences extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);
    }
}
