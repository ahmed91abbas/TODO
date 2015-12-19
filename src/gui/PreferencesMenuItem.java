package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class PreferencesMenuItem extends JMenuItem implements ActionListener,
		AdjustmentListener {

	private JFrame frame;
	private JPanel textColorPanel;
	private JPanel markingColorPanel;
	private JScrollBar sbar1;
	private JScrollBar sbar2;
	private JScrollBar sbar3;
	private JScrollBar sbar4;
	private JScrollBar sbar5;
	private JScrollBar sbar6;
	private int textSize = 0;
	private Color textColor;
	private Color markingColor;
	private static final Color DEFAULT_TEXT_COLOR = Color.BLACK;
	private static final Color DEFAULT_MARKING_COLOR = Color.GREEN;
	private static final int DEFAULT_TEXT_SIZE = 25;
	private Preferences prefs;
	final static String C0 = "Text size";
	final static String C1 = "sbar1";
	final static String C2 = "sbar2";
	final static String C3 = "sbar3";
	final static String C4 = "sbar4";
	final static String C5 = "sbar5";
	final static String C6 = "sbar6";

	public PreferencesMenuItem() {
		super("Preferences");
		prefs = Preferences.userRoot().node(this.getClass().getName());
		try{
		textColor = new Color(prefs.getInt("C1", 0), prefs.getInt("C2", 0), prefs.getInt("C3", 0));
		markingColor = new Color(prefs.getInt("C4", 0), prefs.getInt("C5", 0), prefs.getInt("C6", 0));
		} catch (Exception e){
			textColor = DEFAULT_TEXT_COLOR;
			markingColor = DEFAULT_MARKING_COLOR;
//			textSize = DEFAULT_TEXT_SIZE;
		}
		addActionListener(this);
	}

	/*
	 * text size hot key for: ...new...saveandclose colors
	 */

	private void init() {
		frame = new JFrame("Preferences");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JPanel panel = new JPanel();
		JTextArea jta = new JTextArea();
		jta.setText("Set default text size:");
		jta.setEditable(false);
		JTextArea textSize = new JTextArea();
		GridLayout grid = new GridLayout(1, 2);

		frame.getContentPane().setLayout(null);
		Border border = BorderFactory.createLineBorder(Color.BLUE, 5);
		textSize.setBorder(border);
		jta.setBorder(border);

		panel.setLayout(grid);
		panel.add(jta);
		panel.add(textSize);
		panel.setBounds(0, 0, 400, 55);
		frame.add(panel);

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
		buttons.setBounds(0, 310, 400, 50);
		frame.add(buttons);

		
		sbar1 = new JScrollBar(java.awt.Adjustable.HORIZONTAL, textColor.getRed(), 1, 0, 255);
		sbar1.setBounds(10, 120, 200, 15);
		sbar1.setBackground(Color.red);
		sbar1.addAdjustmentListener(this);
		frame.getContentPane().add(sbar1);
		sbar2 = new JScrollBar(java.awt.Adjustable.HORIZONTAL, textColor.getGreen(), 1, 0,
				255);
		sbar2.setBounds(10, 120 + 20, 200, 15);
		sbar2.setBackground(Color.green);
		sbar2.addAdjustmentListener(this);
		frame.getContentPane().add(sbar2);
		sbar3 = new JScrollBar(java.awt.Adjustable.HORIZONTAL, textColor.getBlue(), 1, 0,
				255);
		sbar3.setBounds(10, 120 + 40, 200, 15);
		sbar3.setBackground(Color.blue);
		sbar3.addAdjustmentListener(this);
		frame.getContentPane().add(sbar3);
		textColorPanel = new JPanel();
		textColorPanel.setBounds(220, 120, 50, 55);
		textColorPanel.setBackground(textColor);
		frame.getContentPane().add(textColorPanel);
		
		sbar4 = new JScrollBar(java.awt.Adjustable.HORIZONTAL, markingColor.getRed(), 1, 0, 255);
		sbar4.setBounds(10, 220, 200, 15);
		sbar4.setBackground(Color.red);
		sbar4.addAdjustmentListener(this);
		frame.getContentPane().add(sbar4);
		sbar5 = new JScrollBar(java.awt.Adjustable.HORIZONTAL,markingColor.getGreen(), 1, 0,
				255);
		sbar5.setBounds(10, 220 + 20, 200, 15);
		sbar5.setBackground(Color.green);
		sbar5.addAdjustmentListener(this);
		frame.getContentPane().add(sbar5);
		sbar6 = new JScrollBar(java.awt.Adjustable.HORIZONTAL, markingColor.getBlue(), 1, 0,
				255);
		sbar6.setBounds(10, 220 + 40, 200, 15);
		sbar6.setBackground(Color.blue);
		sbar6.addAdjustmentListener(this);
		frame.getContentPane().add(sbar6);
		markingColorPanel = new JPanel();
		markingColorPanel.setBounds(220, 220, 50, 55);
		markingColorPanel.setBackground(markingColor);
		frame.getContentPane().add(markingColorPanel);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 3 - frame.getSize().width / 3, dim.height
				/ 6 - frame.getSize().height / 6);
		frame.setPreferredSize(new Dimension(400, 400));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);

	}

	public void adjustmentValueChanged(AdjustmentEvent e) {
		textColorPanel.setBackground(new Color(sbar1.getValue(), sbar2
				.getValue(), sbar3.getValue()));
		markingColorPanel.setBackground(new Color(sbar4.getValue(), sbar5
				.getValue(), sbar6.getValue()));
	}

	private class restoreListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			textSize = DEFAULT_TEXT_SIZE;
			textColor = DEFAULT_TEXT_COLOR;
			markingColor = DEFAULT_MARKING_COLOR;
			sbar1.setValue(DEFAULT_TEXT_COLOR.getRed());
			sbar2.setValue(DEFAULT_TEXT_COLOR.getGreen());
			sbar3.setValue(DEFAULT_TEXT_COLOR.getBlue());
			textColorPanel.setBackground(DEFAULT_TEXT_COLOR);
			sbar4.setValue(DEFAULT_MARKING_COLOR.getRed());
			sbar5.setValue(DEFAULT_MARKING_COLOR.getGreen());
			sbar6.setValue(DEFAULT_MARKING_COLOR.getBlue());
			markingColorPanel.setBackground(DEFAULT_MARKING_COLOR);
		}
	}
	
	private void setValuesInMarkingColorReg(int red, int green, int blue){
		prefs.putInt("C4", red);
		prefs.putInt("C5", green);
		prefs.putInt("C6", blue);
	}
	
	private void setValuesInTextColorReg(int red, int green, int blue){
		prefs.putInt("C1", red);
		prefs.putInt("C2", green);
		prefs.putInt("C3", blue);
	}
	
	private class okListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setValuesInTextColorReg(sbar1.getValue(), sbar2.getValue(), sbar3.getValue());
			setValuesInMarkingColorReg(sbar4.getValue(), sbar5.getValue(), sbar6.getValue());
			textColor = textColorPanel.getBackground();
			markingColor = markingColorPanel.getBackground();
			frame.setVisible(false);
			frame.dispose();
		}
	}

	private class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
			frame.dispose();
		}
	}

	public Color getTextColor() {
		return textColor;
	}

	public Color getMarkingColor() {
		return markingColor;
	}

	public int getTextSize() {
		return textSize == 0 ? DEFAULT_TEXT_SIZE : textSize;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		init();
	}
}
