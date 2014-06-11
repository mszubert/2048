package put.ci.cevo.util.serialization.serializers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import put.ci.cevo.util.serialization.ObjectSerializer;
import put.ci.cevo.util.serialization.SerializationException;
import put.ci.cevo.util.serialization.SerializationInput;
import put.ci.cevo.util.serialization.SerializationManager;
import put.ci.cevo.util.serialization.SerializationOutput;

@AutoRegistered(defaultSerializer = true)
public class MapSerializer<K, V> implements ObjectSerializer<Map<K, V>> {

	@Override
	public void save(SerializationManager manager, Map<K, V> map, SerializationOutput output) throws IOException,
			SerializationException {
		output.writeInt(map.entrySet().size());
		for (Entry<K, V> element : map.entrySet()) {
			manager.serialize(element.getKey(), output);
			manager.serialize(element.getValue(), output);
		}
	}

	@Override
	public Map<K, V> load(SerializationManager manager, SerializationInput input) throws IOException,
			SerializationException {
		int size = input.readInt();
		HashMap<K, V> map = new HashMap<K, V>(size * 2);
		for (int i = 0; i < size; ++i) {
			K key = manager.deserialize(input);
			V value = manager.deserialize(input);
			map.put(key, value);
		}
		return map;
	}

	@Override
	public int getUniqueSerializerId() {
		return 17;
	}
}