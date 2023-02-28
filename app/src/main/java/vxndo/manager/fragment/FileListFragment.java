package vxndo.manager.fragment;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import vxndo.manager.*;
import vxndo.manager.activity.*;
import vxndo.manager.adapter.*;
import vxndo.manager.model.*;
import vxndo.manager.util.*;
import vxndo.manager.widget.*;

import vxndo.manager.util.FileUtils;

public class FileListFragment
extends Fragment
implements MainActivity.IOnBackPressed,
GridView.OnItemClickListener,
GridView.OnItemLongClickListener {

	private Toolbar toolbar;
	private GridView gridView;
	private FileListAdapter adapter;
	private ArrayList<FileItem> items = new ArrayList<>();
	private File storage = Environment.getExternalStorageDirectory();
	private File path;
	private TextView toolbarName, toolbarPath;
	private Activity context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.file_list_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		toolbar = view.findViewById(R.id.file_list_fragment_toolbar);
		toolbarName = view.findViewById(R.id.file_list_fragment_toolbar_name);
		toolbarPath = view.findViewById(R.id.file_list_fragment_toolbar_path);
		gridView = view.findViewById(R.id.file_list_fragment_gridview);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = getActivity();
		setupActionBar();
		if (Build.VERSION.SDK_INT >= 23) {
			String[] perms = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
			perms = PermissionUtils.checkNotGranted(context, perms);
			if (perms != null && perms.length > 0) {
				requestPermissions(perms, 0);
			} else init();
		} else init();
	}

	private void setupActionBar() {
		context.setActionBar(toolbar);
		context.getActionBar().setDisplayHomeAsUpEnabled(true);
		context.getActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
	}

	private void init() {
		adapter = new FileListAdapter(context, items);
		list(storage);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		gridView.setOnItemLongClickListener(this);
	}

	public void list(File file) {
		path = file;
		toolbarName.setText(path.getName());
		toolbarPath.setText(path.getPath());
		ArrayList<File> list = FileUtils.list(path, FileUtils.FILE_NAME_COMPARATOR);
		items.clear();
		for (File f: list) {
			items.add(new FileItem(context, f));
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
		final AlertDialog dialog = new AlertDialog.Builder(context).create();
		GridView view = new GridView(context);
		view.setPadding(10,10,10,10);
		final List<ResolveInfo> list = IntentUtils.queryIntentActivities(context, i);
		view.setAdapter(IntentUtils.getSimpleAdapter(context, list));
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

	public void setPath(View v) {
		View content = LayoutInflater.from(context).inflate(R.layout.dialog_edittext, null, false);
		final TextEditor et = content.findViewById(R.id.dialog_edittext);
		et.setText(path.getPath());
		et.setOnErrorListener(new TextEditor.SimpleErrorListener() {
				@Override
				public boolean getError() {
					String text = et.getText().toString();
					File file = new File(text);
					return !file.exists();
				}
			});
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
		et.setOnSubmitListener(new TextEditor.OnSubmitListener() {
				@Override
				public void onSubmit() {
					dialog.getButton(-1).performClick();
				}
			});
	}

	@Override
	public boolean onBackPressed() {
		if (!path.equals(storage)) {
			list(path.getParentFile());
			return true;
		} else return false;
	}
}
