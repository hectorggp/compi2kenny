package com;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Chingar {
	int a = 0;

	Chingar() {
		prueba();
	}

	void otro(String[] una) {
		boolean a = false;
		int b = 2, c = 1;
		if (a)
			if (a)
				b = 12;
			else
				c = -3 + 2 - 32;
		else
			a = true;
		Chingar p = new Chingar();
		p.geti();
		String aa = "ola q ase" + b + c;
		System.out.println(aa);
	}

	public int geti() {
		return a;
	}

	private StyledText styledText;
	
	public void prueba() {
		final Display display = new Display();
		final Shell shell = new Shell(display);

		styledText = new StyledText(shell, SWT.V_SCROLL
				| SWT.BORDER);
		styledText.setText("asdfasdfasdfasdf12345678910234567890");

		StyleRange[] ranges = new StyleRange[2];
		ranges[0] = new StyleRange(0, 3,
				display.getSystemColor(SWT.COLOR_GREEN), null);
		ranges[1] = new StyleRange(3, 6,
				display.getSystemColor(SWT.COLOR_BLUE), null);

		int [] ran = {1, 3, 4, 6, 8, 9, 11, 18};
		styledText.setStyleRanges(ran, ranges);
		styledText.setBounds(10, 10, 500, 100);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
