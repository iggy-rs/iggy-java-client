package rs.iggy.clients.blocking.tcp;

import rs.iggy.clients.blocking.ConsumerOffsetsClientBaseTest;
import rs.iggy.clients.blocking.IggyClient;

class ConsumerOffsetsTcpClientTest extends ConsumerOffsetsClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return TcpClientFactory.create(iggyServer);
    }

}
