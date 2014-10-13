package com.alexrnl.commons.arguments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.alexrnl.commons.arguments.parsers.AbstractParser;

/**
 * Test suite for the {@link Arguments} class.
 * @author Alex
 */
public class ArgumentsTest {
	/** The arguments to test */
	private Arguments	arguments;
	/** The target for the parameters */
	private Target		target;
	/** The mocked output stream */
	@Mock
	private PrintStream	out;
	
	/**
	 * A class representing the target for the argument parsing.
	 * @author Alex
	 */
	private static class Target {
		/** If the feature is used */
		@Param(names = { "-u", "--used" }, description = "if the feature should be used")
		private boolean	used;
		/** The name to use */
		@Param(names = { "-n" }, description = "the name of the object", required = true)
		private String	name;
		/** A boolean wrapper */
		@Param(names = { "-b" }, description = "boolean wrapping")
		private Boolean	b;
		/** An object */
		@Param(names = { "-o" }, description = "object")
		private Object	o;
		/** An integer */
		@Param(names = { "-x" }, description = "the value for x")
		private int		x;
		/** The list of values*/
		@Param(names = { "-l" }, description = "values", itemClass = String.class)
		private List<String> values;

		/**
		 * Default constructor.
		 */
		private Target () {
			super();
			values = new ArrayList<>();
		}
		
		/**
		 * Constructor which leave the collection attribute to <code>null</code>.
		 * @param nullList
		 *        useless parameter.
		 */
		private Target (final String nullList) {
			super();
		}
		
		/**
		 * Return the attribute used.
		 * @return the attribute used.
		 */
		public boolean isUsed () {
			return used;
		}
		
		/**
		 * Return the attribute name.
		 * @return the attribute name.
		 */
		public String getName () {
			return name;
		}
		
		/**
		 * Return the attribute b.
		 * @return the attribute b.
		 */
		public Boolean isB () {
			return b;
		}
		
		/**
		 * Return the attribute x.
		 * @return the attribute x.
		 */
		public int getX () {
			return x;
		}
		
		/**
		 * Return the attribute values.
		 * @return the attribute values.
		 */
		public List<String> getValues () {
			return values;
		}
	}
	
