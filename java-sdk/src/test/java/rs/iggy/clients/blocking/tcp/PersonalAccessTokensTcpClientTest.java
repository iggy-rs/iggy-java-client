package rs.iggy.clients.blocking.tcp;

import rs.iggy.clients.blocking.IggyClient;
import rs.iggy.clients.blocking.PersonalAccessTokensBaseTest;

class PersonalAccessTokensTcpClientTest extends PersonalAccessTokensBaseTest {

    @Override
    protected IggyClient getClient() {
        return TcpClientFactory.create(iggyServer);
    }

}
