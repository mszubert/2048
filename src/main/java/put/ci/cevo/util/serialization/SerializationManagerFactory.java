package put.ci.cevo.util.serialization;

import java.util.List;

import org.apache.log4j.Logger;

import put.ci.cevo.util.serialization.serializers.DoubleArraySerializer;
import put.ci.cevo.util.serialization.serializers.DoubleSerializer;
import put.ci.cevo.util.serialization.serializers.IntArraySerializer;
import put.ci.cevo.util.serialization.serializers.IntegerSerializer;
import put.ci.cevo.util.serialization.serializers.ListSerializer;

public class SerializationManagerFactory {

	private static final Logger logger = Logger.getLogger(SerializationManagerFactory.class);

	public static SerializationManager create(List<ObjectSerializer<?>> serializers) {

		SerializationManager manager = new SerializationManager();
		serializers.add(new DoubleArraySerializer());
		serializers.add(new DoubleSerializer());
		serializers.add(new IntArraySerializer());
		serializers.add(new IntegerSerializer());
		serializers.add(new ListSerializer());

		for (ObjectSerializer<?> serializer : serializers) {
			try {
				manager.register(serializer);
			} catch (SerializationException e) {
				logger.error("Unable to register serializer: " + serializer.getClass().getName(), e);
			}
		}
		return manager;
	}
}
