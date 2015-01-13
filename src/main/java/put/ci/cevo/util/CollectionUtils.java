package put.ci.cevo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtils {

	public static double sum(Collection<Double> arr) {
		double s = 0;
		for (Double a : arr) {
			s += a;
		}
		return s;
	}

	public static <T> List<T> flatten(Collection<? extends Collection<T>> arr) {
		ArrayList<T> res = new ArrayList<>();
		for (Collection<T> list : arr) {
			res.addAll(list);
		}
		return res;
	}

	public static <T> ArrayList<T> concat(Collection<T> arr1, Collection<T> arr2) {
		ArrayList<T> res = new ArrayList<>(arr1);
		res.addAll(arr2);
		return res;
	}

}
