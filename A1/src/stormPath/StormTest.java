package stormPath;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StormTest {

	@Test
	public void testStormGetName() {
		Storm s = new Storm("Danielle", 2016);
		assertEquals("Danielle", s.getName());
	}

	@Test
	public void testStormGetYear() {
		Storm s = new Storm("Danielle", 2016);
		assertEquals(2016, s.getYear());
	}

	@Test
	public void testCityGetName() {
		City c = new City("Veracruz");
		assertEquals("Veracruz", c.getName());
	}

	@Test
	public void testCityWasHit() {
		Storm s = new Storm("Danielle", 2016);
		City c = new City("Veracruz");
		s.addCity(c);
		assertTrue(c.wasHit(s));
	}

	@Test
	public void testCityWasNotHit() {
		Storm s = new Storm("Danielle", 2016);
		City c = new City("Veracruz");
		assertFalse(c.wasHit(s));
	}

	@Test
	public void testGetCitiesOneStormNoCity() {
		Storm s = new Storm("Danielle", 2016);
		assertTrue(s.getCities().size() == 0);
	}

	@Test
	public void testOneStormAddCityGetCities() {
		Storm s = new Storm("Danielle", 2016);
		City c = new City("Veracruz");
		s.addCity(c);
		assertTrue(s.getCities().size() == 1);
		assertTrue(s.getCities().contains(c));
	}

	@Test
	public void testOneStormAddStormGetCities() {
		Storm s = new Storm("Danielle", 2016);
		City c = new City("Veracruz");
		c.addStorm(s);
		assertTrue(s.getCities().size() == 1);
		assertTrue(s.getCities().contains(c));
		assertTrue(c.wasHit(s));
	}

	@Test
	public void testAddCityOnSamePath() {
		Storm s = new Storm("Danielle", 2016);
		City c1 = new City("Veracruz");
		City c2 = new City("Tamiahua");
		s.addCity(c1);
		s.addCity(c2);
		assertTrue(c1.onSamePath(c2));
	}

	@Test
	public void testNotOnSamePath() {
		City c1 = new City("Veracruz");
		City c2 = new City("Tamiahua");
		assertFalse(c1.onSamePath(c2));
	}

	@Test
	public void testOneStormTwoCities() {
		Storm s = new Storm("Danielle", 2016);
		City c1 = new City("Veracruz");
		City c2 = new City("Tamiahua");
		s.addCity(c1);
		c2.addStorm(s);

		// Does s contain both Cities?
		assertTrue(s.getCities().size() == 2);
		assertTrue(s.getCities().contains(c1));
		assertTrue(s.getCities().contains(c2));

		// Was each City in the path of the Storm?
		assertTrue(c1.wasHit(s));
		assertTrue(c2.wasHit(s));

		// Was c1 and c2 hit by the same storm?
		assertTrue(c1.onSamePath(c2));
		assertTrue(c2.onSamePath(c1));
	}

	@Test
	public void testStormEquals() {
		Storm s1 = new Storm("Danielle", 2016);
		Storm s2 = new Storm("Danielle", 2016);
		assertTrue(s1.equals(s2));
	}

	@Test
	public void testStormEqualsDifferentCities() {
		Storm s1 = new Storm("Danielle", 2016);
		Storm s2 = new Storm("Danielle", 2016);
		City c = new City("Veracruz");
		s1.addCity(c);
		assertTrue(s1.equals(s2));
	}

	@Test
	public void testStormNotEqualsName() {
		Storm s1 = new Storm("Danielle", 2016);
		Storm s2 = new Storm("Fred", 2015);
		assertFalse(s1.equals(s2));
	}

	@Test
	public void testStormNotEqualsYear() {
		Storm s1 = new Storm("Danielle", 2016);
		Storm s2 = new Storm("Bonnie", 2016);
		assertFalse(s1.equals(s2));
	}

	@Test
	public void testCityEqualsNoStorms() {
		City c1 = new City("Veracruz");
		City c2 = new City("Veracruz");
		assertTrue(c1.equals(c2));
	}

	@Test
	public void testCityNotEqualsTwoCities() {
		City c1 = new City("Veracruz");
		City c2 = new City("Tamiahua");
		assertFalse(c1.equals(c2));
	}

	@Test
	public void testCityNotEqualsNonCity() {
		City c1 = new City("Veracruz");
		assertFalse(c1.equals("Veracruz"));
	}

	@Test
	public void testCityEqualsTwoStorms() {
		City c1 = new City("Veracruz");
		City c2 = new City("Veracruz");
		Storm s1 = new Storm("Danielle", 2016);
		Storm s2 = new Storm("Fred", 2015);
		c1.addStorm(s1);
		c1.addStorm(s2);
		c2.addStorm(s1);
		c2.addStorm(s2);
		assertTrue(c1.equals(c2));
	}

	@Test
	public void testCityEqualsTwoStormsDifferentOrder() {
		City c1 = new City("Veracruz");
		City c2 = new City("Veracruz");
		Storm s1 = new Storm("Danielle", 2016);
		Storm s2 = new Storm("Fred", 2015);
		c1.addStorm(s1);
		c1.addStorm(s2);
		c2.addStorm(s2);
		c2.addStorm(s1);
		assertTrue(c1.equals(c2));
	}

	@Test
	public void testCityNotEqualsExtraStorm() {
		City c1 = new City("Veracruz");
		City c2 = new City("Veracruz");
		Storm s1 = new Storm("Danielle", 2016);
		Storm s2 = new Storm("Fred", 2015);
		Storm s3 = new Storm("Dolly", 2014);
		c1.addStorm(s1);
		c1.addStorm(s2);
		c2.addStorm(s2);
		c2.addStorm(s1);
		c2.addStorm(s3);
		assertFalse(c1.equals(c2));
	}

	@Test
	public void testCityToStringNoStorms() {
		City c1 = new City("Veracruz");
		String res = c1.toString();
		String exp = "Veracruz ()";
		assertEquals(exp, res);
	}

	@Test
	public void testCityToStringOneStorm() {
		City c1 = new City("Veracruz");
		Storm s1 = new Storm("Danielle", 2016);
		c1.addStorm(s1);
		String res = c1.toString();
		String exp = "Veracruz (Danielle)";
		assertEquals(exp, res);
	}

	@Test
	public void testCityToStringThreeStorms() {
		City c1 = new City("Veracruz");
		Storm s1 = new Storm("Danielle", 2016);
		Storm s2 = new Storm("Fred", 2015);
		Storm s3 = new Storm("Dolly", 2014);
		c1.addStorm(s1);
		c1.addStorm(s2);
		c1.addStorm(s3);
		String res = c1.toString();
		String exp = "Veracruz (Danielle, Fred, Dolly)";
		assertEquals(exp, res);
	}

	@Test
	public void testStormToStringNoCities() {
		Storm s1 = new Storm("Danielle", 2016);
		String res = s1.toString();
		String exp = "Danielle";
		assertEquals(exp, res);
	}

	@Test
	public void testStormToStringOneStorm() {
		City c1 = new City("Veracruz");
		Storm s1 = new Storm("Danielle", 2016);
		s1.addCity(c1);
		String res = s1.toString();
		String exp = "Danielle, 2016" + System.lineSeparator() + c1.getName();
		assertEquals(exp, res);
	}

	@Test
	public void testStormToStringFourCities() {
		City c1 = new City("Veracruz");
		City c2 = new City("Tamiahua");
		City c3 = new City("Toronto");
		City c4 = new City("Shanghai");
		Storm s1 = new Storm("Danielle", 2016);
		s1.addCity(c1);
		s1.addCity(c2);
		s1.addCity(c3);
		s1.addCity(c4);
		String res = s1.toString();
		String exp = "Danielle, 2016" + System.lineSeparator() + c1.getName() + System.lineSeparator() + c2.getName()
				+ System.lineSeparator() + c3.getName() + System.lineSeparator() + c4.getName();
		assertEquals(exp, res);
	}

}
