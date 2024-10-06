package rs.iggy.clients.blocking.tcp;

import io.netty.buffer.Unpooled;
import rs.iggy.clients.blocking.StreamsClient;
import rs.iggy.identifier.StreamId;
import rs.iggy.stream.StreamBase;
import rs.iggy.stream.StreamDetails;
import java.util.List;
import java.util.Optional;

class StreamsTcpClient implements StreamsClient {

    private static final int CREATE_STREAM_CODE = 202;
    private final TcpConnectionHandler connection;

    StreamsTcpClient(TcpConnectionHandler connection) {
        this.connection = connection;
    }

    @Override
    public void createStream(Optional<Long> streamId, String name) {
        var payloadSize = 4 + 1 + name.length();
        var payload = Unpooled.buffer(payloadSize);

        payload.writeIntLE(streamId.orElse(0L).intValue());
        payload.writeByte(name.length());
        payload.writeBytes(name.getBytes());
        connection.send(CREATE_STREAM_CODE, payload);
    }

    @Override
    public StreamDetails getStream(Long streamId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public StreamDetails getStream(StreamId streamId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<StreamBase> getStreams() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateStream(Long streamId, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateStream(StreamId streamId, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteStream(Long streamId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteStream(StreamId streamId) {
        throw new UnsupportedOperationException();
    }
}
