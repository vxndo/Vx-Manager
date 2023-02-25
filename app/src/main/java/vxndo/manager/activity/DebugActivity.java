package vxndo.manager.activity;

import android.app.*;
import android.content.*;
import android.os.*;
import java.io.*;

public class DebugActivity
extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if (intent != null){
			new AlertDialog.Builder(this).
			setTitle("An error occured").
			setMessage(intent.getStringExtra("error")).
			setNeutralButton("End Application", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			}).show();
		}
	}
}
