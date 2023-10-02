package rs.iggy.http;

import com.fasterxml.jackson.core.type.TypeReference;
import rs.iggy.consumergroup.ConsumerGroup;
import rs.iggy.consumergroup.ConsumerGroupDetails;
import rs.iggy.consumergroup.ConsumerGroupsClient;
import java.util.List;

class ConsumerGroupsHttpClient implements ConsumerGroupsClient {

    private final HttpClient httpClient;

    public ConsumerGroupsHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public ConsumerGroupDetails getConsumerGroup(Long streamId, Long topicId, Long consumerGroupId) {
        var request = httpClient.prepareGetRequest(path(streamId, topicId) + "/" + consumerGroupId);
        return httpClient.execute(request, ConsumerGroupDetails.class);
    }

    @Override
    public ConsumerGroupDetails getConsumerGroup(Long streamId, Long topicId, String consumerGroupName) {
        var request = httpClient.prepareGetRequest(path(streamId, topicId) + "/" + consumerGroupName);
        return httpClient.execute(request, ConsumerGroupDetails.class);
    }

    @Override
    public List<ConsumerGroup> getConsumerGroups(Long streamId, Long topicId) {
        var request = httpClient.prepareGetRequest(path(streamId, topicId));
        return httpClient.execute(request, new TypeReference<>() {
        });
    }

    @Override
    public void createConsumerGroup(Long streamId, Long topicId, Long consumerGroupId, String consumerGroupName) {
        var request = httpClient.preparePostRequest(path(streamId, topicId),
                new CreateConsumerGroup(consumerGroupId, consumerGroupName));
        httpClient.execute(request);
    }

    @Override
    public void deleteConsumerGroup(Long streamId, Long topicId, Long consumerGroupId) {
        var request = httpClient.prepareDeleteRequest(path(streamId, topicId) + "/" + consumerGroupId);
        httpClient.execute(request);
    }

    @Override
    public void deleteConsumerGroup(Long streamId, Long topicId, String consumerGroupName) {
        var request = httpClient.prepareDeleteRequest(path(streamId, topicId) + "/" + consumerGroupName);
        httpClient.execute(request);
    }

    @Override
    public void joinConsumerGroup(Long streamId, Long topicId, Long consumerGroupId) {
        throw new UnsupportedOperationException("Method not available in HTTP client");
    }

    @Override
    public void joinConsumerGroup(Long streamId, Long topicId, String consumerGroupName) {
        throw new UnsupportedOperationException("Method not available in HTTP client");
    }

    @Override
    public void leaveConsumerGroup(Long streamId, Long topicId, Long consumerGroupId) {
        throw new UnsupportedOperationException("Method not available in HTTP client");
    }

    @Override
    public void leaveConsumerGroup(Long streamId, Long topicId, String consumerGroupName) {
        throw new UnsupportedOperationException("Method not available in HTTP client");
    }

    private static String path(Long streamId, Long topicId) {
        return "/streams/" + streamId + "/topics/" + topicId + "/consumer-groups";
    }

    private record CreateConsumerGroup(Long consumerGroupId, String name) {
    }

}
