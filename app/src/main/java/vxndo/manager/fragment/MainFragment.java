package vxndo.manager.fragment;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import vxndo.manager.*;
import vxndo.manager.adapter.*;
import vxndo.manager.listener.*;
import vxndo.manager.model.*;

public class MainFragment
extends Fragment
implements IOnBackPressed,
GridView.OnItemClickListener {

	private GridView mGridView;
	private FileListAdapter mAdapter;
	private ArrayList<FileItem> mFileList;
	private File mFile, sdcard;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.main_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mGridView = view.findViewById(R.id.main_fragment_gridview);
		sdcard = Environment.getExternalStorageDirectory();
		mFile = sdcard;
		mFileList = FileItem.listFiles(getContext(), mFile, false);
		mAdapter = new FileListAdapter(getContext(), mFileList);
		mGridView.setOnItemClickListener(this);
		mGridView.setAdapter(mAdapter);
		
	}

	@Override
	public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
		File tempFile = mFileList.get(p3).getFile();
		if (tempFile.isDirectory())
			mFile = tempFile;
			refresh();
	}

	private void refresh() {
		mFileList.clear();
		mFileList.addAll(FileItem.listFiles(getContext(), mFile, false));
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main_fragment_menu, menu);
		SearchView search = (SearchView) menu.findItem(R.id.main_fragment_menu_search).getActionView();
		search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String p1) {
					// TODO: Implement this method
					return false;
				}

				@Override
				public boolean onQueryTextChange(String p1) {
					mAdapter.getFilter().filter(p1);
					return false;
				}
			});
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onBackPressed() {
		if (mFile.equals(sdcard)) return false;
		else {
			mFile = mFile.getParentFile();
			refresh();
			return true;
		}
	}
}
