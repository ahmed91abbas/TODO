
package gui;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

public class ToDoPrintStream extends PrintStream {
	public ToDoPrintStream(String fileName) throws FileNotFoundException {
		super(fileName);
	}

	public void save(ArrayList<String> list) {
		for (String s : list) {
			println(s);
		}
		flush();
		close();
	}
}
