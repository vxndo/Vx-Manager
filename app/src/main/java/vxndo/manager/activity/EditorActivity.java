package vxndo.manager.activity;

import android.app.*;
import android.os.*;
import vxndo.manager.*;

public class EditorActivity
extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor_activity);
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().build());
		
	}
}
