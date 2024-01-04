package rs.iggy.clients.blocking.http;

import org.apache.hc.core5.http.message.BasicNameValuePair;
import rs.iggy.clients.blocking.ConsumerOffsetsClient;
import rs.iggy.consumeroffset.ConsumerOffsetInfo;
import rs.iggy.identifier.ConsumerId;
import rs.iggy.identifier.SingleConsumerId;
import rs.iggy.identifier.StreamId;
import rs.iggy.identifier.TopicId;
import java.math.BigInteger;
import java.util.Optional;

class ConsumerOffsetsHttpClient implements ConsumerOffsetsClient {

    private static final String DEFAULT_PARTITION_ID = "1";
    private final HttpClient httpClient;

    public ConsumerOffsetsHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void storeConsumerOffset(Long streamId, Long topicId, Optional<Long> partitionId, Long consumerId, BigInteger offset) {
        storeConsumerOffset(StreamId.of(streamId), TopicId.of(topicId), partitionId, SingleConsumerId.of(consumerId),
                offset);
    }

    @Override
    public void storeConsumerOffset(StreamId streamId, TopicId topicId, Optional<Long> partitionId, ConsumerId consumerId, BigInteger offset) {
        var request = httpClient.preparePutRequest(path(streamId, topicId),
                new StoreConsumerOffset(consumerId.toString(), partitionId, offset));
        httpClient.execute(request);
    }

    @Override
    public ConsumerOffsetInfo getConsumerOffset(Long streamId, Long topicId, Optional<Long> partitionId, Long consumerId) {
        return getConsumerOffset(StreamId.of(streamId), TopicId.of(topicId), partitionId,
                SingleConsumerId.of(consumerId));
    }

    @Override
    public ConsumerOffsetInfo getConsumerOffset(StreamId streamId, TopicId topicId, Optional<Long> partitionId, ConsumerId consumerId) {
        var request = httpClient.prepareGetRequest(path(streamId, topicId),
                new BasicNameValuePair("consumer_id", consumerId.toString()),
                new BasicNameValuePair("partition_id", partitionId.map(Object::toString).orElse(DEFAULT_PARTITION_ID)));
        return httpClient.execute(request, ConsumerOffsetInfo.class);
    }

    private static String path(StreamId streamId, TopicId topicId) {
        return "/streams/" + streamId + "/topics/" + topicId + "/consumer-offsets";
    }

    private record StoreConsumerOffset(String consumerId, Optional<Long> partitionId, BigInteger offset) {
    }
}
