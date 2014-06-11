package put.ci.cevo.util.serialization.serializers;

import java.io.IOException;

import put.ci.cevo.util.serialization.ObjectSerializer;
import put.ci.cevo.util.serialization.SerializationException;
import put.ci.cevo.util.serialization.SerializationInput;
import put.ci.cevo.util.serialization.SerializationManager;
import put.ci.cevo.util.serialization.SerializationOutput;

@AutoRegistered(defaultSerializer = true)
public class IntegerSerializer implements ObjectSerializer<Integer> {

	@Override
	public void save(SerializationManager manager, Integer val, SerializationOutput output) throws IOException,
			SerializationException {
		output.writeInt(val);
	}

	@Override
	public Integer load(SerializationManager manager, SerializationInput input) throws IOException,
			SerializationException {
		return input.readInt();
	}

	@Override
	public int getUniqueSerializerId() {
		return 1;
	}
}