	/**
	 * Class which holds duplicate parameter name.
	 * @author Alex
	 */
	private static class DuplicateParam {
		/** A string */
		@Param(names = { "-x" }, description = "string")
		private String	s;
		/** An integer */
		@Param(names = { "-x" }, description = "the value for x")
		private int		x;
	}
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		initMocks(this);
		target = new Target();
		arguments = new Arguments("manLau", target, out);
	}
	
	/**
	 * Test that an {@link IllegalArgumentException} is thrown when a name is used twice.
	 */
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testDoubleDefinitionOfParameter () {
		new Arguments("test", new DuplicateParam());
	}
	
	/**
	 * Test method for {@link Arguments#parse(String[])}.
	 */
	@Test
	public void testParseStringArray () {
		Logger.getLogger(Arguments.class.getName()).setLevel(Level.WARNING);
		arguments.parse("-u", "-n", "alex");
		assertTrue(target.isUsed());
		assertEquals("alex", target.getName());
		assertFalse(target.isB());
		Logger.getLogger(Arguments.class.getName()).setLevel(Level.INFO);
	}
	
	/**
	 * Test method for {@link Arguments#parse(Iterable)}.
	 */
	@Test
	public void testParseIterableOfString () {
		arguments.parse(Arrays.asList("--used", "-n", "manLau", "-b", "-x", "28"));
		assertTrue(target.isUsed());
		assertEquals("manLau", target.getName());
		assertTrue(target.isB());
		assertEquals(28, target.getX());
	}
	
	/**
	 * Test method for {@link Arguments#addParameterParser(AbstractParser)}.
	 */
	@Test
	public void testAddParameterParser () {
		final AbstractParser customParser = mock(AbstractParser.class);
		when(customParser.getFieldType()).thenReturn(String.class);
		assertTrue(arguments.addParameterParser(customParser));
		arguments.parse("-n", "test");
		verify(customParser).parse(eq(target), any(Field.class), eq("test"));
	}
	
	/**
	 * Test method for checking help request.
	 */
	@Test
	public void testHelpRequest () {
		try {
			arguments.parse("-h");
		} catch (final IllegalArgumentException e) {
			verify(out, times(1)).println(arguments);
			verify(out, never()).println(anyString());
		}
		try {
			arguments.parse("--help");
		} catch (final IllegalArgumentException e) {
			verify(out, times(1)).println(arguments);
			verify(out, never()).println(anyString());
		}
	}
	
	/**
	 * Test for an unknown parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testUnknownParameter () {
		arguments.parse("--unknown");
	}
	
	/**
	 * Test for missing a required parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMissingArgument () {
		arguments.parse("-u");
	}
	
	/**
	 * Test for missing a value for a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMissingValue () {
		arguments.parse("-n");
	}
	
	/**
	 * Test for no parser matching type found.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNoParser () {
		arguments.parse("-o", "-n", "test");
	}
	
	/**
	 * Test with unparsable argument.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBadValue () {
		arguments.parse("-n", "test", "-x", "0.28");
	}
	
	/**
	 * Test with the allow unknown parameter option.
	 */
	@Test
	public void testAllowUnknownParameters () {
		final Arguments unknownArguments = new Arguments("test", target, out, true);
		unknownArguments.parse("-n", "test", "-i", "not", "-a");
		assertEquals("test", target.getName());
		verifyZeroInteractions(out);
	}
	
	/**
	 * Test with parameters for collections.
	 */
	@Test
	public void testCollectionItemProcessing () {
		arguments.parse("-n", "test", "-l", "myValue", "-l", "XXX");
		assertEquals("test", target.getName());
		assertEquals(Arrays.asList("myValue", "XXX"), target.getValues());
	}
	
	/**
	 * Test that an uninitialized collection is throwing an {@link IllegalArgumentException}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNullTargetCollection () {
		final Arguments args = new Arguments("test", new Target("forNullList"));
		args.parse("-n", "test", "-l", "fail!");
	}
	
	/**
	 * Test that a collection without a itemClass attribute is throwing an exception.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNoItemClassSet () {
		final Arguments args = new Arguments("test", new Object () {
			@Param(names = "-l", description = "Strings for the program")
			private final List<String> strings = new LinkedList<>();
		});
		args.parse("-l", "fail!");
	}
	
	/**
	 * Test with an collection item which has no parser.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testUnknownParserForCollectionItem () {
		final Arguments args = new Arguments("test", new Object () {
			@Param(names = "-m", description = "Strings for the program", itemClass = Map.class)
			private final List<Map<String, String>> strings = new LinkedList<>();
		});
		args.parse("-m", "XXX");
	}
	
	/**
	 * Test with a bad value in a collection.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBadValueInCollection () {
		final Arguments args = new Arguments("test", new Object () {
			@Param(names = "-numbers", description = "Numbers for the program", itemClass = Integer.class)
			private final List<Integer> numbers = new LinkedList<>();
		});
		args.parse("-numbers", "XXX");
	}
	
	/**
	 * Test method for {@link Arguments#joinArguments(Iterable)}
	 */
	@Test
	public void testJoinArguments () {
		assertEquals(Arrays.asList(""), Arguments.joinArguments(Arrays.asList("")));
		assertEquals(Arrays.asList("hello", "world"), Arguments.joinArguments(Arrays.asList("hello", "world")));
		assertEquals(Arrays.asList("-s", "Aba Ldr"), Arguments.joinArguments(Arrays.asList("-s", "\"Aba", "Ldr\"")));
		assertEquals(Arrays.asList("-s", " \"Aba", "Ldr\""), Arguments.joinArguments(Arrays.asList("-s", " \"Aba", "Ldr\"")));
		assertEquals(Arrays.asList("-s", "\"Aba", "Ldr\""), Arguments.joinArguments(Arrays.asList("-s", "\\\"Aba", "Ldr\"")));
		assertEquals(Arrays.asList("-s", "Aba Ldr"), Arguments.joinArguments(Arrays.asList("-s", "\"Aba", "Ldr")));
		assertEquals(Arrays.asList("-s", "Aba Ldr\" Xdr"), Arguments.joinArguments(Arrays.asList("-s", "\"Aba", "Ldr\\\"", "Xdr")));
		assertEquals(Arrays.asList("-s", "Aba Ldr\" Xdr"), Arguments.joinArguments(Arrays.asList("-s", "\"Aba", "Ldr\\\"", "Xdr\"")));
		assertEquals(Arrays.asList("-s", "Aba \"Ldr"), Arguments.joinArguments(Arrays.asList("-s", "\"Aba", "\"Ldr\"")));
	}
	
	/**
	 * Test method for {@link Arguments#usage()}.
	 */
	@Test
	public void testUsagePrintStream () {
		arguments.usage();
		verify(out).println(arguments);
		// Check output on stdout
		new Arguments("stdout", target).usage();
	}
	
	/**
	 * Test method for {@link Arguments#toString()}.
	 */
	@Test
	public void testToString () {
		assertEquals("manLau usage as follow:\n" +
				"\t   -n\t\t\t\tthe name of the object\n" +
				"\t[  -b\t\t\t\tboolean wrapping  ]\n" +
				"\t[  -l\t\t\t\tvalues  ]\n" +
				"\t[  -o\t\t\t\tobject  ]\n" +
				"\t[  -u, --used\t\t\t\tif the feature should be used  ]\n" +
				"\t[  -x\t\t\t\tthe value for x  ]",
				arguments.toString());
	}
}
