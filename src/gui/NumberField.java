package gui;


import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AbstractDocument;

@SuppressWarnings("serial")
public class NumberField extends JTextField {

	/**
	 * Creates a text field to display only up to two numbers.
	 */
	public NumberField() {
		super("");
		((AbstractDocument) this.getDocument())
				.setDocumentFilter(new OneNumberFilter());
	}

	private class OneNumberFilter extends DocumentFilter {
		OneNumberFilter() {
			super();
		}

		/**
		 * Makes sure that only the numbers 1-99 can be added
		 */
		public void replace(FilterBypass fb, int offset, int length,
				String str, AttributeSet attr) throws BadLocationException {
			if ((fb.getDocument().getLength() + str.length() - length) > 2) {
				return;
			}
			for(int i = 0; i < str.length(); i++){
				String c = "" + str.charAt(i);
				boolean oneToNine = !c.equals("") && !c.equals("" + 1) && !c.equals("" + 2)
						&& !c.equals("" + 3) && !c.equals("" + 4)
						&& !c.equals("" + 5) && !c.equals("" + 6)
						&& !c.equals("" + 7) && !c.equals("" + 8)
						&& !c.equals("" + 9);
			if (oneToNine && i == 0) {
				return;
			} else if (oneToNine && i > 0 && !c.equals("" + 0)){ //TODO fix that you can have the second integer as a zero
				return;
			}
			}
			fb.replace(offset, length, str, attr);
		}
	}
}
