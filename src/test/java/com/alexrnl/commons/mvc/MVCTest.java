package com.alexrnl.commons.mvc;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the
 * @author Alex
 */
public class MVCTest {
	/** The model for the tests*/
	private ModelTest		model;
	/** The view for the tests */
	private ViewTest		view;
	/** The controller for the tests */
	private ControllerTest	controller;
	
	/**
	 * Set-up the MVC pattern.
	 */
	@Before
	public void setUp () {
		model = new ModelTest();
		controller = new ControllerTest();
		view = new ViewTest(controller);
		
		controller.addModel(model);
		controller.addView(view);

		model.initDefault();
	}
	
	/**
	 * Clean-up the controller.
	 */
	@After
	public void tearDown () {
		controller.removeModel(model);
		controller.removeView(view);
	}
	
	/**
	 * Test the propagation of property through MVC, when changing the model.
	 */
	@Test
	public void changeFromViewTest () {
		assertEquals(8, view.getDisplayedValue().intValue());
		assertEquals("Alex", view.getDisplayedName());
		
		view.setDisplayedName("Walt");
		view.setValue(99);

		assertEquals(99, view.getDisplayedValue().intValue());
		assertEquals("Walt", view.getDisplayedName());
	}
}
