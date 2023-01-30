package vxndo.manager.activity;

import android.app.*;
import android.os.*;
import vxndo.manager.*;
import vxndo.manager.fragment.*;
import vxndo.manager.fragment.manager.*;
import vxndo.manager.listener.*;
import vxndo.manager.util.*;

public class MainActivity extends Activity {

	private Fragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		requestAllPerms(new String[] {
			android.Manifest.permission.WRITE_EXTERNAL_STORAGE
		});
	}

	private void requestAllPerms(String[] allPerms) {
		String[] perms = VxPermission.checkNotGrantedPerms(this, allPerms);
		if (perms != null) {
			requestPermissions(perms, 0);
		} else initFragment();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 0) initFragment();
	}

	private void initFragment() {
		VxFragmentManager manager = new VxFragmentManager(this);
		mFragment = new MainFragment();
		manager.replace(mFragment, R.id.main_activity_framelayout);
	}

	@Override
	public void onBackPressed() {
		if (!(mFragment instanceof IOnBackPressed) || !((IOnBackPressed) mFragment).onBackPressed()) {
			super.onBackPressed();
		}
	}
}
