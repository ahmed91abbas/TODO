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
				if(!c.matches("[0-9]+")){
					return;
				}
			}
			fb.replace(offset, length, str, attr);
		}
	}
}
