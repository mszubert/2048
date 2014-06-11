package put.ci.cevo.util;

import org.apache.commons.math3.random.RandomDataGenerator;

/*
 * Similar to <code>Factory<T></code> from apache commons
 */
public interface RandomFactory<T> {
	/**
	 * Create a new object.
	 * 
	 * @return a new object
	 */
	public T create(RandomDataGenerator random);
}
