package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class AboutMenuItem extends JMenuItem implements ActionListener {
	private Font font;

	public AboutMenuItem() {
		super("About ToDo");
		font = new Font("Verdana", Font.ITALIC, 18);
		super.setFont(font);
		addActionListener(this);
	}

	private void init() {
		JFrame frame = new JFrame("About ToDo");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 3 - frame.getSize().width / 3, dim.height / 6 - frame.getSize().height / 6);
		JTextArea jta = new JTextArea();
		jta.setFont(font);
		jta.setEditable(false);
		jta.setLineWrap(true);

		jta.append("\n Version 1.5");
		jta.append("\n\nDate of release: November 2016");
		jta.append(
				"\n\nIf you have any suggestions or if you want to leave some feedback feel free to contact me:\nEmail: snipeeeeer@gmail.com");

		frame.add(jta);
		frame.setPreferredSize(new Dimension(422, 380));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		init();

	}
}
