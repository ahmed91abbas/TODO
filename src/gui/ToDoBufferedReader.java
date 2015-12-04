
package gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class ToDoBufferedReader extends BufferedReader {
	private ToDo todo;

	public ToDoBufferedReader(String name, ToDo todo) throws FileNotFoundException {
		super(new FileReader(name));
		this.todo = todo;
	}


	public void load() {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		try {
			int i = 0;
			while (ready()) {
				String string = readLine();
				map.put(i, string);
				i++;
			}
			todo.load(map);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("in bufferedreader ");
			return;
		}
	}
}
