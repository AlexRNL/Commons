package com.alexrnl.commons.translation;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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
	 * Test method for {@link Translator#get(String)}.
	 */
	@Test
	public void testGetString () {
		assertEquals("aba.ldr", translator.get("aba.ldr"));
		assertEquals("The Name", translator.get("commons.test.name"));
		assertEquals("Hey, this has The Name, isn't that cool?", translator.get("commons.test.include"));
		assertEquals("Hey, this has The Name", translator.get("commons.test.includeEnd"));
		assertEquals("commons.test.missing", translator.get("commons.test.missing"));
		assertEquals("Hey, this has commons.test.missing", translator.get("commons.test.includeFake"));
	}
	
	/**
	 * Test method for {@link Translator#get(java.lang.String, Collection)}.
	 */
	@Test
	public void testGetStringCollection () {
		assertEquals("aba.ldr", translator.get("aba.ldr", Collections.emptyList()));
		assertEquals("The Name", translator.get("commons.test.name", Collections.emptyList()));
		assertEquals("The Name", translator.get("commons.test.name", (Collection<Object>) null));
		assertEquals("My parameter LDR", translator.get("commons.test.parameter", "LDR"));
		assertEquals("This LDR is really ABA!", translator.get("commons.test.parameters", "LDR", "ABA"));
		assertEquals("This LDR is really ABA!", translator.get("commons.test.parameters", "LDR", "ABA", "XXX"));
	}
	
	/**
	 * Test method for {@link Translator#get(ParametrableTranslation)}.
	 */
	@Test
	public void testGetParametrableTranslation () {
		assertEquals("This LDR is really ABA!", translator.get(new ParametrableTranslation() {
			@Override
			public String getTranslationKey () {
				return "commons.test.parameters";
			}
			
			@Override
			public Collection<Object> getParameters () {
				return Arrays.<Object>asList("LDR", "ABA", "XXX");
			}
		}));
	}
	
	/**
	 * Test method for {@link Translator#get(Translatable)}.
	 */
	@Test
	public void testGetTranslatable () {
		assertEquals("The Name", translator.get(new Translatable() {
			@Override
			public String getTranslationKey () {
				return "commons.test.name";
			}
		}));
	}
	
}
