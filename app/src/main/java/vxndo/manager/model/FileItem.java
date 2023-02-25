package vxndo.manager.model;

import android.content.*;
import android.graphics.drawable.*;
import java.io.*;
import vxndo.manager.model.internal.*;

public class FileItem
extends FileItemInternal {

	private Drawable drawable;
	private String info, dateString;
	private Intent intent;

	public FileItem(Context context, File file) {
		super(context, file);
		drawable = getIcon();
		dateString = getDate();
		info = getFileSize();
		if (!file.isDirectory()) intent = makeIntent();
	}

	public Intent getIntent() {
		return makeIntent();
	}

	public File getFile() {
		return file;
	}

	public String getTitle() {
		return file.getName();
	}

	public String getSubtitle() {
		return dateString;
	}

	public String getInfo() {
		return info;
	}

	public Drawable getDrawable() {
		return drawable;
	}
}
