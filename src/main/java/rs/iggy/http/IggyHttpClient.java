package rs.iggy.http;

import rs.iggy.consumergroup.ConsumerGroupsClient;
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

    public IggyHttpClient(String url) {
        HttpClient httpClient = new HttpClient(url);
        systemClient = new SystemHttpClient(httpClient);
        streamsClient = new StreamsHttpClient(httpClient);
        usersClient = new UsersHttpClient(httpClient);
        topicsClient = new TopicsHttpClient(httpClient);
        partitionsClient = new PartitionsHttpClient(httpClient);
        consumerGroupsClient = new ConsumerGroupsHttpClient(httpClient);
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

}
