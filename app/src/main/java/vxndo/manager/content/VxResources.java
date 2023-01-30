package vxndo.manager.content;

import android.content.*;
import android.content.res.*;
import android.util.*;

public class VxResources {

	public static TypedValue resolveAttribute(Context context, int id) {
		Resources.Theme theme = context.getTheme();
		TypedValue value = new TypedValue();
		theme.resolveAttribute(id, value, true);
		return value;
	}
}
