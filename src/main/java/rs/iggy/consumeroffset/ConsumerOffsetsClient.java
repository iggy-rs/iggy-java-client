package rs.iggy.consumeroffset;

import java.math.BigInteger;
import java.util.Optional;

public interface ConsumerOffsetsClient {

    void storeConsumerOffset(Long streamId, Long topicId, Optional<Long> partitionId, Long consumerId, BigInteger offset);

    ConsumerOffsetInfo getConsumerOffset(Long streamId, Long topicId, Optional<Long> partitionId, Long consumerId);

}
