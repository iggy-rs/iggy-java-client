package rs.iggy.tcp;

import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;
import rs.iggy.IggyClient;
import rs.iggy.consumergroup.ConsumerGroupsClient;
import rs.iggy.consumeroffset.ConsumerOffsetsClient;
import rs.iggy.message.MessagesClient;
import rs.iggy.partition.PartitionsClient;
import rs.iggy.stream.StreamsClient;
import rs.iggy.system.SystemClient;
import rs.iggy.topic.TopicsClient;
import rs.iggy.user.UsersClient;

public class IggyTcpClient implements IggyClient {

    private final UsersTcpClient usersClient;

    public IggyTcpClient(String host, Integer port) {
        Connection tcpConnection = TcpClient.create().host(host).port(port).connectNow();
        usersClient = new UsersTcpClient(tcpConnection);
    }

    @Override
    public SystemClient system() {
        throw new UnsupportedOperationException();
    }

    @Override
    public StreamsClient streams() {
        throw new UnsupportedOperationException();
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
