package vxndo.manager.util;

import android.content.*;
import android.content.pm.*;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import vxndo.manager.*;

public class IntentUtils {

	private Context context;

	public IntentUtils(Context context) {
		this.context = context;
	}

	public static List<ResolveInfo> queryIntentActivities(Context context, Intent intent) {
		return context.getPackageManager().queryIntentActivities(intent, 0);
	}

	public static Intent parse(Intent intent, ResolveInfo resolveInfo) {
		String pkg = resolveInfo.activityInfo.packageName;
		String clazz = resolveInfo.activityInfo.name;
		intent.setClassName(pkg, clazz);
		return intent;
	}

	public static ArrayAdapter<ResolveInfo> getSimpleAdapter(final Context context, Intent intent) {
		List<ResolveInfo> list = IntentUtils.queryIntentActivities(context, intent);
		return getSimpleAdapter(context, list);
	}

	public static ArrayAdapter<ResolveInfo> getSimpleAdapter(final Context context, List<ResolveInfo> list) {
		return new ArrayAdapter<ResolveInfo>(context, 0, list) {
			class ViewHolder {
				ImageView icon;
				TextView name;
			}
			@Override
			public View getView(int pos, View view, ViewGroup group) {
				ResolveInfo item = getItem(pos);
				ViewHolder holder;
				if (view == null) {
					view = LayoutInflater.from(context).inflate(R.layout.intent_item, group, false);
					holder = new ViewHolder();
					view.setTag(holder);
				} else holder = (ViewHolder) view.getTag();
				holder.icon = view.findViewById(R.id.intent_item_icon);
				holder.name = view.findViewById(R.id.intent_item_name);
				PackageManager pm = context.getPackageManager();
				holder.icon.setImageDrawable(item.loadIcon(pm));
				holder.name.setText(item.loadLabel(pm));
				return view;
			}
		};
	}
}
