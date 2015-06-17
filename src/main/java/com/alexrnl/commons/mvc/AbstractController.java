package com.alexrnl.commons.mvc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alexrnl.commons.error.ExceptionUtils;
import com.alexrnl.commons.utils.object.ImmutablePair;
import com.alexrnl.commons.utils.object.Pair;
import com.alexrnl.commons.utils.object.ReflectUtils;

/**
 * Abstract class which defines the default behavior for generic controllers.<br />
 * Allows to register several {@link AbstractView views} and {@link AbstractModel models}, the
 * controller is in charge of the communications between the views and the model that they display.
 * @author Alex
 */
public abstract class AbstractController implements PropertyChangeListener {
	/** Logger */
	private static final Logger								LG	= Logger.getLogger(AbstractController.class.getName());
	
	/** The models represented by the views */
	private final List<AbstractModel>						registeredModels;
	/** The views to be updated by the models */
	private final List<AbstractView>						registeredViews;
	/** Map containing the reference to the methods of the model */
	private final Map<AbstractModel, Map<Pair<String, Class<?>>, Method>>	methodPropertyMap;
	
	/**
	 * Constructor #1.<br />
	 * Default constructor.
	 */
	public AbstractController () {
		super();
		registeredModels = new LinkedList<>();
		registeredViews = new LinkedList<>();
		methodPropertyMap = new HashMap<>();
	}
	
	/**
	 * Add a model and subscribe to its modifications.
	 * @param model
	 *        the model to add to the controller.
	 */
	public void addModel (final AbstractModel model) {
		if (model == null) {
			LG.warning("Cannot add null model to controller");
			return;
		}
		synchronized (registeredModels) {
			registeredModels.add(model);
		}
		model.addModelChangeListener(this);
		synchronized (methodPropertyMap) {
			methodPropertyMap.put(model, new HashMap<Pair<String, Class<?>>, Method>());
		}
	}
	
	/**
	 * Remove a model and unsubscribe to its modifications.
	 * @param model
	 *        the model to remove.
	 */
	public void removeModel (final AbstractModel model) {
		if (model == null) {
			LG.warning("Cannot remove null model to controller");
			return;
		}
		synchronized (registeredModels) {
			registeredModels.remove(model);
		}
		model.removeModelListener(this);
		synchronized (methodPropertyMap) {
			methodPropertyMap.remove(model);
		}
	}
	
	/**
	 * Return a safe copy of the registered models.
	 * @return the registered models.
	 */
	protected AbstractModel[] getRegisteredModels () {
		synchronized (registeredModels) {
			return registeredModels.toArray(new AbstractModel[0]);
		}
	}
	
	/**
	 * Get the methods to call for the specified property.<br />
	 * The methods are cached in a map to avoid looking for them each time.
	 * @param model
	 *        the target model.
	 * @param propertyName
	 *        the name of the property.
	 * @param propertyClass
	 *        the class of the property.
	 * @return a {@link Set} with the methods to call.
	 */
	protected Set<Method> getPropertyMethod (final AbstractModel model, final String propertyName, final Class<?> propertyClass) {
		final Set<Method> methods = new HashSet<>();
		synchronized (methodPropertyMap) {
			final Map<Pair<String, Class<?>>, Method> modelMap = methodPropertyMap.get(model);
			final Set<Class<?>> classes = ReflectUtils.getAllInterfaces(propertyClass);
			for (final Class<?> attributeClass : classes) {
				final ImmutablePair<String, Class<?>> pair = new ImmutablePair<String, Class<?>>(propertyName, attributeClass);
				if (!modelMap.containsKey(pair)) {
					try {
						final Method method = model.getClass().getMethod(ReflectUtils.SETTER_PREFIX + propertyName,
								new Class<?>[] { attributeClass });
						modelMap.put(pair, method);
					} catch (final SecurityException e) {
						LG.warning("Could not retrieve method for property " + propertyName
								+ " from model " + model.getClass() + ": " + ExceptionUtils.display(e));
					} catch (final NoSuchMethodException e) {
						// Put a null value to avoid future attempts to retrieve the methods
						modelMap.put(pair, null);
					}
				}
				final Method method = modelMap.get(pair);
				if (method != null) {
					methods.add(method);
				}
			}
		}
		return methods;
	}
	
	/**
	 * Utility method for setting property on models.
	 * @param propertyName
	 *        the name of the property to update (used in the setter).
	 * @param newValue
	 *        the value to set, of the appropriate class.
	 */
	public void setModelProperty (final String propertyName, final Object newValue) {
		if (LG.isLoggable(Level.INFO)) {
			LG.info("Setting property " + propertyName + " to " + newValue + " on models");
		}
		boolean propertyFound = false;
		for (final AbstractModel model : getRegisteredModels()) {
			for (final Method method : getPropertyMethod(model, propertyName, newValue.getClass())) {
				try {
					propertyFound = true;
					method.invoke(model, newValue);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					LG.log(Level.WARNING, "Could not set property " + propertyName + " on model "
							+ model.getClass() + ": " + ExceptionUtils.display(e), e);
				}
			}
		}
		if (!propertyFound) {
			LG.warning("No property " + propertyName + " found in models " + Arrays.toString(getRegisteredModels()));
		}
	}

	/**
	 * Add a view to notify on models changes.
	 * @param view
	 *        the view to notify.
	 */
	public void addView (final AbstractView view) {
		if (view == null) {
			LG.warning("Cannot add null view to controller");
			return;
		}
		synchronized (registeredViews) {
			registeredViews.add(view);
		}
	}
	
	/**
	 * Remove a view from the controller and thus will not receive any future property changes.
	 * @param view
	 *        the view to remove from future notifications.
	 */
	public void removeView (final AbstractView view) {
		if (view == null) {
			LG.warning("Cannot remove null view to controller");
			return;
		}
		synchronized (registeredViews) {
			registeredViews.remove(view);
		}
	}
	
	/**
	 * Return a safe copy of the registered models.
	 * @return the registered models.
	 */
	protected AbstractView[] getRegisteredViews () {
		synchronized (registeredViews) {
			return registeredViews.toArray(new AbstractView[0]);
		}
	}
	
	@Override
	public void propertyChange (final PropertyChangeEvent evt) {
		if (LG.isLoggable(Level.FINE)) {
			LG.fine("property changed in model: " + evt.getPropertyName() + " = " + evt.getNewValue()
					+ " (" + evt.getOldValue() + ")");
		}
		for (final AbstractView view : getRegisteredViews()) {
			view.modelPropertyChange(evt);
		}
	}

	/**
	 * Dispose of the resources used by the controller.<br />
	 * Clear frames, window, unsubscribe models and listeners, etc.
	 */
	public void dispose () {
		for (final AbstractModel model : getRegisteredModels()) {
			removeModel(model);
		}
		for (final AbstractView view : getRegisteredViews()) {
			removeView(view);
		}
	}
	
	/**
	 * Persists any changes to the database.<br />
	 * By default, does nothing. Override this, if required.
	 * @return <code>true</code> if the operation succeeded.
	 */
	public boolean persist () {
		return true;
	}
	
	/**
	 * Reload the models registered from the database.<br />
	 * By default, does nothing. Override this, if required.
	 */
	public void reload () {
		// Nothing to do here
	}
}
