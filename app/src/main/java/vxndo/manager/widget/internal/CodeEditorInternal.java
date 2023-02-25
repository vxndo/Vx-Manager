package vxndo.manager.widget.internal;

import android.content.*;
import android.util.*;
import android.widget.*;

public class CodeEditorInternal
extends FrameLayout {

	private FrameLayout frame;
	private EditText editor;

	public CodeEditorInternal(Context context) {
		this(context, null);
	}

	public CodeEditorInternal(Context context, AttributeSet attr) {
		super(context, attr);
		LayoutParams lp = new LayoutParams(-1, -1);
		frame = new FrameLayout(context);
		frame.setLayoutParams(lp);
		editor = new EditText(context);
		editor.setBackgroundColor(0xff212121);
		editor.setLayoutParams(lp);
		frame.addView(editor);
		addView(frame);
	}
}
