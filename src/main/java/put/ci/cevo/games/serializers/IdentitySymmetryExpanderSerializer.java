package put.ci.cevo.games.serializers;

import java.io.IOException;

import put.ci.cevo.games.encodings.ntuple.expanders.IdentitySymmetryExpander;
import put.ci.cevo.util.serialization.ObjectSerializer;
import put.ci.cevo.util.serialization.SerializationException;
import put.ci.cevo.util.serialization.SerializationInput;
import put.ci.cevo.util.serialization.SerializationManager;
import put.ci.cevo.util.serialization.SerializationOutput;
import put.ci.cevo.util.serialization.serializers.AutoRegistered;

@AutoRegistered(defaultSerializer = true)
public final class IdentitySymmetryExpanderSerializer implements ObjectSerializer<IdentitySymmetryExpander> {

	@Override
	public void save(SerializationManager manager, IdentitySymmetryExpander object, SerializationOutput output)
			throws IOException, SerializationException {
		// No data. Nothing required
	}

	@Override
	public IdentitySymmetryExpander load(SerializationManager manager, SerializationInput input) throws IOException,
			SerializationException {
		return new IdentitySymmetryExpander();
	}

	@Override
	public int getUniqueSerializerId() {
		return 840142292;
	}
}
