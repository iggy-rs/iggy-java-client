package rs.iggy.partition;

import rs.iggy.identifier.StreamId;
import rs.iggy.identifier.TopicId;

public interface PartitionsClient {

    void createPartitions(Long streamId, Long topicId, Long partitionsCount);

    void createPartitions(StreamId streamId, TopicId topicId, Long partitionsCount);

    void deletePartitions(Long streamId, Long topicId, Long partitionsCount);

    void deletePartitions(StreamId streamId, TopicId topicId, Long partitionsCount);

}
