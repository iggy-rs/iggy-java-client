package rs.iggy.clients.blocking.tcp;

import io.netty.buffer.Unpooled;
import rs.iggy.clients.blocking.TopicsClient;
import rs.iggy.identifier.StreamId;
import rs.iggy.identifier.TopicId;
import rs.iggy.topic.CompressionAlgorithm;
import rs.iggy.topic.Topic;
import rs.iggy.topic.TopicDetails;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static rs.iggy.clients.blocking.tcp.BytesDeserializer.readTopic;
import static rs.iggy.clients.blocking.tcp.BytesDeserializer.readTopicDetails;
import static rs.iggy.clients.blocking.tcp.BytesSerializer.nameToBytes;
import static rs.iggy.clients.blocking.tcp.BytesSerializer.toBytes;

class TopicsTcpClient implements TopicsClient {

    private static final int GET_TOPIC_CODE = 300;
    private static final int GET_TOPICS_CODE = 301;
    private static final int CREATE_TOPIC_CODE = 302;
    private static final int DELETE_TOPIC_CODE = 303;
    private static final int UPDATE_TOPIC_CODE = 304;
    private static final int PURGE_TOPIC_CODE = 305;
    private final TcpConnectionHandler connection;

    TopicsTcpClient(TcpConnectionHandler connection) {
        this.connection = connection;
    }

    @Override
    public TopicDetails getTopic(StreamId streamId, TopicId topicId) {
        var payload = toBytes(streamId);
        payload.writeBytes(toBytes(topicId));
        var response = connection.send(GET_TOPIC_CODE, payload);
        return readTopicDetails(response);
    }

    @Override
    public List<Topic> getTopics(StreamId streamId) {
        var payload = toBytes(streamId);
        var response = connection.send(GET_TOPICS_CODE, payload);
        List<Topic> topics = new ArrayList<>();
        while (response.isReadable()) {
            topics.add(readTopic(response));
        }
        return topics;
    }

    @Override
    public void createTopic(StreamId streamId, Optional<Long> topicId, Long partitionsCount, CompressionAlgorithm compressionAlgorithm, BigInteger messageExpiry, BigInteger maxTopicSize, Optional<Short> replicationFactor, String name) {
        var streamIdBytes = toBytes(streamId);
        var payload = Unpooled.buffer(23 + streamIdBytes.capacity() + name.length());

        payload.writeBytes(streamIdBytes);
        payload.writeIntLE(topicId.orElse(0L).intValue());
        payload.writeIntLE(partitionsCount.intValue());
        payload.writeByte(compressionAlgorithm.asCode());
        payload.writeBytes(BytesSerializer.toBytesAsU64(messageExpiry));
        payload.writeBytes(BytesSerializer.toBytesAsU64(maxTopicSize));
        payload.writeByte(replicationFactor.orElse((short) 0));
        payload.writeBytes(nameToBytes(name));

        var response = connection.send(CREATE_TOPIC_CODE, payload);
        //TODO(mm): 9.10.2024 return topic details
    }


    @Override
    public void updateTopic(StreamId streamId, TopicId topicId, Optional<Long> messageExpiry, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteTopic(StreamId streamId, TopicId topicId) {
        var payload = toBytes(streamId);
        payload.writeBytes(toBytes(topicId));
        connection.send(DELETE_TOPIC_CODE, payload);
    }
}
