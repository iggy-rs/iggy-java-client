package rs.iggy.clients.blocking.tcp;

import rs.iggy.clients.blocking.IggyClient;
import rs.iggy.clients.blocking.MessagesClientBaseTest;

class MessagesTcpClientTest extends MessagesClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return TcpClientFactory.create(iggyServer);
    }

}
