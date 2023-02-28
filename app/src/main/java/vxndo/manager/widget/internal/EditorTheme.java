package vxndo.manager.widget.internal;

public enum EditorTheme {

	Light(new int[]{
			  0xFFFFFFFF, 0xFF000000, 0xFFF0F0F0,
			  0xFFE0E0E0, 0xFFB0B0B0
		  }),
	Dark(new int[]{
			 0xFF2B2B2B, 0xFFA9B7C6, 0xFF36383A,
			 0xFF454647, 0xFF888888
		 });

	public int bgColor, textColor;
	public int gutterBg, gutterLineColor, gutterTextColor;

	public EditorTheme(int[] colors) {
		bgColor = colors[0];
		textColor = colors[1];
		gutterBg = colors[2];
		gutterLineColor = colors[3];
		gutterTextColor = colors[4];
	}
}
