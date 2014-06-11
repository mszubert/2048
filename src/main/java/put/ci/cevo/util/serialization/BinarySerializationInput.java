package put.ci.cevo.util.serialization;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

public class BinarySerializationInput implements SerializationInput {

	private final DataInputStream input;

	public BinarySerializationInput(InputStream input) {
		this.input = new DataInputStream(input);
	}

	public BinarySerializationInput(FileInputStream input) {
		this(new InflaterInputStream(input));
	}

	@Override
	public int readInt() throws IOException {
		return input.readInt();
	}

	@Override
	public long readLong() throws IOException {
		return input.readLong();
	}

	@Override
	public String readString() throws IOException {
		return input.readUTF();
	}

	@Override
	public double readDouble() throws IOException {
		return input.readDouble();
	}

	@Override
	public void close() throws IOException {
		input.close();
	}

	@Override
	public int available() throws IOException {
		return input.available();
	}
}
