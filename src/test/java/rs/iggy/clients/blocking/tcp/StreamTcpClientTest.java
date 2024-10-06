package rs.iggy.clients.blocking.tcp;

import org.junit.jupiter.api.Disabled;
import rs.iggy.clients.blocking.IggyClient;
import rs.iggy.clients.blocking.StreamClientBaseTest;

@Disabled
class StreamTcpClientTest extends StreamClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return TcpClientFactory.create(iggyServer);
    }

}
