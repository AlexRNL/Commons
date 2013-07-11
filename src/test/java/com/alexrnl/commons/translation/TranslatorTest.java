package com.alexrnl.commons.translation;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link Translator} class.
 * @author Alex
 */
public class TranslatorTest {
	/** The translator to test */
	private Translator translator;
	
	/**
	 * Set up attributes.
	 * @throws URISyntaxException
	 *         if the path to the test translation file is not valid.
	 */
	@Before
	public void setUp () throws URISyntaxException {
		translator = new Translator(Paths.get(getClass().getResource("/locale.xml").toURI()));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.translation.Translator#get(java.lang.String)}.
	 */
	@Test
	public void testGetString () {
		assertEquals("aba.ldr", translator.get("aba.ldr"));
		assertEquals("The Name", translator.get("commons.test.name"));
		assertEquals("Hey, this has The Name, isn't that cool?", translator.get("commons.test.include"));
		assertEquals("Hey, this has The Name", translator.get("commons.test.includeEnd"));
		assertEquals("Hey, this has commons.test.missing", translator.get("commons.test.includeFake"));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.translation.Translator#get(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testGetStringObjectArray () {
		assertEquals("aba.ldr", translator.get("aba.ldr", (Object[]) null));
		assertEquals("The Name", translator.get("commons.test.name", (Object[]) null));
		assertEquals("The Name", translator.get("commons.test.name", new Object[0]));
		assertEquals("My parameter LDR", translator.get("commons.test.parameter", "LDR"));
		assertEquals("This LDR is really ABA!", translator.get("commons.test.parameters", "LDR", "ABA"));
		assertEquals("This LDR is really ABA!", translator.get("commons.test.parameters", "LDR", "ABA", "XXX"));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.translation.Translator#getField(java.lang.String)}.
	 */
	@Test
	public void testGetField () {
		assertEquals("The Name", translator.getField("commons.test.name"));
	}
}