package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class AboutMenuItem extends JMenuItem implements ActionListener {

	public AboutMenuItem() {
		super("About ToDo");
		addActionListener(this);
	}

	private void init() {
		JFrame frame = new JFrame("About ToDo");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 3 - frame.getSize().width / 3, dim.height
				/ 6 - frame.getSize().height / 6);
		JTextArea jta = new JTextArea();
		jta.setEditable(false);
		jta.setLineWrap(true);

		jta.append("\n Version 1.1");
		jta.append("\n\nTime of release: December 2015");
		jta.append("\n\nIf you have any suggestions or if you want to leave a feedback feel free to contact me:\nEmail: snipeeeeer@gmail.com");

		frame.add(jta);
		frame.setPreferredSize(new Dimension(300, 250));
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		init();

	}
}
