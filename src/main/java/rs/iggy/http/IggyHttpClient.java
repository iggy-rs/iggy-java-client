package rs.iggy.http;

import rs.iggy.consumergroup.ConsumerGroupsClient;
import rs.iggy.consumeroffset.ConsumerOffsetsClient;
import rs.iggy.message.MessagesClient;
import rs.iggy.partition.PartitionsClient;
import rs.iggy.stream.StreamsClient;
import rs.iggy.system.SystemClient;
import rs.iggy.topic.TopicsClient;
import rs.iggy.user.UsersClient;

public class IggyHttpClient {

    private final SystemHttpClient systemClient;
    private final StreamsHttpClient streamsClient;
    private final UsersHttpClient usersClient;
    private final TopicsHttpClient topicsClient;
    private final PartitionsHttpClient partitionsClient;
    private final ConsumerGroupsHttpClient consumerGroupsClient;
    private final ConsumerOffsetsHttpClient consumerOffsetsClient;
    private final MessagesHttpClient messagesClient;

    public IggyHttpClient(String url) {
        HttpClient httpClient = new HttpClient(url);
        systemClient = new SystemHttpClient(httpClient);
        streamsClient = new StreamsHttpClient(httpClient);
        usersClient = new UsersHttpClient(httpClient);
        topicsClient = new TopicsHttpClient(httpClient);
        partitionsClient = new PartitionsHttpClient(httpClient);
        consumerGroupsClient = new ConsumerGroupsHttpClient(httpClient);
        consumerOffsetsClient = new ConsumerOffsetsHttpClient(httpClient);
        messagesClient = new MessagesHttpClient(httpClient);
    }

    public SystemClient system() {
        return systemClient;
    }

    public StreamsClient streams() {
        return streamsClient;
    }

    public UsersClient users() {
        return usersClient;
    }

    public TopicsClient topics() {
        return topicsClient;
    }

    public PartitionsClient partitions() {
        return partitionsClient;
    }

    public ConsumerGroupsClient consumerGroups() {
        return consumerGroupsClient;
    }

    public ConsumerOffsetsClient consumerOffsets() {
        return consumerOffsetsClient;
    }

    public MessagesClient messages() {
        return messagesClient;
    }

}
