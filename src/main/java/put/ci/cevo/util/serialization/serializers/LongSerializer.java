package put.ci.cevo.util.serialization.serializers;

import java.io.IOException;

import put.ci.cevo.util.serialization.ObjectSerializer;
import put.ci.cevo.util.serialization.SerializationException;
import put.ci.cevo.util.serialization.SerializationInput;
import put.ci.cevo.util.serialization.SerializationManager;
import put.ci.cevo.util.serialization.SerializationOutput;

@AutoRegistered(defaultSerializer = true)
public class LongSerializer implements ObjectSerializer<Long> {

	@Override
	public void save(SerializationManager manager, Long object, SerializationOutput output) throws IOException,
			SerializationException {
		output.writeLong(object);
	}

	@Override
	public Long load(SerializationManager manager, SerializationInput input) throws IOException, SerializationException {
		return input.readLong();
	}

	@Override
	public int getUniqueSerializerId() {
		return 2013080110;
	}

}
