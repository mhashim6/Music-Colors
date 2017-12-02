package mhashim6.android.musiccolors;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

import eu.chainfire.libsuperuser.Shell;


public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MusicColorsUI";

	private static final String CHMOD_755 = "chmod 755";
	private static final String CHMOD_664 = "chmod 664";

	public static final String PREFS_FOLDER = " /data/data/mhashim6.android.musiccolors/shared_prefs\n";
	public static final String PREFS_FILE = " /data/data/mhashim6.android.musiccolors/shared_prefs/colors_prefs.xml\n";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
		setSupportActionBar(toolbar);

		getFragmentManager().beginTransaction().replace(R.id.mainActivity, new ColorPickerFragment()).commit();

		checkSu();
	}

	@Override
	protected void onPause() {
		super.onPause();
		fixPermissions();
	}

	private void checkSu() {
		Toast.makeText(this, R.string.root_check, Toast.LENGTH_SHORT).show();

		AsyncTask.execute(() -> {
			if (!Shell.SU.available())
				new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(MainActivity.this,
						R.string.root_failure, Toast.LENGTH_LONG).show());
		});
	}

	private void fixPermissions() {
		AsyncTask.execute(() -> {
			File pkgFolder = new File(getApplicationInfo().dataDir);
			if (pkgFolder.exists()) {
				pkgFolder.setExecutable(true, false);
				pkgFolder.setReadable(true, false);
			}
			// cache dir
			File cacheFolder = getCacheDir();
			if (cacheFolder.exists()) {
				cacheFolder.setExecutable(true, false);
				cacheFolder.setReadable(true, false);
			}
			// files dir
			File filesFolder = getFilesDir();
			if (filesFolder.exists()) {
				filesFolder.setExecutable(true, false);
				filesFolder.setReadable(true, false);
				for (File f : filesFolder.listFiles()) {
					f.setExecutable(true, false);
					f.setReadable(true, false);
				}
			}

			sudoFixPermissions();
			Log.d(TAG, "Saved Preferences Successfully.");
		});
	}

	private void sudoFixPermissions() {
		Shell.SU.run(new String[]{CHMOD_755 + PREFS_FOLDER, CHMOD_664 + PREFS_FILE});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);

		MenuItem github = menu.findItem(R.id.github_item);
		github.setOnMenuItemClickListener(item -> {
			openWebPage("https://github.com/mhashim6");
			return true;
		});

		MenuItem donate = menu.findItem(R.id.donate_item);
		donate.setOnMenuItemClickListener(item -> {
			openWebPage("https://www.paypal.me/mhashim6");
			return true;
		});

		MenuItem credits = menu.findItem(R.id.credits_item);
		credits.setOnMenuItemClickListener(item -> {
			openWebPage("https://github.com/mhashim6/Music-Colors/blob/master/Credits.md");
			return true;
		});

		return super.onCreateOptionsMenu(menu);
	}

	/*private void resetColors() {
		SharedPreferences preferences = getSharedPreferences("colors_prefs", MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		editor.clear().commit();
		recreate();
	}*/

	public final void openWebPage(String url) {
		Intent googleSearchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(googleSearchIntent);
	}
}
