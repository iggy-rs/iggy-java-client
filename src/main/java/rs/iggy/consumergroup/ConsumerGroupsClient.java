package rs.iggy.consumergroup;

import java.util.List;

public interface ConsumerGroupsClient {

    ConsumerGroupDetails getConsumerGroup(Long streamId, Long topicId, Long consumerGroupId);

    ConsumerGroupDetails getConsumerGroup(Long streamId, Long topicId, String consumerGroupName);

    List<ConsumerGroup> getConsumerGroups(Long streamId, Long topicId);

    void createConsumerGroup(Long streamId, Long topicId, Long consumerGroupId, String consumerGroupName);

    void deleteConsumerGroup(Long streamId, Long topicId, Long consumerGroupId);

    void deleteConsumerGroup(Long streamId, Long topicId, String consumerGroupName);

    void joinConsumerGroup(Long streamId, Long topicId, Long consumerGroupId);

    void joinConsumerGroup(Long streamId, Long topicId, String consumerGroupName);

    void leaveConsumerGroup(Long streamId, Long topicId, Long consumerGroupId);

    void leaveConsumerGroup(Long streamId, Long topicId, String consumerGroupName);

}
