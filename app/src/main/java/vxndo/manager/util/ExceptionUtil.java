package vxndo.manager.util;

import android.app.*;
import android.content.*;
import android.widget.*;
import java.io.*;

public class ExceptionUtil {

	public static void showInDialog(final Context context, final Exception e) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("Error");
		StringWriter w = new StringWriter();
		e.printStackTrace(new PrintWriter(w));
		dialog.setMessage(w.toString());
		dialog.setPositiveButton("copy", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface p1, int p2) {
				ClipboardManager cm = context.getSystemService(ClipboardManager.class);
				ClipData cp = ClipData.newPlainText("", e.toString());
				cm.setPrimaryClip(cp);
				Toast.makeText(context, "Copied!", 0).show();
			}
		});
		dialog.setNegativeButton("hide", null);
		dialog.show();
	}
}
