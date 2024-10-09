package rs.iggy.clients.blocking.tcp;

import rs.iggy.clients.blocking.PartitionsClient;
import rs.iggy.identifier.StreamId;
import rs.iggy.identifier.TopicId;
import static rs.iggy.clients.blocking.tcp.BytesSerializer.toBytes;

class PartitionsTcpClient implements PartitionsClient {

    private static final int CREATE_PARTITION_CODE = 402;
    private static final int DELETE_PARTITION_CODE = 403;
    private final TcpConnectionHandler connection;

    PartitionsTcpClient(TcpConnectionHandler connection) {
        this.connection = connection;
    }

    @Override
    public void createPartitions(StreamId streamId, TopicId topicId, Long partitionsCount) {
        var payload = toBytes(streamId);
        payload.writeBytes(toBytes(topicId));
        payload.writeIntLE(partitionsCount.intValue());
        connection.send(CREATE_PARTITION_CODE, payload);
    }

    @Override
    public void deletePartitions(StreamId streamId, TopicId topicId, Long partitionsCount) {
        var payload = toBytes(streamId);
        payload.writeBytes(toBytes(topicId));
        payload.writeIntLE(partitionsCount.intValue());
        connection.send(DELETE_PARTITION_CODE, payload);
    }
}
