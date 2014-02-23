package com.alexrnl.commons.utils.object;

import java.io.Serializable;
import java.util.Map.Entry;


/**
 * Abstract class for a pair of element (referred to as left and right).<br />
 * The class implements the {@link Entry} interface, with the left being the key and the right being
 * the value).
 * @author Alex
 * @param <L>
 *        the type of the left object of the pair.
 * @param <R>
 *        the type of the right object of the pair.
 */
public abstract class Pair<L, R> implements Entry<L, R>, Serializable {
	/** Serial version UID */
	private static final long	serialVersionUID	= 6567766115225429510L;
	
	/**
	 * Return the left value.
	 * @return the left value of the pair.
	 */
	@Field
	public abstract L getLeft ();
	
	/**
	 * Return the right value.
	 * @return the right value of the pair.
	 */
	@Field
	public abstract R getRight ();
	
	@Override
	public L getKey () {
		return getLeft();
	}
	
	@Override
	public R getValue () {
		return getRight();
	}
	
	@Override
	public int hashCode () {
		return AutoHashCode.getInstance().hashCode(this);
	}
	@Override
	public boolean equals (final Object obj) {
		if (!(obj instanceof Pair)) {
			return false;
		}
		return AutoCompare.getInstance().compare(this, (Pair<?, ?>) obj);
	}
	
	@Override
	public String toString () {
		return "(" + getLeft() + ", " + getRight() + ")";
	}
}
