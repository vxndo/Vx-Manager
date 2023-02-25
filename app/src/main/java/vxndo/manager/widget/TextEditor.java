package vxndo.manager.widget;

import android.content.*;
import android.util.*;
import android.text.Editable;
import android.content.res.*;
import android.graphics.*;
import android.widget.*;
import android.graphics.drawable.*;
import android.text.*;

public class TextEditor
extends EditText {

	private OnErrorListener errorListener;

	public TextEditor(Context context) {
		this(context, null);
	}

	public TextEditor(Context context, AttributeSet attr) {
		super(context, attr);
		setTextWatcher(new TextWatcher(this));
	}

	public void setTextWatcher(TextWatcher watcher) {
		super.addTextChangedListener(watcher);
	}

	@Deprecated
	@Override
	public void addTextChangedListener(android.text.TextWatcher watcher) {}

	@Override
	public void setBackground(Drawable background) {
		if (!background.equals(getBackground())) {
			super.setBackground(background);
		}
	}

	public void setOnErrorListener(OnErrorListener listener) {
		errorListener = listener;
	}

	public boolean isError() {
		if (errorListener != null) {
			return errorListener.onError();
		} else return false;
	}

	public static interface OnErrorListener {
		public boolean onError();
	}

	public static class TextWatcher
	implements android.text.TextWatcher {

		private TextEditor editor;
		private Drawable defaultTint;
		private Drawable errorTint;

		public TextWatcher(TextEditor editor) {
			this.editor = editor;
			defaultTint = editor.getBackground();
			Drawable d = editor.getBackground().getConstantState().newDrawable();
			d.setColorFilter(0xffff0000, PorterDuff.Mode.SRC_ATOP);
			errorTint = d;
		}

		@Override
		public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {}

		@Override
		public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {}

		@Override
		public void afterTextChanged(Editable p1) {
			if (editor.errorListener != null) {
				if (editor.errorListener.onError()) {
					editor.setBackground(errorTint);
				} else {
					editor.setBackground(defaultTint);
				}
			}
		}
	}
}
