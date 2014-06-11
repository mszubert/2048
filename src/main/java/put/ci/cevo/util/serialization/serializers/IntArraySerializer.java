package put.ci.cevo.util.serialization.serializers;

import java.io.IOException;

import put.ci.cevo.util.serialization.ObjectSerializer;
import put.ci.cevo.util.serialization.SerializationException;
import put.ci.cevo.util.serialization.SerializationInput;
import put.ci.cevo.util.serialization.SerializationManager;
import put.ci.cevo.util.serialization.SerializationOutput;

@AutoRegistered(defaultSerializer = true)
public class IntArraySerializer implements ObjectSerializer<int[]> {

	@Override
	public void save(SerializationManager manager, int[] arr, SerializationOutput output) throws IOException {
		output.writeInt(arr.length);
		for (int element : arr) {
			output.writeInt(element);
		}
	}

	@Override
	public int[] load(SerializationManager manager, SerializationInput input) throws IOException,
			SerializationException {
		int len = input.readInt();
		int[] arr = new int[len];
		for (int i = 0; i < len; ++i) {
			arr[i] = input.readInt();
		}
		return arr;
	}

	@Override
	public int getUniqueSerializerId() {
		return 14;
	}
}