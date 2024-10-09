package rs.iggy.clients.blocking.tcp;

import rs.iggy.clients.blocking.ConsumerGroupsClientBaseTest;
import rs.iggy.clients.blocking.IggyClient;

class ConsumerGroupsTcpClientTest extends ConsumerGroupsClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return TcpClientFactory.create(iggyServer);
    }

}
