package rs.iggy.http;

import com.fasterxml.jackson.core.type.TypeReference;
import rs.iggy.identifier.StreamId;
import rs.iggy.identifier.TopicId;
import rs.iggy.topic.Topic;
import rs.iggy.topic.TopicDetails;
import rs.iggy.topic.TopicsClient;
import java.util.List;
import java.util.Optional;

class TopicsHttpClient implements TopicsClient {

    private static final String STREAMS = "/streams";
    private static final String TOPICS = "/topics";
    private final HttpClient httpClient;

    public TopicsHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public TopicDetails getTopic(Long streamId, Long topicId) {
        return getTopic(StreamId.of(streamId), TopicId.of(topicId));
    }

    @Override
    public TopicDetails getTopic(StreamId streamId, TopicId topicId) {
        var request = httpClient.prepareGetRequest(STREAMS + "/" + streamId + TOPICS + "/" + topicId);
        return httpClient.execute(request, TopicDetails.class);
    }

    @Override
    public List<Topic> getTopics(Long streamId) {
        return getTopics(StreamId.of(streamId));
    }

    @Override
    public List<Topic> getTopics(StreamId streamId) {
        var request = httpClient.prepareGetRequest(STREAMS + "/" + streamId + TOPICS);
        return httpClient.execute(request, new TypeReference<>() {
        });
    }

    @Override
    public void createTopic(Long streamId, Long topicId, Long partitionsCount, Optional<Long> messageExpiry, String name) {
        createTopic(StreamId.of(streamId), topicId, partitionsCount, messageExpiry, name);
    }

    @Override
    public void createTopic(StreamId streamId, Long topicId, Long partitionsCount, Optional<Long> messageExpiry, String name) {
        var request = httpClient.preparePostRequest(STREAMS + "/" + streamId + TOPICS,
                new CreateTopic(topicId, partitionsCount, messageExpiry, name));
        httpClient.execute(request);
    }

    @Override
    public void updateTopic(Long streamId, Long topicId, Optional<Long> messageExpiry, String name) {
        updateTopic(StreamId.of(streamId), TopicId.of(topicId), messageExpiry, name);
    }

    @Override
    public void updateTopic(StreamId streamId, TopicId topicId, Optional<Long> messageExpiry, String name) {
        var request = httpClient.preparePutRequest(STREAMS + "/" + streamId + TOPICS + "/" + topicId,
                new UpdateTopic(messageExpiry, name));
        httpClient.execute(request);
    }

    @Override
    public void deleteTopic(Long streamId, Long topicId) {
        deleteTopic(StreamId.of(streamId), TopicId.of(topicId));
    }

    @Override
    public void deleteTopic(StreamId streamId, TopicId topicId) {
        var request = httpClient.prepareDeleteRequest(STREAMS + "/" + streamId + TOPICS + "/" + topicId);
        httpClient.execute(request);
    }

    record CreateTopic(Long topicId, Long partitionsCount, Optional<Long> messageExpiry, String name) {
    }

    record UpdateTopic(Optional<Long> messageExpiry, String name) {
    }
}
