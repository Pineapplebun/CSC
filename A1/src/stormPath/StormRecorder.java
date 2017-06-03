package stormPath;

import com.sun.deploy.panel.ExceptionListDialog;
import com.sun.javafx.collections.MappingChange;
import jdk.internal.util.xml.impl.Input;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.io.*;
import java.util.*;

public class StormRecorder {
	public static void main(String[] args) {
		Charset encoding = Charset.forName("UTF-8");
		Path file = Paths.get("D:\\google-drive\\Year 4\\CSC207\\Labs and Assignments\\A1\\src\\stormPath\\Stormlist.txt");

		//ArrayList<Storm> storms = new ArrayList<Storm>();
		//ArrayList<City> cities = new ArrayList<City>();

		HashMap<String, City> cities = new HashMap<String, City>();

		try (BufferedReader reader = Files.newBufferedReader(file, encoding)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				String[] data = line.split("\\s\\|\\s*");
				//System.out.println(data[0]);
				System.out.println(data[1]);
				//System.out.println(data[2]);

				String[] nameyear = data[0].split("\\s");
				Storm s = new Storm(nameyear[0], Integer.parseInt(nameyear[1]));

				for (int i=1; i<data.length; i++) {
					//is it already in our map?
					if (cities.containsKey(data[i])) {
						s.addCity(cities.get(data[i]));
					}
					else {
						City c = new City(data[i]);
						s.addCity(c);
						cities.put(c.getName(), c);
					}
				}
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}

		boolean run = true;
		while (run) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter the name of a city:");
			String input = scanner.next();

			if (input.equals("exit")) {
				run = false;
			} else {
				boolean found = false;
				System.out.println(cities.size());
				if (cities.containsKey(input)) {
					System.out.println(cities.get(input));
				}
				else {
					System.out.println("This is not a valid city.");
				}
			}
		}
	}
}