package vxndo.manager.widget;

import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.util.*;
import android.widget.*;
import vxndo.manager.widget.internal.*;
import android.view.*;

public class TextEditor
extends EditText {

	private OnErrorListener errorListener;
	private OnSubmitListener submitListener;
	private EditorTheme theme;
	private Paint gutterBgPaint, gutterLinePaint, gutterTextPaint;
	private boolean showLineNumbers;

	public TextEditor(Context context) {
		this(context, null);
	}

	public TextEditor(Context context, AttributeSet attr) {
		super(context, attr);
		setGravity(Gravity.START);
		setImeOptions(268435456);
		setTextWatcher(new TextWatcher(this));
	}

	public void setTheme(EditorTheme theme) {
		this.theme = theme;
		setBackgroundColor(theme.bgColor);
		setTextColor(theme.textColor);
		gutterBgPaint = new Paint();
		gutterLinePaint = new Paint();
		gutterTextPaint = new Paint();
		gutterBgPaint.setColor(theme.gutterBg);
		gutterLinePaint.setColor(theme.gutterLineColor);
		gutterTextPaint.set(getPaint());
		gutterTextPaint.setTextAlign(Paint.Align.RIGHT);
		gutterTextPaint.setColor(theme.gutterTextColor);
	}

	public void setTextWatcher(TextWatcher watcher) {
		super.addTextChangedListener(watcher);
	}

	public void setShowLineNumbers(boolean showLineNumbers) {
		this.showLineNumbers = showLineNumbers;
		setTheme(EditorTheme.Dark);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case event.KEYCODE_ENTER:
			if (submitListener != null) {
				submitListener.onSubmit();
			} break;
		} return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (showLineNumbers) {
			Integer lineNumber = 1;
			Integer textWidth = (int) getPaint().measureText("m");
			Integer lineWidth = (int) getPaint().measureText(String.valueOf(getLineCount()));
			int width = lineWidth+textWidth;
			RectF bgRect = new RectF(0, 0, width, getHeight()+getScrollY());
			RectF lineRect = new RectF(width, 0, width+2, getHeight()+getScrollY());
			canvas.drawRect(bgRect, gutterBgPaint);
			canvas.drawRect(lineRect, gutterLinePaint);
			for (int i = 0; i < getLineCount(); ++i) {
				int baseline = getLineBounds(i, null);
				if (i == 0) {
					canvas.drawText(lineNumber.toString(), lineRect.left-textWidth/2, baseline, gutterTextPaint);
					++lineNumber;
				} else if (getText().charAt(getLayout().getLineStart(i) - 1) == '\n') {
					canvas.drawText(lineNumber.toString(), lineRect.left-textWidth/2, baseline, gutterTextPaint);
					++lineNumber;
				}
			} setPaddingLeft(width+(textWidth/2));
		} super.onDraw(canvas);
	}

	@Override
	protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
		super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
		if (horiz > oldHoriz || vert > oldVert) {
			requestLayout();
		}
	}

	private void setPaddingLeft(int paddingLeft) {
		if (paddingLeft == getPaddingLeft()) return;
		setPadding(paddingLeft, getPaddingTop(), getPaddingRight(), getPaddingBottom());
	}

	@Deprecated
	@Override
	public void addTextChangedListener(android.text.TextWatcher watcher) {}

	@Override
	public void setBackground(Drawable background) {
		if (background == null || !background.equals(getBackground())) {
			super.setBackground(background);
		}
	}

	public void setOnErrorListener(OnErrorListener listener) {
		errorListener = listener;
	}

	public void setOnSubmitListener(OnSubmitListener listener) {
		submitListener = listener;
	}

	public boolean isError() {
		if (errorListener != null) {
			return errorListener.getError();
		} else return false;
	}

	public static interface OnErrorListener {
		public boolean getError();
		public void onError();
	}

	public static interface OnSubmitListener {
		public void onSubmit();
	}

	public static class SimpleErrorListener
	implements OnErrorListener {
		@Override
		public boolean getError() {
			return false;
		}
		@Override
		public void onError() {}
	}

	public static class TextWatcher
	implements android.text.TextWatcher {

		private TextEditor editor;
		private Drawable defaultTint;
		private Drawable errorTint;

		public TextWatcher(TextEditor editor) {
			this.editor = editor;
			defaultTint = editor.getBackground();
			Drawable d = editor.getBackground().mutate().getConstantState().newDrawable();
			d.setColorFilter(0xffff0000, PorterDuff.Mode.SRC_ATOP);
			errorTint = d;
		}

		@Override
		public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {}

		@Override
		public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {}

		@Override
		public void afterTextChanged(android.text.Editable p1) {
			if (editor.errorListener != null) {
				if (editor.errorListener.getError()) {
					editor.setBackground(errorTint);
					editor.errorListener.onError();
				} else {
					editor.setBackground(defaultTint);
				}
			}
		}
	}
}
