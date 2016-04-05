package com.alexrnl.commons.arguments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.arguments.parsers.AbstractParser;
import com.alexrnl.commons.arguments.parsers.WIntegerParser;
import com.alexrnl.commons.utils.Word;

/**
 * Test suite for the {@link DefaultParameterValueSetterFactory}.
 * @author Alex
 */
public class DefaultParameterValueSetterFactoryTest {
	/** The factory to test */
	private DefaultParameterValueSetterFactory	factory;
	/** The target object for argument parsing */
	private Target								target;
	/** The parsing results */
	private ParsingResults						results;
	
	/**
	 * A class representing the target for the argument parsing.
	 */
	private static class Target {
		/** The word parameter to test */
		@Param(names = { "-w" }, description = "The word")
		private Word				word;
		/** The number parameter to test */
		@Param(names = { "-n" }, description = "The number")
		private Integer				n;
		/** The list to test */
		@Param(names = { "-l" }, description = "The list", itemClass = Integer.class)
		private final List<Integer>	list			= new ArrayList<>();
		/** The list without item class */
		@Param(names = { "-lnc" }, description = "The list with no class")
		private final List<Integer>	listNoClass		= new ArrayList<>();
		/** The list without parser */
		@Param(names = { "-lnp" }, description = "The list with no parser")
		private final List<Word>	listNoParser	= new ArrayList<>();
	}
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		factory = new DefaultParameterValueSetterFactory();
		target = new Target();
		results = new ParsingResults(Collections.<Parameter>emptySet());
	}
	
	/**
	 * Test method for {@link DefaultParameterValueSetterFactory#addParameterParser(AbstractParser)}.
	 */
	@Test
	public void testAddParameterParser () {
		assertFalse(factory.addParameterParser(new AbstractParser<Word>(Word.class) {
			@Override
			public Word getValue (final String parameter) {
				return Word.getNextWord(parameter);
			}
		}));
		
		final Parameter parameter = CollectionFieldSetterTest.getParameterForField(Target.class, "word");
		factory.createParameterValueSetter(parameter).setValue(results, new ParsingParameters(target, " aba hjo ", "-w"));
		assertEquals("aba", target.word.toString());
	}
	
	/**
	 * Test method for {@link DefaultParameterValueSetterFactory#addParameterParser(AbstractParser)}
	 * when the parser overrides an existing one.
	 */
	@Test
	public void testAddParameterParserOverride () {
		final WIntegerParser overrideIntegerParser = spy(new WIntegerParser());
		assertTrue(factory.addParameterParser(overrideIntegerParser));
		final Parameter parameter = CollectionFieldSetterTest.getParameterForField(Target.class, "n");
		factory.createParameterValueSetter(parameter).setValue(results, new ParsingParameters(target, "28", "-n"));
		assertEquals(Integer.valueOf(28), target.n);
		verify(overrideIntegerParser).getValue("28");
	}
	
	/**
	 * Test method for {@link DefaultParameterValueSetterFactory#createParameterValueSetter(Parameter)}.
	 */
	@Test
	public void testCreateParameterValueSetter () {
		factory.createParameterValueSetter(CollectionFieldSetterTest.getParameterForField(Target.class, "n"))
			.setValue(results, new ParsingParameters(target, "88", "-n"));
		assertEquals(Integer.valueOf(88), target.n);
	}
	
	/**
	 * Test method for {@link DefaultParameterValueSetterFactory#createParameterValueSetter(Parameter)}
	 * when the parameter type has no parser.
	 */
	@Test
	public void testCreateParameterValueSetterNotFound () {
		factory.createParameterValueSetter(CollectionFieldSetterTest.getParameterForField(Target.class, "word"))
			.setValue(results, new ParsingParameters(target, "88", "-n"));
		assertEquals(1, results.getErrors().size());
	}
	
	/**
	 * Test method for {@link DefaultParameterValueSetterFactory#createParameterValueSetter(Parameter)}
	 * when the parameter type is a collection.
	 */
	@Test
	public void testCreateParameterValueSetterCollection () {
		factory.createParameterValueSetter(CollectionFieldSetterTest.getParameterForField(Target.class, "list"))
			.setValue(results, new ParsingParameters(target, "88", "-l"));
		assertEquals(Arrays.asList(88), target.list);
	}
	
	/**
	 * Test method for {@link DefaultParameterValueSetterFactory#createParameterValueSetter(Parameter)}
	 * when the parameter type is a collection without an item class defined.
	 */
	@Test
	public void testCreateParameterValueSetterCollectionNoItemClass () {
		factory.createParameterValueSetter(CollectionFieldSetterTest.getParameterForField(Target.class, "listNoClass"))
			.setValue(results, new ParsingParameters(target, "88", "-lnc"));
		assertEquals(1, results.getErrors().size());
	}
	
	/**
	 * Test method for {@link DefaultParameterValueSetterFactory#createParameterValueSetter(Parameter)}
	 * when the parameter type is a collection with no parser associated.
	 */
	@Test
	public void testCreateParameterValueSetterCollectionNoParser () {
		factory.createParameterValueSetter(CollectionFieldSetterTest.getParameterForField(Target.class, "listNoParser"))
			.setValue(results, new ParsingParameters(target, " aba hjo", "-lnp"));
		assertEquals(1, results.getErrors().size());
	}
}
