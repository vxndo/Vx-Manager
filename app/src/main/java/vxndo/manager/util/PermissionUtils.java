package vxndo.manager.util;

import android.app.*;
import android.content.pm.*;

public class PermissionUtils {

	public static String[] getAppPerms(Activity context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
			return info.requestedPermissions;
		} catch (Exception e) {
			return new String[]{};
		}
	}

	public static String[] checkNotGranted(Activity context, String... perms) {
		String[] notGrantedPerms = new String[0];
		for (int i = 0; i < perms.length; i++) {
			if (context.checkSelfPermission(perms[0]) != 0) {
				notGrantedPerms = new String[notGrantedPerms.length+1];
				notGrantedPerms[i] = perms[i];
			}
		} return notGrantedPerms;
	}
}
