package com.alexrnl.commons.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilities on collections.<br />
 * @author Alex
 */
public final class CollectionUtils {
	/** Logger */
	private static final Logger	LG	= Logger.getLogger(CollectionUtils.class.getName());
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private CollectionUtils () {
		super();
		throw new InstantiationError("Instantiation of class " + CollectionUtils.class + " is forbidden");
	}
	
	/**
	 * Check if a collection is sorted.<br />
	 * @param collection
	 *        the collection to check.
	 * @return <code>true</code> if the collection is sorted.
	 */
	public static <T extends Comparable<? super T>> boolean isSorted (final Iterable<T> collection) {
		Objects.requireNonNull(collection);
		final Iterator<T> it = collection.iterator();
		
		// Empty collection case
		if (!it.hasNext()) {
			return true;
		}

		T current = it.next();
		while (it.hasNext()) {
			final T next = it.next();
			if (current.compareTo(next) > 0) {
				if (LG.isLoggable(Level.INFO)) {
					LG.info("Collection is unordered at element " + current);
				}
				return false;
			}
			current = next;
		}
		
		return true;
	}
	
	/**
	 * Convert a {@link Properties} into a {@link HashMap}.<br />
	 * @param properties
	 *        the properties to convert.
	 * @return the map with the same objects.
	 */
	public static Map<String, String> convertPropertiesToMap (final Properties properties) {
		Objects.requireNonNull(properties);
		final HashMap<String, String> map = new HashMap<>(properties.size());
		for (final Entry<Object, Object> property : properties.entrySet()) {
			// Properties are always Strings (left as Object in JDK for backward compatibility purposes)
			map.put((String) property.getKey(), (String) property.getValue());
		}
		return map;
	}
	
	/**
	 * Return a random item from the collection specified.<br />
	 * If the collection is empty, <code>null</code> is returned.
	 * @param collection
	 *        the collection.
	 * @param random
	 *        the random generator to use.
	 * @return the randomly selected item.
	 */
	public static <T> T getRandomItem (final Collection<T> collection, final Random random) {
		// Check argument validity
		if (collection == null) {
			throw new IllegalArgumentException("Cannot get random item with null collection");
		}
		if (random == null) {
			throw new IllegalArgumentException("Cannot get random item with null random generator");
		}
		
		// Avoid unnecessary computation with empty collection (or with one item).
		if (collection.size() == 0) {
			return null;
		}
		if (collection.size() == 1) {
			return collection.iterator().next();
		}
		
		// Standard cases
		final int position = random.nextInt(collection.size());
		int counter = 0;
		final Iterator<T> iterator = collection.iterator();
		while (counter < position) {
			iterator.next();
			counter++;
		}
		return iterator.next();
	}
	
	/**
	 * Join several all provided collections in the first one provided.<br />
	 * The instance of the target collection is returned to ease use in foreach loops.
	 * @param target
	 *        the target collection
	 * @param collections
	 *        the collections of items to add in the target collection.
	 * @return the first collection, with the other items of the collection added.
	 */
	@SafeVarargs
	public static <T> Collection<T> joinCollections (final Collection<T> target, final Collection<? extends T>... collections) {
		if (target == null) {
			throw new IllegalArgumentException("Cannot join collection in null target");
		}
		if (collections.length == 0) {
			return target;
		}
		
		for (int i = 0; i < collections.length; i++) {
			target.addAll(collections[i]);
		}
		return target;
	}
}
