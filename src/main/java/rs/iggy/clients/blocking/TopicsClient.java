package rs.iggy.clients.blocking;

import rs.iggy.identifier.StreamId;
import rs.iggy.identifier.TopicId;
import rs.iggy.topic.CompressionAlgorithm;
import rs.iggy.topic.Topic;
import rs.iggy.topic.TopicDetails;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface TopicsClient {

    TopicDetails getTopic(Long streamId, Long topicId);

    TopicDetails getTopic(StreamId streamId, TopicId topicId);

    List<Topic> getTopics(Long streamId);

    List<Topic> getTopics(StreamId streamId);

    void createTopic(Long streamId,
                     Optional<Long> topicId,
                     Long partitionsCount,
                     CompressionAlgorithm compressionAlgorithm,
                     BigInteger messageExpiry,
                     BigInteger maxTopicSize,
                     Optional<Short> replicationFactor,
                     String name);

    void createTopic(StreamId streamId,
                     Optional<Long> topicId,
                     Long partitionsCount,
                     CompressionAlgorithm compressionAlgorithm,
                     BigInteger messageExpiry,
                     BigInteger maxTopicSize,
                     Optional<Short> replicationFactor,
                     String name);

    void updateTopic(Long streamId, Long topicId, Optional<Long> messageExpiry, String name);

    void updateTopic(StreamId streamId, TopicId topicId, Optional<Long> messageExpiry, String name);

    void deleteTopic(Long streamId, Long topicId);

    void deleteTopic(StreamId streamId, TopicId topicId);

}
