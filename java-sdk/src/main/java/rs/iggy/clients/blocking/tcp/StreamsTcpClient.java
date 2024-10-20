package rs.iggy.clients.blocking.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import rs.iggy.clients.blocking.StreamsClient;
import rs.iggy.identifier.StreamId;
import rs.iggy.stream.StreamBase;
import rs.iggy.stream.StreamDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static rs.iggy.clients.blocking.tcp.BytesDeserializer.readStreamBase;
import static rs.iggy.clients.blocking.tcp.BytesDeserializer.readStreamDetails;
import static rs.iggy.clients.blocking.tcp.BytesSerializer.nameToBytes;
import static rs.iggy.clients.blocking.tcp.BytesSerializer.toBytes;

class StreamsTcpClient implements StreamsClient {

    private static final int GET_STREAM_CODE = 200;
    private static final int GET_STREAMS_CODE = 201;
    private static final int CREATE_STREAM_CODE = 202;
    private static final int DELETE_STREAM_CODE = 203;
    private static final int UPDATE_STREAM_CODE = 204;
    private final TcpConnectionHandler connection;

    StreamsTcpClient(TcpConnectionHandler connection) {
        this.connection = connection;
    }

    @Override
    public StreamDetails createStream(Optional<Long> streamId, String name) {
        var payloadSize = 4 + 1 + name.length();
        var payload = Unpooled.buffer(payloadSize);

        payload.writeIntLE(streamId.orElse(0L).intValue());
        payload.writeBytes(nameToBytes(name));
        var response = connection.send(CREATE_STREAM_CODE, payload);
        return readStreamDetails(response);
    }

    @Override
    public Optional<StreamDetails> getStream(StreamId streamId) {
        var payload = toBytes(streamId);
        var response = connection.send(GET_STREAM_CODE, payload);
        if (response.isReadable()) {
            return Optional.of(readStreamDetails(response));
        }
        return Optional.empty();
    }

    @Override
    public List<StreamBase> getStreams() {
        ByteBuf response = connection.send(GET_STREAMS_CODE);
        List<StreamBase> streams = new ArrayList<>();
        while (response.isReadable()) {
            streams.add(readStreamBase(response));
        }
        return streams;
    }

    @Override
    public void updateStream(StreamId streamId, String name) {
        var payloadSize = 1 + name.length();
        var idBytes = toBytes(streamId);
        var payload = Unpooled.buffer(payloadSize + idBytes.capacity());

        payload.writeBytes(idBytes);
        payload.writeBytes(nameToBytes(name));
        connection.send(UPDATE_STREAM_CODE, payload);
    }

    @Override
    public void deleteStream(StreamId streamId) {
        var payload = toBytes(streamId);
        connection.send(DELETE_STREAM_CODE, payload);
    }

}
