package vxndo.manager.adapter;

import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import vxndo.manager.*;
import vxndo.manager.model.*;
import android.widget.Filter.*;
import vxndo.manager.adapter.FileListAdapter.*;

public class FileListAdapter
extends ArrayAdapter<FileItem> {

	private List<FileItem> mFullList;
	private List<FileItem> mList;
	private FileItemFilter mFilter;
	
	public FileListAdapter(Context context, List<FileItem> list) {
		super(context, 0, list);
		mList = list;
		mFullList = new ArrayList<FileItem>(mList);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		FileItem item = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.file_list_adapter_item, parent, false);
			holder = new ViewHolder();
			convertView.setTag(holder);
		} else holder = (ViewHolder) convertView.getTag();
		holder.icon = convertView.findViewById(R.id.file_list_adapter_item_icon);
		holder.title = convertView.findViewById(R.id.file_list_adapter_item_title);
		holder.info = convertView.findViewById(R.id.file_list_adapter_item_info);
		holder.more = convertView.findViewById(R.id.file_list_adapter_item_more);
		holder.icon.setImageDrawable(item.getIcon());
		holder.title.setText(item.getName());
		holder.info.setText(item.getInfo());
		item.getMorePopup(holder.more);
		return convertView;
	}

	@Override
	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new FileItemFilter();
		} return mFilter;
	}

	private class FileItemFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence p1) {
			FilterResults results = new FilterResults();
			String prefix = p1.toString().toLowerCase();
			if (prefix == null || prefix.length() == 0) {
				results.values = mFullList;
			} else {
				ArrayList<FileItem> tempList = new ArrayList<>();
				for (FileItem item: mFullList) {
					if (item.getName().toLowerCase().contains(prefix)) {
						tempList.add(item);
					}
				} results.values = tempList;
			} return results;
		}

		@Override
		protected void publishResults(CharSequence p1, FilterResults p2) {
			mList.clear();
			mList.addAll((List) p2.values);
			notifyDataSetChanged();
		}
	};

	private class ViewHolder {
		private ImageView icon;
		private TextView title, info;
		private ImageView more;
	}
}
