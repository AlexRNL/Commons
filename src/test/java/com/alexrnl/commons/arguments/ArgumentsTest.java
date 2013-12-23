package com.alexrnl.commons.arguments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link Arguments} class.
 * @author Alex
 */
public class ArgumentsTest {
	/** The arguments to test */
	private Arguments	arguments;
	/** The target for the parameters */
	private Target		target;
	
	/**
	 * A class representing the target for the argument parsing.
	 * @author Alex
	 */
	private static class Target {
		/** If the feature is used. */
		@Param(names = {"-u","--used"}, description = "if the feature should be used", required = false)
		private boolean isUsed;
		
		/**
		 * Constructor #1.<br />
		 * Default constructor.
		 */
		private Target () {
			super();
		}
		
		/**
		 * Return the attribute isUsed.
		 * @return the attribute isUsed.
		 */
		public boolean isUsed () {
			return isUsed;
		}
		
		/**
		 * Set the attribute isUsed.
		 * @param isUsed the attribute isUsed.
		 */
		public void setUsed (final boolean isUsed) {
			this.isUsed = isUsed;
		}
	}
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		target = new Target();
		arguments = new Arguments("manLau", target);
	}
	
	/**
	 * Test method for {@link Arguments#parse(String[])}.
	 */
	@Test
	public void testParseStringArray () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link Arguments#parse(Iterable)}.
	 */
	@Test
	public void testParseIterableOfString () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link Arguments#usage(PrintStream)}.
	 */
	@Test
	public void testUsagePrintStream () {
		final PrintStream out = mock(PrintStream.class);
		arguments.usage(out);
		verify(out).println(anyObject());
	}
	
	/**
	 * Test method for {@link Arguments#usage()}.
	 */
	@Test
	public void testUsage () {
		arguments.usage();
	}
	
	/**
	 * Test method for {@link Arguments#toString()}.
	 */
	@Test
	public void testToString () {
		assertEquals("manLau usage as follow:\n" +
				"\t[  -u, --used\t\t\t\tif the feature should be used  ]",
				arguments.toString());
	}
}
