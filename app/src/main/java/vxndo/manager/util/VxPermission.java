package vxndo.manager.util;

import android.content.*;
import java.util.*;

public class VxPermission {

	public static String[] checkNotGrantedPerms(Context context, String[] allPerms) {
		String[] perms = new String[0];
		for (int i = 0; i < allPerms.length; i++) {
			if (context.checkSelfPermission(allPerms[i]) != 0) {
				perms = Arrays.copyOf(perms, perms.length+1);
				perms[i] = allPerms[i];
			}
		} if (perms.length > 0) {
			return perms;
		} return null;
	}
}
