package stormPath;

import sun.font.TrueTypeGlyphMapper;

import java.util.ArrayList;
import java.util.Iterator;

public class Storm {
	private String name;
	private ArrayList cities;
	private int year;

	public Storm(String name, int year) {
		this.name = name;
		this.year = year;
		this.cities = new ArrayList();
	}

	public String getName() {

		return this.name;
	}

	public int getYear(){

		return this.year;
	}

	public ArrayList getCities() {

		return this.cities;
	}

	public void addCity(City c) {
		this.cities.add(c);
		if (c.wasHit(this) == false) {
			c.addStorm(this);
		}
	}

	public boolean equals(Storm s) {
		if ((s.name.equals(this.name) && (s.year == this.year))) {
			return true;
		}
		else {
			return false;
		}
	}

	public String toString() {
		if (cities.isEmpty()) {
			return this.getName();
		}
		else {
			String s = this.name + ", " + this.year;
			for (int i = 0; i < this.cities.size(); i++) {
				City t = (City) this.cities.get(i);
				s = s + System.lineSeparator() + t.getName();
			}
			return s;
		}
	}


}
