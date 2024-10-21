package rs.iggy.clients.blocking.tcp;

import rs.iggy.clients.blocking.IggyClient;
import rs.iggy.clients.blocking.StreamClientBaseTest;

class StreamTcpClientTest extends StreamClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return TcpClientFactory.create(iggyServer);
    }

}
