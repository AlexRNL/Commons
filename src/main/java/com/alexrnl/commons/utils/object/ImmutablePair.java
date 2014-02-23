package com.alexrnl.commons.utils.object;

/**
 * Implementation for a pair of immutable objects.<br
 * /
 * @author Alex
 * @param <L>
 *        the type of the left object of the pair.
 * @param <R>
 *        the type of the right object of the pair.
 */
public class ImmutablePair<L, R> extends Pair<L, R> {
	/** Serial version UID */
	private static final long	serialVersionUID	= -5018624454481496431L;
	
	/** The left object of the pair */
	private final L	left;
	/** The right object of the pair */
	private final R	right;
	
	/**
	 * Constructor #1.<br />
	 * @param left
	 *        the left object.
	 * @param right
	 *        the right object.
	 */
	public ImmutablePair (final L left, final R right) {
		super();
		this.left = left;
		this.right = right;
	}
	
	@Override
	@Field
	public L getLeft () {
		return left;
	}
	
	@Override
	@Field
	public R getRight () {
		return right;
	}
	
	@Override
	public R setValue (final R value) {
		throw new UnsupportedOperationException("Cannot change right object of immutable pair");
	}
	
}
