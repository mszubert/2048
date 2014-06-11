package put.ci.cevo.games.serializers;

import java.io.IOException;

import put.ci.cevo.games.encodings.ntuple.NTuple;
import put.ci.cevo.util.serialization.ObjectSerializer;
import put.ci.cevo.util.serialization.SerializationException;
import put.ci.cevo.util.serialization.SerializationInput;
import put.ci.cevo.util.serialization.SerializationManager;
import put.ci.cevo.util.serialization.SerializationOutput;
import put.ci.cevo.util.serialization.serializers.AutoRegistered;

@AutoRegistered(defaultSerializer = true)
public final class NTupleSerializer implements ObjectSerializer<NTuple> {

	@Override
	public void save(SerializationManager manager, NTuple object, SerializationOutput output) throws IOException,
			SerializationException {
		output.writeInt(object.getNumValues());
		manager.serialize(object.getLocations(), output);
		manager.serialize(object.getWeights(), output);
	}

	@Override
	public NTuple load(SerializationManager manager, SerializationInput input) throws IOException,
			SerializationException {
		int numValues = input.readInt();
		int[] locations = manager.deserialize(input);
		double[] weights = manager.deserialize(input);
		return new NTuple(numValues, locations, weights);
	}

	@Override
	public int getUniqueSerializerId() {
		return 570718399;
	}
}
