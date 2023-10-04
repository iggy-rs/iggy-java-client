package rs.iggy.consumeroffset;

import rs.iggy.identifier.ConsumerId;
import rs.iggy.identifier.StreamId;
import rs.iggy.identifier.TopicId;
import java.math.BigInteger;
import java.util.Optional;

public interface ConsumerOffsetsClient {

    void storeConsumerOffset(Long streamId, Long topicId, Optional<Long> partitionId, Long consumerId, BigInteger offset);

    void storeConsumerOffset(StreamId streamId, TopicId topicId, Optional<Long> partitionId, ConsumerId consumerId, BigInteger offset);

    ConsumerOffsetInfo getConsumerOffset(Long streamId, Long topicId, Optional<Long> partitionId, Long consumerId);

    ConsumerOffsetInfo getConsumerOffset(StreamId streamId, TopicId topicId, Optional<Long> partitionId, ConsumerId consumerId);

}
