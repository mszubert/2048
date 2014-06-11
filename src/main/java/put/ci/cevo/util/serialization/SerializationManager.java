package put.ci.cevo.util.serialization;

import static org.apache.commons.io.FileUtils.openInputStream;
import static org.apache.commons.io.FileUtils.openOutputStream;
import static org.apache.commons.lang3.ClassUtils.getAllInterfaces;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jodah.typetools.TypeResolver;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public final class SerializationManager {

	private static final Logger logger = Logger.getLogger(SerializationManager.class);

	private final Map<Type, ObjectSerializer<? extends Object>> defaultSerializers = new HashMap<>();
	private final Map<Integer, ObjectSerializer<? extends Object>> deserializers = new HashMap<>();

	/**
	 * Instantiate only using {@link SerializationManagerFactory#create()}
	 */
	protected SerializationManager() {
		// forbid direct construction
	}

	public SerializationManager(Iterable<ObjectSerializer<?>> serializers) throws SerializationException {
		for (ObjectSerializer<?> serializer : serializers) {
			register(serializer);
		}
	}

	/**
	 * Registers serializer and deserializer. A warning is logged if serializer for the same type already existed
	 */
	public <T> void register(ObjectSerializer<T> serializer) throws SerializationException {
		registerSerializer(serializer);
		registerDeserializer(serializer);
	}

	/**
	 * Used to register old serializers. They will be used only for deserialization, never for serialization.
	 */
	public <T> void registerAdditional(ObjectSerializer<T> serializer) throws SerializationException {
		registerDeserializer(serializer);
	}

	/**
	 * Allows to override any automatically registered serializers. Use with caution!
	 */
	public <T> void registerOverride(ObjectSerializer<T> serializer) {
		ObjectSerializer<?> previousSerializer = defaultSerializers
			.put(getSerializerObjectType(serializer), serializer);
		ObjectSerializer<?> previousDeserializer = deserializers.put(serializer.getUniqueSerializerId(), serializer);

		if (previousSerializer != null) {
			logger.warn("Overriding serializer: " + previousSerializer + " with: " + serializer);
		}
		if (previousDeserializer != null) {
			logger.warn("Overriding deserializer: " + previousDeserializer + " with: " + serializer);
		}
	}

	/**
	 * Unregister a serializer (and a deserializer). Unregistration is based on Serializer's id and generic param type.
	 */
	public <T> void unregister(ObjectSerializer<T> serializer) {
		defaultSerializers.remove(getSerializerObjectType(serializer));
		deserializers.remove(serializer.getUniqueSerializerId());
	}

	/**
	 * Serialize object to output
	 */
	public <T> void serialize(T object, SerializationOutput output) throws SerializationException {
		Preconditions.checkNotNull(object);
		serialize(object, resolveSerializer(object.getClass()), output);
	}

	public <T> void serializeStream(Iterable<T> object, SerializationOutput output) throws SerializationException {
		for (T o : object) {
			serialize(o, output);
		}
	}

	/**
	 * Serialize object to a file using {@link SerializationManager#serialize(Object, File)} and wrap all exceptions
	 * into a {@link RuntimeException}.
	 */
	public <T> void serializeWrapExceptions(T object, File file) {
		try {
			serialize(object, file);
		} catch (SerializationException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Serialize object to file with BinarySerializator.
	 */
	public <T> void serialize(T object, File file) throws SerializationException {
		Preconditions.checkNotNull(object);
		try (BinarySerializationOutput output = new BinarySerializationOutput(openOutputStream(file))) {
			serialize(object, output);
		} catch (IOException e) {
			throw new SerializationException("Cound to close file: " + file.toString(), e);
		}
	}

	/**
	 * Deserialize object from input and return it
	 */
	@SuppressWarnings("unchecked")
	public <T> T deserialize(SerializationInput input) throws SerializationException {
		try {
			int id = input.readInt();
			ObjectSerializer<?> deserializer = deserializers.get(id);
			if (deserializer == null) {
				throw new SerializationException("Deserializator with id " + id + " was not registered");
			}
			return (T) deserializer.load(this, input);
		} catch (IOException e) {
			throw new SerializationException("Failed to deserialize object", e);
		}
	}

	/**
	 * Deserialize object from a file using {@link SerializationManager#deserialize(File)} and wrap all exceptions into
	 * a {@link RuntimeException}.
	 */
	public <T> T deserializeWrapExceptions(File file) {
		try {
			return deserialize(file);
		} catch (SerializationException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Deserialize object from file with BinarySerializator
	 */
	public <T> T deserialize(File file) throws SerializationException {
		try (BinarySerializationInput input = new BinarySerializationInput(openInputStream(file))) {
			return deserialize(input);
		} catch (IOException e) {
			throw new SerializationException("Could not deserialize from file: " + file.toString(), e);
		}
	}

	/**
	 * Serialize with a explicitly given serializer. Just for testing.
	 */
	<T> void serialize(T object, ObjectSerializer<T> serializer, SerializationOutput output)
			throws SerializationException {
		try {
			output.writeInt(serializer.getUniqueSerializerId());
			serializer.save(this, object, output);
		} catch (IOException e) {
			throw new SerializationException("Could not serialize object", e);
		}
	}

	/**
	 * Attempts to resolve a serializer by the class name. In case a dedicated serializer is not found, all of
	 * interfaces implemented by the target class are scanned for registered default serializers. However, when multiple
	 * top-level interface serializers are found, an exception is thrown as there is currently no way to accurately
	 * choose the right one.
	 */
	@SuppressWarnings("unchecked")
	private <T> ObjectSerializer<T> resolveSerializer(Class<? extends Object> clazz) throws SerializationException {
		ObjectSerializer<T> serializer = (ObjectSerializer<T>) defaultSerializers.get(clazz);
		if (serializer != null) {
			return serializer;
		}
		List<ObjectSerializer<T>> matchingSerializers = Lists.newArrayList();
		for (Class<?> implementedInterface : getAllInterfaces(clazz)) {
			serializer = (ObjectSerializer<T>) defaultSerializers.get(implementedInterface);
			if (serializer != null) {
				matchingSerializers.add(serializer);
			}
		}
		if (matchingSerializers.size() == 1) {
			return matchingSerializers.iterator().next();
		}
		throw new SerializationException("No serializer has been registered for type: " + clazz
			+ ". Matching default interface serializers found: " + matchingSerializers);
	}

	private <T> void registerSerializer(ObjectSerializer<T> serializer) {
		Type type = getSerializerObjectType(serializer);

		if (defaultSerializers.containsKey(defaultSerializers.containsKey(type))) {
			logger.warn("Registering serializer " + serializer.getClass().getName()
				+ " which overrides previously registered serializer for class " + type);
		}
		defaultSerializers.put(type, serializer);
	}

	private <T> void registerDeserializer(ObjectSerializer<T> serializer) throws SerializationException {
		if (deserializers.containsKey(serializer.getUniqueSerializerId())) {
			throw new SerializationException("Cannot register " + serializer.getClass().getName()
				+ " because other serializer with this Id has been already registered for deserialization");
		}
		deserializers.put(serializer.getUniqueSerializerId(), serializer);
	}

	/**
	 * Returns type of T in ObjectSerializer<T>
	 */
	private <T> Class<?> getSerializerObjectType(ObjectSerializer<T> serializer) {
		return TypeResolver.resolveRawArgument(ObjectSerializer.class, serializer.getClass());
	}
}
