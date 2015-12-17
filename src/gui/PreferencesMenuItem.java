package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class PreferencesMenuItem extends JMenuItem implements ActionListener {
	
	private JFrame frame;
	private int textSize = 0;
	private Color background;
	private Color markingColor;
	private static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
	private static final Color DEFAULT_MARKING_COLOR = Color.GREEN;
	private static final int DEFAULT_TEXT_SIZE = 25;

	public PreferencesMenuItem() {
		super("Preferences");
		addActionListener(this);
	}

	/*
	 * text size hot key for: ...new...saveandclose colors
	 */

	private void init() {
		frame = new JFrame("Preferences");
		JPanel panel = new JPanel();
		JTextArea jta = new JTextArea();
		jta.setText("Set default text size:");
		jta.setEditable(false);
		JTextArea textSize = new JTextArea();
		GridLayout grid = new GridLayout(1, 2);

		Border border = BorderFactory.createLineBorder(Color.BLUE, 5);
		textSize.setBorder(border);
		jta.setBorder(border);

		panel.setLayout(grid);
		panel.add(jta);
		panel.add(textSize);
		frame.add(panel, BorderLayout.BEFORE_FIRST_LINE);

		JPanel buttons = new JPanel();
		JButton ok = new JButton("Ok");
		ok.addActionListener(new okListener());
		buttons.add(ok);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new cancelListener());
		buttons.add(cancel);
		JButton restore = new JButton("Restore defaults");
		restore.addActionListener(new restoreListener());
		buttons.add(restore);

		GridLayout buttonsGrid = new GridLayout(1, 3);
		buttons.setLayout(buttonsGrid);
		frame.add(buttons, BorderLayout.PAGE_END);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 3 - frame.getSize().width / 3, dim.height
				/ 6 - frame.getSize().height / 6);
		frame.setPreferredSize(new Dimension(400, 400));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);

	}

	private class restoreListener implements ActionListener {
		

		public void actionPerformed(ActionEvent e) {
			textSize = DEFAULT_TEXT_SIZE;
			background = DEFAULT_BACKGROUND_COLOR;
			markingColor = DEFAULT_MARKING_COLOR;
			//TODO show in gui
		}
	}

	private class okListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	}

	private class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			 frame.setVisible(false);
			 frame.dispose();
		}
	}

	public Color getBackgroundColor() {
		return background == null ? DEFAULT_BACKGROUND_COLOR : background;
	}

	public Color getMarkingColor() {
		return markingColor == null ? DEFAULT_MARKING_COLOR : markingColor;
	}

	public int getTextSize() {
		return textSize == 0 ? DEFAULT_TEXT_SIZE : textSize;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		init();
	}
}
