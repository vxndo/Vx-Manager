package vxndo.manager.model;

import android.content.*;
import android.graphics.drawable.*;
import android.text.format.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import vxndo.manager.*;

import android.text.format.Formatter;

public class FileItem
implements PopupMenu.OnMenuItemClickListener {

	private File mFile;
	private Context mContext;
	private String mInfo;
	private Drawable mIcon;

	public FileItem(Context context, File file) {
		mContext = context;
		mFile = file;
		if (mFile.isDirectory()) {
			mIcon = mContext.getDrawable(R.drawable.ic_folder);
			int count = mFile.list().length;
			if (count == 0) mInfo = "Empty";
			else mInfo = String.format("Files: %d", count);
		} else {
			mIcon = mContext.getDrawable(R.drawable.ic_file);
			String length = Formatter.formatFileSize(mContext, mFile.length());
			mInfo = String.format("Size: %s", length);
		}
	}

	public File getFile() {
		return mFile;
	}

	public Drawable getIcon() {
		return mIcon;
	}

	public String getName() {
		return mFile.getName();
	}

	public String getInfo() {
		return mInfo;
	}

	public static ArrayList<FileItem> listFiles(Context context, File src, boolean addAll) {
		ArrayList<FileItem> files = new ArrayList<>();
		ArrayList<FileItem> dirs = new ArrayList<>();
		for (File file: src.listFiles()) {
			if (file.isDirectory()) {
				dirs.add(new FileItem(context, file));
				if (addAll) dirs.addAll(listFiles(context, file, true));
			} else {
				files.add(new FileItem(context, file));
			}
		} files.sort(FileItem.getAZComparator());
		dirs.sort(FileItem.getAZComparator());
		dirs.addAll(files);
		return dirs;
	}

	public void getMorePopup(ImageView more) {
		more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View p1) {
				PopupMenu menu = new PopupMenu(mContext, p1);
				menu.inflate(R.menu.file_list_adapter_more_menu);
				menu.setOnMenuItemClickListener(FileItem.this);
				menu.show();
			}
		});
	}

	@Override
	public boolean onMenuItemClick(MenuItem p1) {
		
		return false;
	}

	public static Comparator<FileItem> getAZComparator() {
		return new Comparator<FileItem>() {
			@Override
			public int compare(FileItem p1, FileItem p2) {
				return p1.getName().compareToIgnoreCase(p2.getName());
			}
		};
	}
}
