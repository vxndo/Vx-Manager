package vxndo.manager.util;

import android.content.*;
import android.os.*;

public class CfrUtil {

	public static void decompile(final Context context, final String jarPath) {
		new AsyncTask() {
			@Override
			protected Object doInBackground(Object[] p1) {
				String path = "/sdcard/Download/cfr.dex";
				String className = "org.benf.cfr.reader.Cfr";
				String methodName = "decompile";
				Object[] params = {jarPath};
				try {
					ClassLoaderUtil.loadDex(context, path, className, methodName, params);
				} catch (Exception e) {
					AlertDialogUtil.makeSimple(context, e).show();
				} return true;
			}
		}.execute();
	}
}
