package vxndo.manager.util;

import android.app.*;
import android.content.*;
import android.os.*;
import java.io.*;
import java.nio.file.*;

public class LibUtil {

	public static final String CFR_URL = "https://github.com/Vxndo7z/Vx-Manager/blob/main/app/libs/cfr.dex?raw=true";
	public static final String APKSIG_URL = "https://github.com/Vxndo7z/Vx-Manager/blob/main/app/libs/apksig.dex?raw=true";

	public static void decompile(final Context context, final String jarPath) {
		final File cfr = new File(context.getFilesDir(), "cfr.dex");
		final ProgressTask task = new ProgressTask(context, cfr, CFR_URL);
		task.setOnExecuteListener(new ProgressTask.OnExecuteListener() {
			@Override
			public void onExecute() {
				String path = cfr.getPath();
				String className = "org.benf.cfr.reader.Cfr";
				String methodName = "decompile";
				Object[] params = {jarPath};
				try {
					ClassLoaderUtil.loadDex(context, path, className, methodName, params);
				} catch (Exception e) {
					task.getException(e);
				}
			}
		});
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("Decompile");
		dialog.setMessage(jarPath);
		dialog.setPositiveButton("yes", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface p1, int p2) {
				final ProgressDialog pd = new ProgressDialog(context);
				pd.setCancelable(false);
				pd.setButton(pd.BUTTON_NEGATIVE, "cancel", new ProgressDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface p1, int p2) {
						pd.dismiss();
						task.cancel(true);
					}
				});
				pd.show();
				task.execute(pd);
			}
		});
		dialog.setNegativeButton("cancel", null);
		dialog.show();
	}

	public static void signApk(final Context context, final File input) {
		final File apksig = new File(context.getFilesDir(), "apksig.dex");
		final ProgressTask task = new ProgressTask(context, apksig, APKSIG_URL);
		task.setOnExecuteListener(new ProgressTask.OnExecuteListener() {
			@Override
			public void onExecute() {
				String path = apksig.getPath();
				String className = "vxndo.lib.apksig.ApkSigner";
				String methodName = "sign";
				try {
					InputStream pk8In = context.getAssets().open("keys/testkey.pk8");
					InputStream x509In = context.getAssets().open("keys/testkey.x509.pem");
					File pk8 = new File(context.getFilesDir(), "keys/testkey.pk8");
					File x509 = new File(context.getFilesDir(), "keys/testkey.x509.pem");
					pk8.getParentFile().mkdirs();
					if (!pk8.exists()) Files.copy(pk8In, pk8.toPath());
					if (!x509.exists()) Files.copy(x509In, x509.toPath());
					Object[] params = {input.getPath(), pk8.getPath(), x509.getPath()};
					ClassLoaderUtil.loadDex(context, path, className, methodName, params);
				} catch (Exception e) {
					task.getException(e);
				}
			}
		});
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("Sign");
		dialog.setMessage(input.getPath());
		dialog.setPositiveButton("yes", new AlertDialog.OnClickListener() {
				@Override
				public void onClick(DialogInterface p1, int p2) {
					final ProgressDialog pd = new ProgressDialog(context);
					pd.setCancelable(false);
					pd.setButton(pd.BUTTON_NEGATIVE, "cancel", new ProgressDialog.OnClickListener() {
							@Override
							public void onClick(DialogInterface p1, int p2) {
								pd.dismiss();
								task.cancel(true);
							}
						});
					pd.show();
					task.execute(pd);
				}
			});
		dialog.setNegativeButton("cancel", null);
		dialog.show();
	}

	public static class ProgressTask
	extends AsyncTask {
		private Exception e;
		private Context context;
		private File dex;
		private String dexUrl;
		private OnExecuteListener onExecuteListener;
		
		public ProgressTask(Context context, File dex, String dexUrl) {
			this.context = context;
			this.dex = dex;
			this.dexUrl = dexUrl;
		}
		public void setOnExecuteListener(OnExecuteListener listener) {
			onExecuteListener = listener;
		}
		@Override
		protected Object doInBackground(Object[] p1) {
			if (dex.exists()) {
				run();
			} else {
				Downloader.downloadFile(dexUrl, dex);
				run();
			} return p1[0];
		}
		private void run() {
			if (onExecuteListener != null) {
				onExecuteListener.onExecute();
			}
		}
		public void getException(Exception e) {
			this.e = e;
		}
		@Override
		public void onPostExecute(Object result) {
			if (result instanceof ProgressDialog) {
				((ProgressDialog) result).dismiss();
			} if (e != null) ExceptionUtil.showInDialog(context, e);
		}
		public static interface OnExecuteListener {
			void onExecute()
		}
	}
}
