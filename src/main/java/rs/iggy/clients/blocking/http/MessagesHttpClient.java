package rs.iggy.clients.blocking.http;

import org.apache.hc.core5.http.message.BasicNameValuePair;
import rs.iggy.clients.blocking.MessagesClient;
import rs.iggy.identifier.ConsumerId;
import rs.iggy.identifier.SingleConsumerId;
import rs.iggy.identifier.StreamId;
import rs.iggy.identifier.TopicId;
import rs.iggy.message.MessageToSend;
import rs.iggy.message.Partitioning;
import rs.iggy.message.PolledMessages;
import rs.iggy.message.PollingStrategy;
import java.util.List;
import java.util.Optional;

class MessagesHttpClient implements MessagesClient {

    private final HttpClient httpClient;

    public MessagesHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public PolledMessages pollMessages(Long streamId, Long topicId, Optional<Long> partitionId, Long consumerId, PollingStrategy strategy, Long count, boolean autoCommit) {
        return pollMessages(StreamId.of(streamId), TopicId.of(topicId), partitionId, SingleConsumerId.of(consumerId),
                strategy, count, autoCommit);
    }

    @Override
    public PolledMessages pollMessages(StreamId streamId, TopicId topicId, Optional<Long> partitionId, ConsumerId consumerId, PollingStrategy strategy, Long count, boolean autoCommit) {
        var request = httpClient.prepareGetRequest(path(streamId, topicId),
                new BasicNameValuePair("consumer_id", consumerId.toString()),
                partitionId.map(id -> new BasicNameValuePair("partition_id", id.toString())).orElse(null),
                new BasicNameValuePair("strategy_kind", strategy.kind().name()),
                new BasicNameValuePair("strategy_value", strategy.value().toString()),
                new BasicNameValuePair("count", count.toString()),
                new BasicNameValuePair("auto_commit", Boolean.toString(autoCommit)));
        return httpClient.execute(request, PolledMessages.class);
    }

    @Override
    public void sendMessages(Long streamId, Long topicId, Partitioning partitioning, List<MessageToSend> messages) {
        sendMessages(StreamId.of(streamId), TopicId.of(topicId), partitioning, messages);
    }

    @Override
    public void sendMessages(StreamId streamId, TopicId topicId, Partitioning partitioning, List<MessageToSend> messages) {
        var request = httpClient.preparePostRequest(path(streamId, topicId), new SendMessages(partitioning, messages));
        httpClient.execute(request);
    }

    private static String path(StreamId streamId, TopicId topicId) {
        return "/streams/" + streamId + "/topics/" + topicId + "/messages";
    }

    private record SendMessages(Partitioning partitioning, List<MessageToSend> messages) {
    }
}
