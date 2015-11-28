
package gui;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Map.Entry;
import java.util.Set;

public class ToDoPrintStream extends PrintStream {
	public ToDoPrintStream(String fileName) throws FileNotFoundException {
		super(fileName);
	}

	
	public void save(Set<Entry<Integer, String>> set) {
		for (Entry<Integer, String> entry : set) {
			println(entry.getValue());
		}
		flush();
		close();
	}
}
