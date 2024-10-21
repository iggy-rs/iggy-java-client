package rs.iggy.clients.blocking.tcp;

import rs.iggy.clients.blocking.IggyClient;
import rs.iggy.clients.blocking.TopicsClientBaseTest;

class TopicsTcpClientTest extends TopicsClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return TcpClientFactory.create(iggyServer);
    }

}
