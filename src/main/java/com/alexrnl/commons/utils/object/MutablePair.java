package com.alexrnl.commons.utils.object;


/**
 * Implementation for a pair of mutable objects.<br /
 * @author Alex
 * @param <L>
 *        the type of the left object of the pair.
 * @param <R>
 *        the type of the right object of the pair.
 */
public class MutablePair<L, R> extends Pair<L, R> {
	/** Serial version UID */
	private static final long	serialVersionUID	= 4120413691248731093L;
	
	/** The left object of the pair */
	private L left;
	/** The right object of the pair */
	private R right;
	
	/**
	 * Constructor #1.<br />
	 * @param left
	 *        the left object.
	 * @param right
	 *        the right object.
	 */
	public MutablePair (final L left, final R right) {
		super();
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Constructor #2.<br />
	 * Default constructor, initialize a pair with <code>null</code> objects.
	 */
	public MutablePair () {
		this(null, null);
	}

	@Override
	@Field
	public L getLeft () {
		return left;
	}

	/**
	 * Set the attribute left.
	 * @param left the attribute left.
	 */
	public void setLeft (final L left) {
		this.left = left;
	}

	@Override
	@Field
	public R getRight () {
		return right;
	}

	/**
	 * Set the attribute right.
	 * @param right the attribute right.
	 */
	public void setRight (final R right) {
		this.right = right;
	}

	@Override
	public R setValue (final R value) {
		final R oldRight = getRight();
		setRight(value);
		return oldRight;
	}
	
}
