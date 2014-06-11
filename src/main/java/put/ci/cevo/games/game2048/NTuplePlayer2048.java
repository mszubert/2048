package put.ci.cevo.games.game2048;

import java.io.File;
import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import put.ci.cevo.games.encodings.ntuple.NTuples;
import put.ci.cevo.games.serializers.IdentitySymmetryExpanderSerializer;
import put.ci.cevo.games.serializers.NTupleSerializer;
import put.ci.cevo.games.serializers.NTuplesSerializer;
import put.ci.cevo.games.serializers.StandardSymmetryExpanderSerializer;
import put.ci.cevo.rl.environment.Transition;
import put.ci.cevo.util.Pair;
import put.ci.cevo.util.serialization.SerializationManager;
import put.ci.cevo.util.serialization.SerializationManagerFactory;

import com.google.common.collect.Lists;

public class NTuplePlayer2048 implements Player2048 {

	@SuppressWarnings("unchecked")
	private final static SerializationManager serializer = SerializationManagerFactory.create(Lists.newArrayList(
		new NTuplesSerializer(), new NTupleSerializer(), new IdentitySymmetryExpanderSerializer(),
		new StandardSymmetryExpanderSerializer()));
	
	private final static Game2048 game = new Game2048();

	private NTuples ntuples;

	public NTuplePlayer2048(NTuples ntuples) {
		this.ntuples = ntuples;
	}

	@Override
	public Action2048 chooseAction(State2048 state, List<Action2048> actions) {
		Action2048 bestAction = null;
		double bestValue = Double.NEGATIVE_INFINITY;

		for (Action2048 action : actions) {
			Transition<State2048, Action2048> transition = game.computeTransition(state, action);
			double value = transition.getReward() + ntuples.getValue(transition.getAfterState().getFeatures());
			if (value > bestValue) {
				bestAction = action;
				bestValue = value;
			}
		}

		return bestAction;
	}

	public String chooseAction(double[] features) {
		State2048 state = new State2048(features);
		List<Action2048> actions = game.getPossibleActions(state);
		Action2048 chosenAction = chooseAction(state, actions);
		return chosenAction.toString();
	}

	public static NTuplePlayer2048 readPlayer(File file) {
		NTuples ntuples = serializer.deserializeWrapExceptions(file);
		return new NTuplePlayer2048(ntuples);
	}

	public void evaluate(int numGames, RandomDataGenerator random) {
		double wonGames = 0;
		SummaryStatistics stats = new SummaryStatistics();
		for (int j = 0; j <= numGames; j++) {
			Pair<Integer, Integer> res = game.playGame(this, random);
			if (res.second() >= 2048) {
				wonGames += 1.0;
			}
			stats.addValue(res.first());
		}

		System.out.println(stats.getMean());
		System.out.println(stats.getStandardDeviation());

		System.out.println(wonGames / numGames);
	}

	public static void main(String[] args) {
		NTuplePlayer2048 player = readPlayer(new File(args[0]));
		player.evaluate(1000, new RandomDataGenerator());
	}
}
