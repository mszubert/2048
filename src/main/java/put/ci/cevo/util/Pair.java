package put.ci.cevo.util;

import static com.google.common.base.Objects.toStringHelper;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Pair<F, S> implements Serializable {

	private static final long serialVersionUID = 20130620184644L;

	public static class PairComparator<F extends Comparable<? super F>, S extends Comparable<? super S>> implements
			Comparator<Pair<F, S>> {
		@Override
		public int compare(Pair<F, S> p1, Pair<F, S> p2) {
			final int firstCompare = p1.first().compareTo(p2.first());
			return firstCompare != 0 ? firstCompare : p1.second().compareTo(p2.second());
		}
	}

	public static class ReversePairComparator<F extends Comparable<? super F>, S extends Comparable<? super S>>
			implements Comparator<Pair<F, S>> {
		@Override
		public int compare(Pair<F, S> p1, Pair<F, S> p2) {
			final int secondCompare = p1.second().compareTo(p2.second());
			return secondCompare != 0 ? secondCompare : p1.first().compareTo(p2.first());
		}
	}

	private final F first;
	private final S second;

	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	public F first() {
		return first;
	}

	public S second() {
		return second;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (first == null ? 0 : first.hashCode());
		result = PRIME * result + (second == null ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || this.getClass() != other.getClass()) {
			return false;
		}
		final Pair<?, ?> otherPair = (Pair<?, ?>) other;
		return Objects.equals(first, otherPair.first) && Objects.equals(second, otherPair.second);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("first", first).add("second", second).toString();
	}

	public static <F, S> Pair<F, S> create(F first, S second) {
		return new Pair<F, S>(first, second);
	}
}
