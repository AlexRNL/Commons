package com.alexrnl.commons.arguments;

import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Test suite for the {@link ParsingResults} class.
 * @author Alex
 */
public class ParsingResultsTest {
	/** The parsing results to test */
	private ParsingResults	parsingResults;
	/** A mocked required parameter */
	@Mock
	private Parameter		param1;
	/** A mocked required parameter */
	@Mock
	private Parameter		param2;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		initMocks(this);
		parsingResults = new ParsingResults(new HashSet<>(Arrays.asList(param1, param2)));
	}
	
	/**
	 * Test method for {@link ParsingResults#isHelpRequested()}.
	 */
	@Test
	public void testIsHelpRequested () {
		assertFalse(parsingResults.isHelpRequested());
	}
	
	/**
	 * Test method for {@link ParsingResults#setHelpRequested(boolean)}.
	 */
	@Test
	public void testSetHelpRequested () {
		assertFalse(parsingResults.isHelpRequested());
		parsingResults.setHelpRequested(true);
		assertTrue(parsingResults.isHelpRequested());
	}
	
	/**
	 * Test method for {@link ParsingResults#removeRequiredParameter(Parameter)}.
	 */
	@Test
	public void testRemoveRequiredParameter () {
		final Set<Parameter> requiredParameters = parsingResults.getRequiredParameters();
		assertEquals(2, requiredParameters.size());
		parsingResults.removeRequiredParameter(param1);
		assertEquals(1, requiredParameters.size());
		assertThat(param1, not(isIn(requiredParameters)));
		assertThat(param2, isIn(requiredParameters));
	}
	
	/**
	 * Test method for {@link ParsingResults#getRequiredParameters()}.
	 */
	@Test
	public void testGetRequiredParameters () {
		final Set<Parameter> requiredParameters = parsingResults.getRequiredParameters();
		assertEquals(2, requiredParameters.size());
		assertThat(param1, isIn(requiredParameters));
		assertThat(param2, isIn(requiredParameters));
	}
	
	/**
	 * Test method for {@link ParsingResults#getRequiredParameters()}.<br />
	 * Verify, that required parameters cannot be modified externally.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testCannotModifyRequiredParameters () {
		parsingResults.getRequiredParameters().clear();
	}
	
	/**
	 * Test method for {@link ParsingResults#addError(String)}.
	 */
	@Test
	public void testAddError () {
		final List<String> errors = parsingResults.getErrors();
		errors.isEmpty();
		parsingResults.addError("aba");
		parsingResults.addError("hjo");
		parsingResults.addError("aaa");
		assertEquals(Arrays.asList("aba", "hjo", "aaa"), errors);
	}
	
	/**
	 * Test method for {@link ParsingResults#addError(String)}.<br />
	 * Verify that a <code>null</code> error cannot be added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddNullError () {
		parsingResults.addError(null);
	}
	
	/**
	 * Test method for {@link ParsingResults#addError(String)}.<br />
	 * Verify that an empty error cannot be added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddEmptyError () {
		parsingResults.addError("");
	}
	
	/**
	 * Test method for {@link ParsingResults#getErrors()}.
	 */
	@Test
	public void testGetErrors () {
		final List<String> errors = parsingResults.getErrors();
		errors.isEmpty();
	}
	
	/**
	 * Test method for {@link ParsingResults#getErrors()}.<br />
	 * Verify, that errors cannot be modified externally.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testCannotModifyErrors () {
		parsingResults.getErrors().clear();
	}
}
