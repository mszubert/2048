package put.ci.cevo.util.serialization.serializers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import put.ci.cevo.util.serialization.ObjectSerializer;
import put.ci.cevo.util.serialization.SerializationException;
import put.ci.cevo.util.serialization.SerializationInput;
import put.ci.cevo.util.serialization.SerializationManager;
import put.ci.cevo.util.serialization.SerializationOutput;

@AutoRegistered(defaultSerializer = true)
public class ListSerializer<T> implements ObjectSerializer<List<T>> {

	@Override
	public void save(SerializationManager manager, List<T> arr, SerializationOutput output) throws IOException,
			SerializationException {
		output.writeInt(arr.size());
		for (T element : arr) {
			manager.serialize(element, output);
		}
	}

	@Override
	public List<T> load(SerializationManager manager, SerializationInput input) throws IOException,
			SerializationException {
		int len = input.readInt();
		List<T> list = new ArrayList<T>(len);
		for (int i = 0; i < len; ++i) {
			list.add(manager.<T> deserialize(input));
		}
		return list;
	}

	@Override
	public int getUniqueSerializerId() {
		return 123499;
	}
}