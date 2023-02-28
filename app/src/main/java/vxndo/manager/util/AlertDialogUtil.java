package vxndo.manager.util;

import android.app.*;
import android.content.*;

public class AlertDialogUtil {

	public static AlertDialog makeSimple(Context context, Object message) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setMessage(message.toString());
		return dialog.create();
	}
}
