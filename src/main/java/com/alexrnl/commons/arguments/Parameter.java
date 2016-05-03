package com.alexrnl.commons.arguments;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.alexrnl.commons.arguments.validators.ParameterValidator;
import com.alexrnl.commons.utils.object.AutoEquals;
import com.alexrnl.commons.utils.object.AutoHashCode;

/**
 * A class which describe a parameter.<br />
 * Parameter are comparable, they will be sorted by their order field, then by their required field
 * (field required first) and finally by the shortest name of the parameter available.
 * @author Alex
 */
public class Parameter implements Comparable<Parameter> {
	/** The reference to the field of this parameter */
	private final Field									field;
	/** The names of this parameter */
	private final Set<String>							names;
	/** <code>true</code> if the parameter is required */
	private final boolean								required;
	/** The description of the parameter */
	private final String								description;
	/** The order of the parameter */
	private final int									order;
	/** The validator of the parameter */
	private final Class<? extends ParameterValidator>	validator;
	/** The class of the item in the collection (relevant only if the parameter is a collection) */
	private final Class<?>								itemClass;
	
	/**
	 * Constructor #1.<br />
	 * @param field
	 *        the field of this parameter.
	 * @param names
	 *        the names of the parameter.
	 * @param required
	 *        if the parameter is required.
	 * @param description
	 *        the description of the parameter.
	 * @param order
	 *        the order of the parameter.
	 * @param validator
	 *        the validator of the parameter.
	 * @param itemClass
	 *        the class of the item in the collection (relevant only if the parameter is a
	 *        collection).
	 */
	public Parameter (final Field field, final Collection<String> names, final boolean required,
			final String description, final int order, final Class<? extends ParameterValidator> validator, final Class<?> itemClass) {
		super();
		if (names.isEmpty()) {
			throw new IllegalArgumentException("Cannot create parameter with no names");
		}
		
		this.field = field;
		this.names = new TreeSet<>(new Comparator<String>() {
			@Override
			public int compare (final String o1, final String o2) {
				final int comparedLength = Integer.valueOf(o1.length()).compareTo(o2.length());
				if (comparedLength != 0) {
					return comparedLength;
				}
				return o1.compareTo(o2);
			}
		});
		this.names.addAll(names);
		this.required = required;
		this.description = description;
		this.order = order;
		this.validator = validator;
		this.itemClass = itemClass;
	}
	
	/**
	 * Constructor #2.<br />
	 * @param field
	 *        the field of this parameter.
	 * @param param
	 *        the {@link Param} annotation associated to the field.
	 */
	public Parameter (final Field field, final Param param) {
		this(field, Arrays.asList(param.names()), param.required(), param.description(), param.order(),
				param.validator().asSubclass(ParameterValidator.class), param.itemClass());
	}
	
	/**
	 * Return the attribute field.
	 * @return the attribute field.
	 */
	public Field getField () {
		return field;
	}
	
	/**
	 * Return the attribute names.
	 * @return the attribute names.
	 */
	@com.alexrnl.commons.utils.object.Field
	public Set<String> getNames () {
		return Collections.unmodifiableSet(names);
	}
	
	/**
	 * Return the attribute required.
	 * @return the attribute required.
	 */
	@com.alexrnl.commons.utils.object.Field
	public boolean isRequired () {
		return required;
	}
	
	/**
	 * Return the attribute description.
	 * @return the attribute description.
	 */
	public String getDescription () {
		return description;
	}
	
	/**
	 * Return the attribute order.
	 * @return the attribute order.
	 */
	@com.alexrnl.commons.utils.object.Field
	public int getOrder () {
		return order;
	}
	
	/**
	 * Return the attribute validator.
	 * @return the attribute validator.
	 */
	public Class<? extends ParameterValidator> getValidator () {
		return validator;
	}

	/**
	 * Return the attribute itemClass.
	 * @return the attribute itemClass.
	 */
	public Class<?> getItemClass () {
		return itemClass;
	}
	
	@Override
	public int hashCode () {
		return AutoHashCode.getInstance().hashCode(this);
	}
	
	@Override
	public boolean equals (final Object obj) {
		if (!(obj instanceof Parameter)) {
			return false;
		}
		return AutoEquals.getInstance().compare(this, (Parameter) obj);
	}
	
	@Override
	public int compareTo (final Parameter o) {
		final int compareOrder = Integer.compare(getOrder(), o.getOrder());
		// If order is enough to compare both parameters
		if (compareOrder != 0) {
			return compareOrder;
		}
		// Use the required parameter to compare if the order was not enough
		final int compareRequired = Boolean.compare(o.isRequired(), isRequired());
		if (compareRequired != 0) {
			return compareRequired;
		}
		// Compare their first names in the sets
		return getNames().iterator().next().compareTo(o.getNames().iterator().next());
	}
	
}
