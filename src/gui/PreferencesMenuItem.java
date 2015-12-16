package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class PreferencesMenuItem extends JMenuItem implements ActionListener{

	public PreferencesMenuItem(){
		super("Preferences");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "This option will be available in the next release", "Obs",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
