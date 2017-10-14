package mhashim6.android.musiccolors;


import android.os.Bundle;
import android.preference.PreferenceFragment;

public class ColorPickerFragment extends PreferenceFragment {

	public ColorPickerFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getPreferenceManager().setSharedPreferencesName("colors_prefs");
		addPreferencesFromResource(R.xml.preferences);
	}

}
