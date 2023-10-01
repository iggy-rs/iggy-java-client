package rs.iggy.http;

import rs.iggy.IggyClient;
import rs.iggy.consumergroup.ConsumerGroupsClient;
import rs.iggy.consumeroffset.ConsumerOffsetsClient;
import rs.iggy.message.MessagesClient;
import rs.iggy.partition.PartitionsClient;
import rs.iggy.stream.StreamsClient;
import rs.iggy.system.SystemClient;
import rs.iggy.topic.TopicsClient;
import rs.iggy.user.UsersClient;

public class IggyHttpClient implements IggyClient {

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

    @Override
    public SystemClient system() {
        return systemClient;
    }

    @Override
    public StreamsClient streams() {
        return streamsClient;
    }

    @Override
    public UsersClient users() {
        return usersClient;
    }

    @Override
    public TopicsClient topics() {
        return topicsClient;
    }

    @Override
    public PartitionsClient partitions() {
        return partitionsClient;
    }

    @Override
    public ConsumerGroupsClient consumerGroups() {
        return consumerGroupsClient;
    }

    @Override
    public ConsumerOffsetsClient consumerOffsets() {
        return consumerOffsetsClient;
    }

    @Override
    public MessagesClient messages() {
        return messagesClient;
    }

}
