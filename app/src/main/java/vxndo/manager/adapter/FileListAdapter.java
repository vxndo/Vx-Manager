package vxndo.manager.adapter;

import android.content.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import vxndo.manager.*;
import vxndo.manager.model.*;

public class FileListAdapter
extends ArrayAdapter<FileItem> {

	private List<FileItem> list;
	private Context context;

	public FileListAdapter(Context context, List<FileItem> list) {
		super(context, 0, list);
		this.context = context;
		this.list = list;
	}

	@Override
	public View getView(int pos, View view, ViewGroup group) {
		FileItem item = getItem(pos);
		ViewHolder holder;
		if (view == null) {
			LayoutInflater i = LayoutInflater.from(context);
			view = i.inflate(R.layout.file_list_item, group, false);
			holder = new ViewHolder();
			view.setTag(holder);
		} else holder = (ViewHolder) view.getTag();
		holder.icon = view.findViewById(R.id.file_list_item_icon);
		holder.title = view.findViewById(R.id.file_list_item_title);
		holder.subTitle = view.findViewById(R.id.file_list_item_subtitle);
		holder.info = view.findViewById(R.id.file_list_item_info);
		holder.icon.setImageDrawable(item.getDrawable());
		holder.title.setText(item.getTitle());
		holder.subTitle.setText(item.getSubtitle());
		holder.info.setText(item.getInfo());
		return view;
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@Override
			protected Filter.FilterResults performFiltering(CharSequence p1) {
				FilterResults results = new FilterResults();
				results.values = null;
				return results;
			}

			@Override
			protected void publishResults(CharSequence p1, Filter.FilterResults p2) {
				
				notifyDataSetChanged();
			}
		};
	}

	private class ViewHolder {
		public ImageView icon;
		public TextView title, subTitle, info;
	}
}
