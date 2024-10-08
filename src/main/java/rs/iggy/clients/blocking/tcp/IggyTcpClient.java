package rs.iggy.clients.blocking.tcp;

import rs.iggy.clients.blocking.*;

public class IggyTcpClient implements IggyClient {

    private final UsersTcpClient usersClient;
    private final StreamsTcpClient streamsClient;
    private final TopicsTcpClient topicsClient;
    private final PartitionsTcpClient partitionsClient;
    private final ConsumerGroupsTcpClient consumerGroupsClient;
    private final ConsumerOffsetTcpClient consumerOffsetsClient;
    private final MessagesTcpClient messagesClient;

    public IggyTcpClient(String host, Integer port) {
        TcpConnectionHandler connection = new TcpConnectionHandler(host, port);
        usersClient = new UsersTcpClient(connection);
        streamsClient = new StreamsTcpClient(connection);
        topicsClient = new TopicsTcpClient(connection);
        partitionsClient = new PartitionsTcpClient(connection);
        consumerGroupsClient = new ConsumerGroupsTcpClient(connection);
        consumerOffsetsClient = new ConsumerOffsetTcpClient(connection);
        messagesClient = new MessagesTcpClient(connection);
    }

    @Override
    public SystemClient system() {
        throw new UnsupportedOperationException();
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
