package stormPath;

import java.util.ArrayList;
import java.util.Iterator;

public class City {
	private String name;
	private ArrayList storms;

	public City(String name) {
		this.name = name;
		this.storms = new ArrayList();
	}

	public String getName() {
		return this.name;
	}

	public boolean wasHit(Storm s) {

		return this.storms.contains(s);
	}

	public boolean onSamePath(City c) {
		Boolean samePath = false;
		for (int i = 0; i < this.storms.size(); i++) {
			if (c.wasHit((Storm) this.storms.get(i))) {
				samePath = true;
			}
		}
		return samePath;
	}

	public void addStorm(Storm s) {
		this.storms.add(s);
		if (s.getCities().contains(this) == false){
			s.addCity(this);
		}
	}

	public boolean equals(City c) {
		if (this.name == c.getName() && this.storms.size() == c.storms.size()) {
			Boolean flag = true;
			for (int i=0; i < this.storms.size(); i++) {
				Storm storm = (Storm)this.storms.get(i);
				if (!c.wasHit(storm)) {
					flag = false;
				}
			}
			return flag;
		}
		else {
			return false;
		}
	}

	public String toString() {
		String s = this.name + " (";
		Iterator<Storm> iter = this.storms.iterator();
		for (int i = 0; i < this.storms.size(); i++) {
			if (i == this.storms.size() - 1) {
				s = s + iter.next().getName();
			}
			else {
				s = s + iter.next().getName() + ", ";
			}
		}
		return s + ")";
	}


}
