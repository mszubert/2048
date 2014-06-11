package put.ci.cevo.util;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionUtils {

	public static double sum(Collection<Double> arr) {
		double s = 0;
		for (Double a : arr) {
			s += a;
		}
		return s;
	}

	public static double average(Collection<Double> arr) {
		if (arr.size() > 0) {
			return sum(arr) / arr.size();
		} else {
			return 0;
		}
	}

	public static <T> ArrayList<T> concat(Collection<T> arr1, Collection<T> arr2) {
		ArrayList<T> res = new ArrayList<>(arr1);
		res.addAll(arr2);
		return res;
	}

}
