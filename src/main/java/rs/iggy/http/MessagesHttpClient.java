package rs.iggy.http;

import org.apache.hc.core5.http.message.BasicNameValuePair;
import rs.iggy.message.*;
import java.util.List;
import java.util.Optional;

class MessagesHttpClient implements MessagesClient {

    private final HttpClient httpClient;

    public MessagesHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public PolledMessages pollMessages(Long streamId, Long topicId, Optional<Long> partitionId, Long consumerId, PollingStrategy strategy, Long count, boolean autoCommit) {
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
        var request = httpClient.preparePostRequest(path(streamId, topicId), new SendMessages(partitioning, messages));
        httpClient.execute(request);
    }

    private static String path(Long streamId, Long topicId) {
        return "/streams/" + streamId + "/topics/" + topicId + "/messages";
    }

    private record SendMessages(Partitioning partitioning, List<MessageToSend> messages) {
    }
}
