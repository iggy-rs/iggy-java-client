package rs.iggy.clients.blocking.http;

import com.fasterxml.jackson.core.type.TypeReference;
import rs.iggy.clients.blocking.ConsumerGroupsClient;
import rs.iggy.consumergroup.ConsumerGroup;
import rs.iggy.consumergroup.ConsumerGroupDetails;
import rs.iggy.identifier.ConsumerGroupId;
import rs.iggy.identifier.StreamId;
import rs.iggy.identifier.TopicId;
import java.util.List;

class ConsumerGroupsHttpClient implements ConsumerGroupsClient {

    private final HttpClient httpClient;

    public ConsumerGroupsHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public ConsumerGroupDetails getConsumerGroup(Long streamId, Long topicId, Long consumerGroupId) {
        return getConsumerGroup(StreamId.of(streamId), TopicId.of(topicId), ConsumerGroupId.of(consumerGroupId));
    }

    @Override
    public ConsumerGroupDetails getConsumerGroup(StreamId streamId, TopicId topicId, ConsumerGroupId consumerGroupId) {
        var request = httpClient.prepareGetRequest(path(streamId, topicId) + "/" + consumerGroupId);
        return httpClient.execute(request, ConsumerGroupDetails.class);
    }

    @Override
    public List<ConsumerGroup> getConsumerGroups(Long streamId, Long topicId) {
        return getConsumerGroups(StreamId.of(streamId), TopicId.of(topicId));
    }

    @Override
    public List<ConsumerGroup> getConsumerGroups(StreamId streamId, TopicId topicId) {
        var request = httpClient.prepareGetRequest(path(streamId, topicId));
        return httpClient.execute(request, new TypeReference<>() {
        });
    }

    @Override
    public void createConsumerGroup(Long streamId, Long topicId, Long consumerGroupId, String consumerGroupName) {
        createConsumerGroup(StreamId.of(streamId), TopicId.of(topicId), consumerGroupId, consumerGroupName);
    }

    @Override
    public void createConsumerGroup(StreamId streamId, TopicId topicId, Long consumerGroupId, String consumerGroupName) {
        var request = httpClient.preparePostRequest(path(streamId, topicId),
                new CreateConsumerGroup(consumerGroupId, consumerGroupName));
        httpClient.execute(request);
    }

    @Override
    public void deleteConsumerGroup(Long streamId, Long topicId, Long consumerGroupId) {
        deleteConsumerGroup(StreamId.of(streamId), TopicId.of(topicId), ConsumerGroupId.of(consumerGroupId));
    }

    @Override
    public void deleteConsumerGroup(StreamId streamId, TopicId topicId, ConsumerGroupId consumerGroupId) {
        var request = httpClient.prepareDeleteRequest(path(streamId, topicId) + "/" + consumerGroupId);
        httpClient.execute(request);
    }

    @Override
    public void joinConsumerGroup(Long streamId, Long topicId, Long consumerGroupId) {
        joinConsumerGroup(StreamId.of(streamId), TopicId.of(topicId), ConsumerGroupId.of(consumerGroupId));
    }

    @Override
    public void joinConsumerGroup(StreamId streamId, TopicId topicId, ConsumerGroupId consumerGroupId) {
        throw new UnsupportedOperationException("Method not available in HTTP client");
    }

    @Override
    public void leaveConsumerGroup(Long streamId, Long topicId, Long consumerGroupId) {
        leaveConsumerGroup(StreamId.of(streamId), TopicId.of(topicId), ConsumerGroupId.of(consumerGroupId));
    }

    @Override
    public void leaveConsumerGroup(StreamId streamId, TopicId topicId, ConsumerGroupId consumerGroupId) {
        throw new UnsupportedOperationException("Method not available in HTTP client");
    }

    private static String path(StreamId streamId, TopicId topicId) {
        return "/streams/" + streamId + "/topics/" + topicId + "/consumer-groups";
    }

    private record CreateConsumerGroup(Long consumerGroupId, String name) {
    }

}
