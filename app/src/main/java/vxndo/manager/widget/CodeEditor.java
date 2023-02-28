package vxndo.manager.widget;

import android.animation.*;
import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.text.*;
import vxndo.manager.widget.internal.*;

public class CodeEditor
extends FrameLayout {

	private TextEditor editorView;

	public CodeEditor(Context context) {
		this(context, null);
	}

	public CodeEditor(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutParams params = new LayoutParams(-1, -1);
		params.gravity = Gravity.BOTTOM;
		final FrameLayout rootView = new FrameLayout(context);
		rootView.setLayoutParams(params);
		final LayoutParams scrollParams = new LayoutParams(-1, -1);
		final ScrollView scrollView = new ScrollView(context);
		scrollView.setLayoutParams(scrollParams);
		scrollView.setFillViewport(true);
		HorizontalScrollView hScrollView = new HorizontalScrollView(context);
		hScrollView.setLayoutParams(new LayoutParams(-1, -1));
		hScrollView.setFillViewport(true);
		editorView = new TextEditor(context);
		editorView.setBackground(null);
		editorView.setHorizontallyScrolling(true);
		editorView.setShowLineNumbers(true);
		editorView.setLayoutParams(new LayoutParams(-1, -1));
		hScrollView.addView(editorView);
		scrollView.addView(hScrollView);
		rootView.addView(scrollView);
		addView(rootView);
	}

	public void setText(String text) {
		editorView.setText(text);
	}

	public Editable getText() {
		return editorView.getText();
	}

	public void setTheme(EditorTheme theme) {
		editorView.setTheme(theme);
	}
}

