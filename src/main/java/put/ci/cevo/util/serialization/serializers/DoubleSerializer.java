package put.ci.cevo.util.serialization.serializers;

import java.io.IOException;

import put.ci.cevo.util.serialization.ObjectSerializer;
import put.ci.cevo.util.serialization.SerializationException;
import put.ci.cevo.util.serialization.SerializationInput;
import put.ci.cevo.util.serialization.SerializationManager;
import put.ci.cevo.util.serialization.SerializationOutput;

@AutoRegistered(defaultSerializer = true)
public class DoubleSerializer implements ObjectSerializer<Double> {

	@Override
	public void save(SerializationManager manager, Double object, SerializationOutput output) throws IOException,
			SerializationException {
		output.writeDouble(object);
	}

	@Override
	public Double load(SerializationManager manager, SerializationInput input) throws IOException,
			SerializationException {
		return input.readDouble();
	}

	@Override
	public int getUniqueSerializerId() {
		return 2013071215;
	}

}
