package put.ci.cevo.util.serialization;

import java.io.IOException;

public interface SerializationOutput extends AutoCloseable {
	void writeInt(int value) throws IOException;

	void writeLong(long value) throws IOException;

	void writeString(String value) throws IOException;

	void writeDouble(double value) throws IOException;
}
