package vxndo.manager.activity;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.widget.*;
import java.io.*;
import java.util.stream.*;
import vxndo.manager.*;
import vxndo.manager.widget.*;

public class EditorActivity
extends Activity {

	private CodeEditor editor;
	private Toolbar toolbar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor_activity);
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().build());
		editor = findViewById(R.id.editor_activity_codeeditor);
		toolbar = findViewById(R.id.editor_activity_toolbar);
		setActionBar(toolbar);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent data = getIntent();
		if (data != null) {
			Uri uri = data.getData();
			try {
				InputStream is = getContentResolver().openInputStream(uri);
				Reader r = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(r);
				String line;
				while ((line = br.readLine()) != null) {
					int length = editor.getText().length();
					line = line+"\n";
					editor.getText().insert(length, line);
				} int last = editor.getText().length()-1;
				editor.getText().delete(last, last+1);
				is.close();
			} catch (Exception e) {}
		}
	}
}
