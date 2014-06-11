package put.ci.cevo.util;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;

public class ArrayUtils {
	/**
	 * Returns a string representation of 2D array such that it can be used as Java code
	 */
	public static String toJavaArray(int[][] arr) {
		StringBuilder str = new StringBuilder();
		str.append("new int[][] {\n");
		for (int i = 0; i < arr.length; i++) {
			str.append("{");
			for (int j = 0; j < arr.length; j++) {
				str.append(arr[i][j] + ",");
			}
			str.append("},\n");
		}
		str.append("};");
		return str.toString();
	}

	/**
	 * Returns a string representation of array such that it can be used as Java code
	 */
	public static String toJavaArray(double[] arr) {
		StringBuilder str = new StringBuilder();
		str.append("new double[] { ");
		for (int i = 0; i < arr.length; i++) {
			if (i < arr.length - 1) {
				str.append(arr[i] + ",");
			} else {
				str.append(arr[i]);
			}
		}
		str.append(" }");
		return str.toString();
	}

	/** Computes the number of elements in 2d array */
	private static int numElements(double[][] arr) {
		int count = 0;
		for (int i = 0; i < arr.length; ++i) {
			count += arr[i].length;
		}
		return count;
	}

	/** Converts 2d array to 1d array */
	public static double[] flatten(double[][] arr) {
		// TODO: Performance can be improved
		double[] result = new double[numElements(arr)];
		int index = 0;
		for (int i = 0; i < arr.length; ++i) {
			for (int j = 0; j < arr[i].length; ++j) {
				result[index++] = arr[i][j];
			}
		}
		return result;
	}

	public static int[][] transposed(int[][] arr) {
		Preconditions.checkArgument(arr.length > 0);

		int n = arr.length;
		int m = arr[0].length;

		assert m > 0;

		int[][] tran = new int[m][n];
		for (int i = 0; i < n; ++i) {
			assert arr[i].length == m;
			for (int j = 0; j < m; ++j) {
				tran[j][i] = arr[i][j];
			}
		}
		return tran;
	}

	public static int[] sorted(int[] arr) {
		return sorted(arr, arr.length);
	}

	/**
	 * Returns sorted(arr[0:numElements))
	 */
	public static int[] sorted(int[] arr, int numElements) {
		Preconditions.checkArgument(numElements <= arr.length);
		int[] copy = Arrays.copyOf(arr, numElements);
		Arrays.sort(copy);
		return copy;
	}

	public static double sum(double[] arr) {
		double s = 0;
		for (double a : arr) {
			s += a;
		}
		return s;
	}

	public static double mean(double[] arr) {
		return sum(arr) / arr.length;
	}

	/**
	 * e.g. absmax([-1,2,-4])=-4
	 */
	public static double absmax(double[] arr) {
		Preconditions.checkNotNull(arr);
		Preconditions.checkArgument(arr.length > 0);
		double max = arr[0];
		for (int i = 0; i < arr.length; ++i) {
			if (Math.abs(max) < Math.abs(arr[i])) {
				max = arr[i];
			}
		}
		return max;
	}

	/**
	 * Returns normalized vector, i.e. such that maximum abs = 1.0. If all values = 0 returns a cloned array
	 */
	public static double[] normalized(double[] arr) {
		double absmax = ArrayUtils.absmax(arr);
		absmax = Math.abs(absmax);
		if (absmax == 0.0) {
			return arr.clone();
		}

		double[] normalizedWeights = arr.clone();
		for (int i = 0; i < arr.length; ++i) {
			normalizedWeights[i] /= absmax;
		}
		return normalizedWeights;
	}

	/**
	 * @param string
	 *            which looks like "[0.123, 0.153, -10.1255523, (...),]"
	 */
	public static double[] fromPythonString(String string) {
		string = StringUtils.remove(string, '[');
		string = StringUtils.remove(string, ']');
		String[] cells = StringUtils.split(string, ',');
		double[] values = new double[cells.length];
		for (int i = 0; i < values.length; ++i) {
			values[i] = Double.parseDouble(StringUtils.strip(cells[i]));
		}
		return values;
	}

	public static String toPythonString(double[] arr) {
		return "[" + Doubles.join(", ", arr) + "]";
	}

	public static double[] add(double[] a, double[] b) {
		Preconditions.checkArgument(a.length == b.length);
		double[] res = new double[a.length];
		for (int i = 0; i < a.length; ++i) {
			res[i] = a[i] + b[i];
		}
		return res;
	}

	public static double[] substract(double[] a, double[] b) {
		Preconditions.checkArgument(a.length == b.length);
		double[] res = new double[a.length];
		for (int i = 0; i < a.length; ++i) {
			res[i] = a[i] - b[i];
		}
		return res;
	}

	public static double[] multiplied(double[] a, double multiplier) {
		double[] res = new double[a.length];
		for (int i = 0; i < a.length; ++i) {
			res[i] = a[i] * multiplier;
		}
		return res;
	}
}
