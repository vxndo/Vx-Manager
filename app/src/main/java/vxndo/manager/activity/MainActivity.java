package vxndo.manager.activity;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import vxndo.manager.*;
import vxndo.manager.adapter.*;
import vxndo.manager.model.*;
import vxndo.manager.util.*;
import vxndo.manager.widget.*;
import vxndo.widget.*;

import vxndo.manager.util.FileUtils;
import vxndo.manager.widget.TextEditor;
import android.content.pm.*;

public class MainActivity
extends Activity
implements GridView.OnItemClickListener,
GridView.OnItemLongClickListener {

	private DrawerLayout drawer;
	private Toolbar toolbar;
	private GridView gridView;
	private FileListAdapter adapter;
	private ArrayList<FileItem> items = new ArrayList<>();
	private File storage = Environment.getExternalStorageDirectory();
	private File path;
	private TextView toolbarName, toolbarPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().build());
		drawer = findViewById(R.id.main_activiy_drawerlayout);
		toolbar = findViewById(R.id.main_activity_toolbar);
		toolbarName = findViewById(R.id.main_activity_toolbar_name);
		toolbarPath = findViewById(R.id.main_activity_toolbar_path);
		setupActionBar();
		if (Build.VERSION.SDK_INT >= 23) {
			String[] perms = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
			perms = PermissionUtils.checkNotGranted(this, perms);
			if (perms != null && perms.length > 0) {
				requestPermissions(perms, 0);
			} else init();
		} else init();
	}

	private void setupActionBar() {
		setActionBar(toolbar);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				drawer.toggle();
				break;
		} return true;
	}

	private void init() {
		adapter = new FileListAdapter(this, items);
		list(storage);
		gridView = findViewById(R.id.main_activity_gridview);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		gridView.setOnItemLongClickListener(this);
	}

	public void setPath(View v) {
		View content = LayoutInflater.from(this).inflate(R.layout.dialog_edittext, null, false);
		final TextEditor et = content.findViewById(R.id.dialog_edittext);
		et.setText(path.getPath());
		et.setOnErrorListener(new TextEditor.OnErrorListener() {
			@Override
			public boolean onError() {
				String text = et.getText().toString();
				File file = new File(text);
				return !file.exists();
			}
		});
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Go To");
		builder.setView(content);
		builder.setPositiveButton("GO", null);
		final AlertDialog dialog = builder.create();
		dialog.show();
		dialog.getButton(-1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View p1) {
				String text = et.getText().toString();
				File file = new File(text);
				if (file.exists()) {
					list(file);
					dialog.dismiss();
				}
			}
		});
	}

	public void list(File file) {
		path = file;
		toolbarName.setText(path.getName());
		toolbarPath.setText(path.getPath());
		ArrayList<File> list = FileUtils.list(path, FileUtils.FILE_NAME_COMPARATOR);
		items.clear();
		for (File f: list) {
			items.add(new FileItem(this, f));
		} adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
		FileItem item = items.get(p3);
		File file = item.getFile();
		if (file.isDirectory()) {
			list(file);
		} else {
			try {
				showIntents(item.getIntent());
			} catch (Exception e) {
				Intent i = item.getIntent();
				i.setType("*/*");
				showIntents(i);
			}
		}
	}

	public void showIntents(final Intent i) {
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		GridView view = new GridView(this);
		view.setPadding(10,10,10,10);
		final List<ResolveInfo> list = IntentUtils.queryIntentActivities(this, i);
		view.setAdapter(IntentUtils.getSimpleAdapter(this, list));
		view.setOnItemClickListener(new GridView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
				startActivity(IntentUtils.parse(i, list.get(p3)));
				dialog.dismiss();
			}
		});
		dialog.setView(view);
		dialog.show();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4) {
		return true;
	}

	@Override
	public void onBackPressed() {
		if (!path.equals(storage)) {
			list(path.getParentFile());
		} else super.onBackPressed();
	}
}
