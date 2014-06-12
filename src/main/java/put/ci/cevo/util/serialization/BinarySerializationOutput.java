package put.ci.cevo.util.serialization;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;

/**
 * Used for binary serialization
 */
public class BinarySerializationOutput implements SerializationOutput {

	private final DataOutputStream output;

	public BinarySerializationOutput(FileOutputStream output) {
		this(new DeflaterOutputStream(output));
	}

	public BinarySerializationOutput(OutputStream output) {
		this.output = new DataOutputStream(output);
	}

	@Override
	public void writeInt(int value) throws IOException {
		output.writeInt(value);
	}

	@Override
	public void writeLong(long value) throws IOException {
		output.writeLong(value);
	}

	@Override
	public void writeString(String value) throws IOException {
		output.writeUTF(value);
	}

	@Override
	public void writeDouble(double value) throws IOException {
		output.writeDouble(value);
	}

	@Override
	public void close() throws IOException {
		output.close();
	}
}
