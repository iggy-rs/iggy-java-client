package rs.iggy.clients.blocking.tcp;

import rs.iggy.clients.blocking.*;

public class IggyTcpClient implements IggyClient {

    private final UsersTcpClient usersClient;
    private final StreamsTcpClient streamsClient;

    public IggyTcpClient(String host, Integer port) {
        TcpConnectionHandler connection = new TcpConnectionHandler(host, port);
        usersClient = new UsersTcpClient(connection);
        streamsClient = new StreamsTcpClient(connection);
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
        throw new UnsupportedOperationException();
    }

    @Override
    public PartitionsClient partitions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConsumerGroupsClient consumerGroups() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConsumerOffsetsClient consumerOffsets() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MessagesClient messages() {
        throw new UnsupportedOperationException();
    }
}
