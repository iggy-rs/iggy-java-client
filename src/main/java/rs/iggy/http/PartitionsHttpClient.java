package rs.iggy.http;

import org.apache.hc.core5.http.message.BasicNameValuePair;
import rs.iggy.identifier.StreamId;
import rs.iggy.identifier.TopicId;
import rs.iggy.partition.PartitionsClient;

class PartitionsHttpClient implements PartitionsClient {

    private static final String STREAMS = "/streams";
    private static final String TOPICS = "/topics";
    private static final String PARTITIONS = "/partitions";
    private final HttpClient httpClient;

    public PartitionsHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void createPartitions(Long streamId, Long topicId, Long partitionsCount) {
        createPartitions(StreamId.of(streamId), TopicId.of(topicId), partitionsCount);
    }

    @Override
    public void createPartitions(StreamId streamId, TopicId topicId, Long partitionsCount) {
        var request = httpClient.preparePostRequest(STREAMS + "/" + streamId + TOPICS + "/" + topicId + PARTITIONS,
                new CreatePartitions(partitionsCount));
        httpClient.execute(request);
    }

    @Override
    public void deletePartitions(Long streamId, Long topicId, Long partitionsCount) {
        deletePartitions(StreamId.of(streamId), TopicId.of(topicId), partitionsCount);
    }

    @Override
    public void deletePartitions(StreamId streamId, TopicId topicId, Long partitionsCount) {
        var request = httpClient.prepareDeleteRequest(STREAMS + "/" + streamId + TOPICS + "/" + topicId + PARTITIONS,
                new BasicNameValuePair("partitions_count", partitionsCount.toString()));
        httpClient.execute(request);
    }

    private record CreatePartitions(Long partitionsCount) {
    }
}
