package rs.iggy.clients.blocking.tcp;

import rs.iggy.clients.blocking.IggyClient;
import rs.iggy.clients.blocking.PartitionsClientBaseTest;

class PartitionsTcpClientTest extends PartitionsClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return TcpClientFactory.create(iggyServer);
    }

}
