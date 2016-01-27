package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class PreferencesMenuItem extends JMenuItem implements ActionListener,
		AdjustmentListener {

	private JFrame frame;
	private ToDo todo;
	private JPanel textColorPanel;
	private JPanel markingColorPanel;
	private JTextField text;
	private JScrollBar sbar1;
	private JScrollBar sbar2;
	private JScrollBar sbar3;
	private JScrollBar sbar4;
	private JScrollBar sbar5;
	private JScrollBar sbar6;
	private int textSize = 0;
	private Color textColor;
	private Color markingColor;
	private JTextArea hotkey1TextArea;
	private JTextArea hotkey2TextArea;
	private int newKeyCode;
	private int saveAndCloseKeyCode;
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
	final static String C7 = "newHotkey";
	final static String C8 = "SaveAndCloseHotkey";

	public PreferencesMenuItem(ToDo todo) {
		super("Preferences");
		this.todo = todo;
		prefs = Preferences.userRoot().node(this.getClass().getName());

		textColor = new Color(prefs.getInt("C1", 0), prefs.getInt("C2", 0),
				prefs.getInt("C3", 0));
		markingColor = new Color(prefs.getInt("C4", 0),
				prefs.getInt("C5", 255), prefs.getInt("C6", 0));
		textSize = prefs.getInt("C0", 25);
		newKeyCode = prefs.getInt("C7", KeyEvent.VK_N);
		saveAndCloseKeyCode = prefs.getInt("C8", KeyEvent.VK_Q);

		addActionListener(this);
	}

	public void showInGUI() {
		todo.setForeground(textColor);
		todo.setTextSize(textSize);
		todo.setHotkeyForNew(newKeyCode);
		todo.setHotkeyForSaveAndClose(saveAndCloseKeyCode);
	}

	private void init() {
		frame = new JFrame("Preferences");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JPanel textSizePanel = new JPanel();
		JTextArea jta = new JTextArea();
		jta.setBackground(getBackground());
		Font font = new Font("Verdana", Font.BOLD, 15);
		jta.setFont(font);
		jta.setText("Set default text size:");
		jta.setEditable(false);
		text = new NumberField();
		text.setHorizontalAlignment(JTextField.CENTER);
		text.setText(Integer.toString(prefs.getInt("C0", 25)));
		GridLayout grid = new GridLayout(1, 2);

		frame.getContentPane().setLayout(null);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		text.setBorder(border);

		textSizePanel.setLayout(grid);
		textSizePanel.add(jta);
		textSizePanel.add(text);
		textSizePanel.setBounds(10, 10, 350, 25);
		frame.add(textSizePanel);

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
		buttons.setBounds(0, 370, 400, 50);
		frame.add(buttons);

		hotkey1TextArea = new JTextArea();
		hotkey1TextArea.setBackground(getBackground());
		hotkey1TextArea.setFont(font);
		hotkey1TextArea.setText("Press Alt + "
				+ KeyEvent.getKeyText(prefs.getInt("C7", KeyEvent.VK_N))
				+ " to make a new task");
		hotkey1TextArea.setEditable(false);
		JButton newHotkey = new JButton("Change");
		newHotkey.addKeyListener(new newHotkeyEvent());
		hotkey1TextArea.setBounds(10, 60, 280, 35);
		newHotkey.setBounds(295, 60, 80, 25);
		frame.add(hotkey1TextArea);
		frame.add(newHotkey);

		hotkey2TextArea = new JTextArea();
		hotkey2TextArea.setBackground(getBackground());
		hotkey2TextArea.setFont(font);
		hotkey2TextArea.setText("Press Alt + "
				+ KeyEvent.getKeyText(prefs.getInt("C8", KeyEvent.VK_Q))
				+ " to save and close");
		hotkey2TextArea.setEditable(false);
		JButton SaveAndCloseHotkey = new JButton("Change");
		SaveAndCloseHotkey.addKeyListener(new SaveAndCloseHotkeyEvent());
		hotkey2TextArea.setBounds(10, 100, 280, 35);
		SaveAndCloseHotkey.setBounds(295, 100, 80, 25);
		frame.add(hotkey2TextArea);
		frame.add(SaveAndCloseHotkey);

		JTextArea changeTextColor = new JTextArea();
		changeTextColor.setBackground(getBackground());
		changeTextColor.setFont(font);
		changeTextColor.setText("Change the text color:");
		changeTextColor.setEditable(false);
		changeTextColor.setBounds(10, 155, 300, 20);
		frame.getContentPane().add(changeTextColor);
		sbar1 = new JScrollBar(java.awt.Adjustable.HORIZONTAL,
				textColor.getRed(), 1, 0, 256);
		sbar1.setBounds(10, 185, 200, 15);
		sbar1.setBackground(Color.red);
		sbar1.addAdjustmentListener(this);
		frame.getContentPane().add(sbar1);
		sbar2 = new JScrollBar(java.awt.Adjustable.HORIZONTAL,
				textColor.getGreen(), 1, 0, 256);
		sbar2.setBounds(10, 185 + 20, 200, 15);
		sbar2.setBackground(Color.green);
		sbar2.addAdjustmentListener(this);
		frame.getContentPane().add(sbar2);
		sbar3 = new JScrollBar(java.awt.Adjustable.HORIZONTAL,
				textColor.getBlue(), 1, 0, 256);
		sbar3.setBounds(10, 185 + 40, 200, 15);
		sbar3.setBackground(Color.blue);
		sbar3.addAdjustmentListener(this);
		frame.getContentPane().add(sbar3);
		textColorPanel = new JPanel();
		textColorPanel.setBounds(220, 185, 50, 55);
		textColorPanel.setBackground(textColor);
		frame.getContentPane().add(textColorPanel);

		JTextArea changeBackgroundColor = new JTextArea();
		changeBackgroundColor.setBackground(getBackground());
		changeBackgroundColor.setFont(font);
		changeBackgroundColor.setText("Change the background color:");
		changeBackgroundColor.setEditable(false);
		changeBackgroundColor.setBounds(10, 260, 300, 20);
		frame.getContentPane().add(changeBackgroundColor);
		sbar4 = new JScrollBar(java.awt.Adjustable.HORIZONTAL,
				markingColor.getRed(), 1, 0, 256);
		sbar4.setBounds(10, 290, 200, 15);
		sbar4.setBackground(Color.red);
		sbar4.addAdjustmentListener(this);
		frame.getContentPane().add(sbar4);
		sbar5 = new JScrollBar(java.awt.Adjustable.HORIZONTAL,
				markingColor.getGreen(), 1, 0, 256);
		sbar5.setBounds(10, 290 + 20, 200, 15);
		sbar5.setBackground(Color.green);
		sbar5.addAdjustmentListener(this);
		frame.getContentPane().add(sbar5);
		sbar6 = new JScrollBar(java.awt.Adjustable.HORIZONTAL,
				markingColor.getBlue(), 1, 0, 256);
		sbar6.setBounds(10, 290 + 40, 200, 15);
		sbar6.setBackground(Color.blue);
		sbar6.addAdjustmentListener(this);
		frame.getContentPane().add(sbar6);
		markingColorPanel = new JPanel();
		markingColorPanel.setBounds(220, 290, 50, 55);
		markingColorPanel.setBackground(markingColor);
		frame.getContentPane().add(markingColorPanel);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 3 - frame.getSize().width / 3, dim.height
				/ 6 - frame.getSize().height / 6);
		frame.setPreferredSize(new Dimension(400, 460));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);

	}

	private class newHotkeyEvent extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent event) {
			newKeyCode = event.getKeyCode();
			hotkey1TextArea.setText("Press Alt + " + event.getKeyChar()
					+ " to make a new task");
		}
	}

	private class SaveAndCloseHotkeyEvent extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent event) {
			saveAndCloseKeyCode = event.getKeyCode();
			hotkey2TextArea.setText("Press Alt + " + event.getKeyChar()
					+ " to save and close");
		}
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
			prefs.putInt("C0", textSize);
			String s = Integer.toString(textSize);
			text.setText(s);
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
			newKeyCode = KeyEvent.VK_N;
			prefs.putInt("C7", newKeyCode);
			hotkey1TextArea.setText("Press Alt + "
					+ KeyEvent.getKeyText(newKeyCode) + " to make a new task");
			saveAndCloseKeyCode = KeyEvent.VK_Q;
			prefs.putInt("C8", saveAndCloseKeyCode);
			hotkey2TextArea.setText("Press Alt + "
					+ KeyEvent.getKeyText(saveAndCloseKeyCode)
					+ " to save and close");
		}
	}

	private void setValuesInMarkingColorReg(int red, int green, int blue) {
		prefs.putInt("C4", red);
		prefs.putInt("C5", green);
		prefs.putInt("C6", blue);
	}

	private void setValuesInTextColorReg(int red, int green, int blue) {
		prefs.putInt("C1", red);
		prefs.putInt("C2", green);
		prefs.putInt("C3", blue);
	}

	private class okListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setValuesInTextColorReg(sbar1.getValue(), sbar2.getValue(),
					sbar3.getValue());
			setValuesInMarkingColorReg(sbar4.getValue(), sbar5.getValue(),
					sbar6.getValue());
			textSize = Integer.parseInt(text.getText());
			prefs.putInt("C0", textSize);
			prefs.putInt("C7", newKeyCode);
			prefs.putInt("C8", saveAndCloseKeyCode);

			textColor = textColorPanel.getBackground();
			markingColor = markingColorPanel.getBackground();
			showInGUI();
			todo.enableFrame(true);
			frame.setVisible(false);
			frame.dispose();

		}
	}

	private class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			todo.enableFrame(true);
			frame.setVisible(false);
			frame.dispose();

		}
	}

	public Color getMarkingColor() {
		return markingColor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		todo.enableFrame(false);
		init();
	}
}
