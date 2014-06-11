package put.ci.cevo.games.encodings.ntuple;

import static put.ci.cevo.games.encodings.ntuple.NTupleUtils.createSymmetric;
import static put.ci.cevo.util.CollectionUtils.concat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import put.ci.cevo.games.encodings.ntuple.eval.DefaultNTupleEvaluator;
import put.ci.cevo.games.encodings.ntuple.expanders.IdentitySymmetryExpander;
import put.ci.cevo.games.encodings.ntuple.expanders.SymmetryExpander;
import put.ci.cevo.games.game2048.Game2048Board;
import put.ci.cevo.rl.agent.functions.RealFunction;

import com.carrotsearch.hppc.DoubleArrayList;

public class NTuples implements Iterable<NTuple>, Serializable, RealFunction {

	/** A class helping in building NTuples object */
	public static class Builder {
		private final List<NTuple> tuples = new ArrayList<NTuple>();

		private final SymmetryExpander expander;

		public Builder(SymmetryExpander expander) {
			this.expander = expander;
		}

		public void add(NTuple tuple) {
			tuples.add(tuple);
		}

		public NTuples build() {
			return new NTuples(tuples, expander);
		}
	}

	private static final long serialVersionUID = -3843856387088040436L;

	// AllNTuples contain tuples equal to mainNTuples (those are different objects, but are exactly equal).
	// Moreover, the weights (LUT) are shared between allNTuples and mainNTuples.
	private final List<NTuple> allNTuples;
	private final List<NTuple> mainNTuples;

	private final SymmetryExpander symmetryExpander;

	/** No symmetry */
	public NTuples(List<NTuple> tuples) {
		this(tuples, new IdentitySymmetryExpander());
	}

	public NTuples(NTuples ntuples) {
		this(ntuples.mainNTuples, ntuples.symmetryExpander);
	}

	/** Creates a n-tuples system where each tuple from tuples is expanded by expander */
	public NTuples(List<NTuple> tuples, SymmetryExpander expander) {
		// Defensive copy
		mainNTuples = new ArrayList<NTuple>();
		for (NTuple tuple : tuples) {
			mainNTuples.add(new NTuple(tuple));
		}
		this.allNTuples = new ArrayList<>();
		for (NTuple mainTuple : mainNTuples) {
			List<NTuple> symmetric = createSymmetric(mainTuple, expander);
			assert symmetric.get(0).equals(mainTuple);
			allNTuples.addAll(symmetric);
		}
		this.symmetryExpander = expander;
	}

	public NTuples add(NTuples other) {
		return new NTuples(concat(getAll(), other.getAll()));
	}

	public List<NTuple> getMain() {
		return mainNTuples;
	}

	public List<NTuple> getAll() {
		return allNTuples;
	}

	public NTuple getTuple(int idx) {
		return allNTuples.get(idx);
	}

	public int totalWeights() {
		return weights().length;
	}

	public double[] weights() {
		DoubleArrayList weights = new DoubleArrayList();
		for (NTuple tuple : mainNTuples) {
			weights.add(tuple.getWeights());
		}
		return weights.toArray();
	}

	public SymmetryExpander getSymmetryExpander() {
		return symmetryExpander;
	}

	@Override
	public Iterator<NTuple> iterator() {
		return allNTuples.iterator();
	}

	public int size() {
		return allNTuples.size();
	}

	@Override
	public int hashCode() {
		return Objects.hash(allNTuples);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		NTuples other = (NTuples) obj;
		return allNTuples.equals(other.allNTuples);
	}

	@Override
	public String toString() {
		return "NTuples [mainNTuples=" + mainNTuples + ", symmetryExpander=" + symmetryExpander + "]";
	}

	@Override
	public double getValue(double[] input) {
		DefaultNTupleEvaluator evaluator = new DefaultNTupleEvaluator();

		return evaluator.evaluate(this, new Game2048Board(input));
	}

	@Override
	public void update(double[] input, double expectedValue, double learningRate) {
		DefaultNTupleEvaluator evaluator = new DefaultNTupleEvaluator();

		double val = evaluator.evaluate(this, new Game2048Board(input));

		double error = expectedValue - val;

		double delta = error * learningRate;

        Game2048Board board = new Game2048Board(input);
		for (NTuple tuple : getAll()) {
			tuple.getWeights()[tuple.address(board)] += delta;
		}
	}
}
