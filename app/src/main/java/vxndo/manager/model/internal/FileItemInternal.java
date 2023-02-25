package vxndo.manager.model.internal;

import android.content.*;
import android.content.pm.*;
import android.graphics.drawable.*;
import android.net.*;
import android.webkit.*;
import java.io.*;
import java.text.*;
import java.util.*;
import vxndo.manager.*;
import vxndo.manager.content.*;

public class FileItemInternal {

	protected File file;
	protected Context context;

	public FileItemInternal(Context context, File file) {
		this.context = context;
		this.file = file;
	}

	public Intent makeIntent() {
		Intent i = new Intent(Intent.ACTION_VIEW);
		Uri uri = FileProvider.getUriForFile(context, "vxndo.manager.fileprovider", file);
		i.setDataAndType(uri, getMimeType());
		i.setFlags(1|2);
		return i;
	}

	protected String getMimeType() {
		String fileName = file.getName();
		if (fileName.contains(".")) {
			int index = fileName.lastIndexOf(".");
			fileName = fileName.substring(index + 1);
			MimeTypeMap mtp = MimeTypeMap.getSingleton();
			return mtp.getMimeTypeFromExtension(fileName);
		} return "*/*";
	}

	protected String getFileSize() {
		if (file.isDirectory()) {
			return "<DIR>";
		} else {
			float size = file.length();
			String type = " B";
			if (size >= 1073741824) {
				size = size / 1073741824;
				type = " GB";
			} else if (size >= 1048576) {
				size = size / 1048576;
				type = " MB";
			} else if (size >= 1024) {
				size = size / 1024;
				type = " KB";
			} String sizeValue = new DecimalFormat("#.##").format(size);
			return sizeValue + type;
		}
	}

	protected String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Date date = new Date(file.lastModified());
		return sdf.format(date);
	}

	protected Drawable getIcon() {
		if (file.isDirectory()) {
			return context.getDrawable(R.drawable.ic_folder);
		} else {
			if (file.getName().endsWith(".apk")) {
				try {
					PackageManager pm = context.getPackageManager();
					PackageInfo pi = pm.getPackageArchiveInfo(file.getPath(), 0);
					ApplicationInfo ai = pi.applicationInfo;
					ai.publicSourceDir = file.getPath();
					ai.sourceDir = file.getPath();
					return pm.getApplicationIcon(ai);
				} catch (Exception e) {
					return context.getDrawable(R.drawable.ic_file);
				}
			} else {
				return context.getDrawable(R.drawable.ic_file);
			}
		}
	}
}
