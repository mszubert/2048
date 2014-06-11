package put.ci.cevo.util.serialization;

public class SerializationException extends Exception {

	private static final long serialVersionUID = 2369185289909583646L;

	public SerializationException() {
		super();
	}

	public SerializationException(String message) {
		super(message);
	}

	public SerializationException(String message, Throwable e) {
		super(message, e);
	}
}
