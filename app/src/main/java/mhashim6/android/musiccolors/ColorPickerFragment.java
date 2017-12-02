package mhashim6.android.musiccolors;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Switch;

public class ColorPickerFragment extends PreferenceFragment {

	public ColorPickerFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getPreferenceManager().setSharedPreferencesName("colors_prefs");
		addPreferencesFromResource(R.xml.preferences);

		SharedPreferences preferences = getPreferenceManager().getSharedPreferences();
		Switch master = (Switch) getActivity().findViewById(R.id.master_switch);
		master.setChecked(preferences.getBoolean("master_switch_pref", false));
		master.setOnCheckedChangeListener((buttonView, isChecked) -> preferences.edit().putBoolean("master_switch_pref", isChecked).apply());
	}

}
