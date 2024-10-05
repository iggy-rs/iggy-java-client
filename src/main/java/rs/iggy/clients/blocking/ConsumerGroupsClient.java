package rs.iggy.clients.blocking;

import rs.iggy.consumergroup.ConsumerGroup;
import rs.iggy.consumergroup.ConsumerGroupDetails;
import rs.iggy.identifier.ConsumerGroupId;
import rs.iggy.identifier.StreamId;
import rs.iggy.identifier.TopicId;
import java.util.List;
import java.util.Optional;

public interface ConsumerGroupsClient {

    ConsumerGroupDetails getConsumerGroup(Long streamId, Long topicId, Long consumerGroupId);

    ConsumerGroupDetails getConsumerGroup(StreamId streamId, TopicId topicId, ConsumerGroupId consumerGroupId);

    List<ConsumerGroup> getConsumerGroups(Long streamId, Long topicId);

    List<ConsumerGroup> getConsumerGroups(StreamId streamId, TopicId topicId);

    ConsumerGroupDetails createConsumerGroup(Long streamId, Long topicId, Optional<Long> consumerGroupId, String consumerGroupName);

    ConsumerGroupDetails createConsumerGroup(StreamId streamId, TopicId topicId, Optional<Long> consumerGroupId, String consumerGroupName);

    void deleteConsumerGroup(Long streamId, Long topicId, Long consumerGroupId);

    void deleteConsumerGroup(StreamId streamId, TopicId topicId, ConsumerGroupId consumerGroupId);

    void joinConsumerGroup(Long streamId, Long topicId, Long consumerGroupId);

    void joinConsumerGroup(StreamId streamId, TopicId topicId, ConsumerGroupId consumerGroupId);

    void leaveConsumerGroup(Long streamId, Long topicId, Long consumerGroupId);

    void leaveConsumerGroup(StreamId streamId, TopicId topicId, ConsumerGroupId consumerGroupId);

}
