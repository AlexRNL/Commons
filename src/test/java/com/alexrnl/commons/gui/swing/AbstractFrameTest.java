package com.alexrnl.commons.gui.swing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.WindowConstants;

import org.junit.Test;

/**
 * Add test suite for the {@link AbstractFrame} class.
 * @author Alex
 */
public class AbstractFrameTest {
	/**
	 * Private class used for test purposes.
	 * @author Alex
	 */
	private class TestFrame extends AbstractFrame {
		/** Serial version UID */
		private static final long	serialVersionUID	= 1L;

		/**
		 * Constructor #1.<br />
		 * Default constructor.
		 */
		public TestFrame () {
			super("Test");
		}

		/**
		 * Constructor #1.<br />
		 * Default constructor.
		 * @param icon
		 *        the icon to load for decorating the frame.
		 */
		public TestFrame (final Path icon) {
			super("Test", icon);
		}

		@Override
		public void modelPropertyChange (final PropertyChangeEvent evt) {
			// This has been tested in the MVC tests
		}

		@Override
		protected void preInit (final Object... parameters) {
			// TODO Auto-generated method stub
		}

		@Override
		protected void build () {
			// TODO Auto-generated method stub
		}
	}
	
	/**
	 * Test method for the final state of the frame.
	 * @throws URISyntaxException
	 *         if there was an issue with the icon's path.
	 * @throws IOException
	 *         if there was an issue while loading the icon.
	 * @throws InterruptedException
	 *         if the thread was interrupted while waiting for the frame to be drawn.
	 */
	@Test
	public void testFinalState () throws URISyntaxException, IOException, InterruptedException {
		Logger.getLogger(AbstractFrame.class.getName()).setLevel(Level.WARNING);
		final TestFrame frame = new TestFrame();
		assertFalse(frame.isReady());
		assertEquals("Test", frame.getTitle());
		assertFalse(frame.isVisible());
		assertEquals(WindowConstants.HIDE_ON_CLOSE, frame.getDefaultCloseOperation());
		assertNull(frame.getIconImage());
		assertEquals(frame.getMinimumSize(), frame.getSize());
		Logger.getLogger(AbstractFrame.class.getName()).setLevel(Level.FINE);
		final Path iconPath = Paths.get(getClass().getResource("/frameIcon.jpg").toURI());
		final TestFrame frameWithIcon = new TestFrame(iconPath);
		synchronized (frameWithIcon) {
			while (!frameWithIcon.isReady()) {
				frameWithIcon.wait();
			}
		}
		assertTrue(frameWithIcon.isReady());
		assertNotNull(frameWithIcon.getIconImages().get(0));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.gui.swing.AbstractFrame#getFrame()}.
	 */
	@Test
	public void testGetFrame () {
		// TODO more test, while the frame is loading
	}
}
