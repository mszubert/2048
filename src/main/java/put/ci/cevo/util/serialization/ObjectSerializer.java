package put.ci.cevo.util.serialization;

import java.io.IOException;

public interface ObjectSerializer<T> {
	
	public void save(SerializationManager manager, T object, SerializationOutput output) throws IOException, SerializationException;

	public T load(SerializationManager manager, SerializationInput input) throws IOException, SerializationException;

	public int getUniqueSerializerId();
}
