package rs.iggy.topic;

import java.util.List;
import java.util.Optional;

public interface TopicsClient {

    TopicDetails getTopic(Long streamId, Long topicId);

    List<Topic> getTopics(Long streamId);

    void createTopic(Long streamId, Long topicId, Long partitionsCount, Optional<Long> messageExpiry, String name);

    void updateTopic(Long streamId, Long topicId, Optional<Long> messageExpiry, String name);

    void deleteTopic(Long streamId, Long topicId);

}
