package mhashim6.android.musiccolors;

import android.graphics.Color;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;

/**
 * Created by mhashim6 on 07/05/2017.
 */

public class MusicColorsModule implements IXposedHookInitPackageResources, IXposedHookZygoteInit {

	private static final String PRIMARY_KEY = "PRIMARY";
	private static final String PRIMARY_DARK_KEY = "PRIMARY_DARK";
	private static final String PRIMARY_LIGHT_KEY = "PRIMARY_LIGHT";
	private static final String PROGRESS_FILL_KEY = "PROGRESS_FILL";
	private static final String PROGRESS_BG_KEY = "PROGRESS_BG";

	private static final String PRIMARY_LIGHT_INVERSE_KEY = "PRIMARY_LIGHT_INVERSE";
	private static final String PROGRESS_FILL_INVERSE_KEY = "PROGRESS_FILL_INVERSE";
	private static final String PROGRESS_BG_INVERSE_KEY = "PROGRESS_BG_INVERSE";

	private static final String MUSIC_PACKAGE = "com.sonyericsson.music";
	private static final String PACKAGE_NAME = "mhashim6.android.musiccolors";
	private static final String COLORS_PREFS = "colors_prefs";
	private static final String COLOR_TYPE = "color";

	private static XSharedPreferences preferences;

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		preferences = new XSharedPreferences(PACKAGE_NAME, COLORS_PREFS);
		preferences.makeWorldReadable();
	}

	@Override
	public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
		if (!resparam.packageName.equals(MUSIC_PACKAGE))
			return;

		preferences.reload();

		/*White theme*/
		final int PRIMARY = preferences.getInt(PRIMARY_KEY, Color.parseColor("#5c3bb5"));
		final int PRIMARY_DARK = preferences.getInt(PRIMARY_DARK_KEY, Color.parseColor("#462d8a"));
		final int PRIMARY_LIGHT = preferences.getInt(PRIMARY_LIGHT_KEY, Color.parseColor("#7359c0"));

		final int PROGRESS_FILL = preferences.getInt(PROGRESS_FILL_KEY, Color.parseColor("#5c3bb5"));
		final int PROGRESS_BG = preferences.getInt(PROGRESS_BG_KEY, Color.parseColor("#7359c0"));

		/*Dark theme*/
		final int PRIMARY_LIGHT_INVERSE = preferences.getInt(PRIMARY_LIGHT_INVERSE_KEY, PRIMARY_LIGHT);
		final int PROGRESS_FILL_INVERSE = preferences.getInt(PROGRESS_FILL_INVERSE_KEY, Color.parseColor("#5c3bb5"));
		final int PROGRESS_BG_INVERSE = preferences.getInt(PROGRESS_BG_INVERSE_KEY, Color.parseColor("#7359c0"));

		/*Dark theme*/
		resparam.res.setReplacement(MUSIC_PACKAGE, COLOR_TYPE, "material_primary", PRIMARY); //5c3bb5
		resparam.res.setReplacement(MUSIC_PACKAGE, COLOR_TYPE, "material_primary_dark", PRIMARY_DARK); //462d8a
		resparam.res.setReplacement(MUSIC_PACKAGE, COLOR_TYPE, "material_primary_light", PRIMARY_LIGHT); //7359c0

		resparam.res.setReplacement(MUSIC_PACKAGE, COLOR_TYPE, "mini_player_progress_fill", PROGRESS_FILL); //5c3bb5
		resparam.res.setReplacement(MUSIC_PACKAGE, COLOR_TYPE, "mini_player_progress_bg", PROGRESS_BG); //7359c0

		/*White theme*/
		resparam.res.setReplacement(MUSIC_PACKAGE, COLOR_TYPE, "material_primary_light_inverse", PRIMARY_LIGHT_INVERSE); //7359c0
		resparam.res.setReplacement(MUSIC_PACKAGE, COLOR_TYPE, "mini_player_progress_bg_inverse", PROGRESS_BG_INVERSE); //4a2f9b
		resparam.res.setReplacement(MUSIC_PACKAGE, COLOR_TYPE, "mini_player_progress_fill_inverse", PROGRESS_FILL_INVERSE); //8972ce
	}

}
