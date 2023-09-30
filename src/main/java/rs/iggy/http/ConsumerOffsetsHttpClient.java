package rs.iggy.http;

import org.apache.hc.core5.http.message.BasicNameValuePair;
import rs.iggy.consumeroffset.ConsumerOffsetInfo;
import rs.iggy.consumeroffset.ConsumerOffsetsClient;
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
        var request = httpClient.preparePutRequest(path(streamId, topicId), new StoreConsumerOffset(consumerId, partitionId, offset));
        httpClient.execute(request);
    }

    @Override
    public ConsumerOffsetInfo getConsumerOffset(Long streamId, Long topicId, Optional<Long> partitionId, Long consumerId) {
        var request = httpClient.prepareGetRequest(path(streamId, topicId),
                new BasicNameValuePair("consumer_id", consumerId.toString()),
                new BasicNameValuePair("partition_id", partitionId.map(Object::toString).orElse(DEFAULT_PARTITION_ID)));
        return httpClient.execute(request, ConsumerOffsetInfo.class);
    }

    private static String path(Long streamId, Long topicId) {
        return "/streams/" + streamId + "/topics/" + topicId + "/consumer-offsets";
    }

    private record StoreConsumerOffset(Long consumerId, Optional<Long> partitionId, BigInteger offset) {
    }
}
