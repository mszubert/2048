package put.ci.cevo.games.serializers;

import java.io.IOException;

import put.ci.cevo.games.encodings.ntuple.expanders.StandardSymmetryExpander;
import put.ci.cevo.util.serialization.ObjectSerializer;
import put.ci.cevo.util.serialization.SerializationException;
import put.ci.cevo.util.serialization.SerializationInput;
import put.ci.cevo.util.serialization.SerializationManager;
import put.ci.cevo.util.serialization.SerializationOutput;
import put.ci.cevo.util.serialization.serializers.AutoRegistered;

@AutoRegistered(defaultSerializer = true)
public final class StandardSymmetryExpanderSerializer implements ObjectSerializer<StandardSymmetryExpander> {

	@Override
	public void save(SerializationManager manager, StandardSymmetryExpander object, SerializationOutput output)
			throws IOException, SerializationException {
		output.writeInt(object.boardSize());
	}

	@Override
	public StandardSymmetryExpander load(SerializationManager manager, SerializationInput input) throws IOException,
			SerializationException {
		int boardSize = input.readInt();
		return new StandardSymmetryExpander(boardSize);
	}

	@Override
	public int getUniqueSerializerId() {
		return 121345704;
	}
}
