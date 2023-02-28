package vxndo.manager.activity;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import vxndo.manager.*;
import vxndo.manager.util.*;
import vxndo.manager.widget.*;
import vxndo.widget.*;
import vxndo.manager.fragment.*;

public class MainActivity
extends Activity {

	private DrawerLayout drawer;
	private Fragment fragment;
	private Fragment[] containers = {
		new FileListFragment(),
		new MusicPlayerFragment()
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().build());
		drawer = findViewById(R.id.main_activiy_drawerlayout);
		replace(0);
		String jarPath = "/sdcard/Download/test.jar";
		String cfrUrl = "https://github.com/Vxndo7z/Files/blob/main/VxManager/cfr.dex";
		//CfrUtil.decompile(this, jarPath);
	}

	private void replace(int pos) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		fragment = containers[pos];
		ft.replace(R.id.main_activity_framelayout, fragment);
		ft.commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				drawer.toggle();
				break;
		} return true;
	}

	public void setPath(View v) {
		if (fragment instanceof FileListFragment) {
			((FileListFragment)fragment).setPath(v);
		}
	}

	@Override
	public void onBackPressed() {
		if (drawer.isDrawerOpen(Gravity.START)) {
			drawer.closeDrawer(Gravity.START);
		} else if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
			super.onBackPressed();
		}
	}

	public static interface IOnBackPressed {
		boolean onBackPressed();
	}
}
